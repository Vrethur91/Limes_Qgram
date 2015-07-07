package limes_qgram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Bene
 */
public class LimesQgram {

    private HashMap<Integer, ArrayList<String>> sortByLengthList1;
    private HashMap<Integer, ArrayList<String>> sortByLengthList2;
    private HashMap<String, HashSet<String>> sortByQGram;
    private Validation validation;
    private Tokenizer tokenizer;
    private Formula formula;
    private Bounds bounds;
    private double threshold;
    private int q;

    public LimesQgram() {
        this.bounds = Bounds.NO_BOUNDS;
        this.formula = Formula.JACCARD;
        this.tokenizer = Tokenizer.BASIC;
        this.validation = Validation.ALWAYS_TRUE;
        this.threshold = 0.6;
        this.q = 3;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public void setTokenizer(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void setValidation(Validation validation) {
        this.validation = validation;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public double[] computeSimilarities(ArrayList<String> list1, ArrayList<String> list2) {
        double n = 3.0;
        int hit = 0;
        long totalTime = 0;

        for (int i = 0; i < n; i++) {
            long timeStart1 = System.currentTimeMillis();
            hit = 0;
            if (bounds.equals(Bounds.NO_BOUNDS)) {
                for (String string1 : list1) {
                    for (String string2 : list2) {
                        if (validation.hasValidLengthDiff(string1, string2, q, threshold)) {
                            double sim = compare(string1, string2);
                            if (sim >= threshold) {
                                hit++;
                            }
                        }
                    }
                }
            } else {
                sortByLength(list1, list2);
                for (Entry<Integer, ArrayList<String>> entry : sortByLengthList1.entrySet()) {
                    int size = entry.getKey();
                    Integer[] currentBounds = bounds.getBounds(size, q, threshold);
                    int min = currentBounds[0];
                    int max = currentBounds[1];
                    for (String string1 : entry.getValue()) {
                        for (int currentLength = min; currentLength <= max; currentLength++) {
                            if (sortByLengthList2.containsKey(currentLength)) {
                                for (String string2 : sortByLengthList2.get(currentLength)) {
                                    if (validation.hasValidLengthDiff(string1, string2, q, threshold)) {
                                        double sim = compare(string1, string2);
                                        if (sim >= threshold) {
                                            hit++;
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }

            long timeEnd1 = System.currentTimeMillis();
            totalTime += (timeEnd1 - timeStart1);
        }
        
        long avgTime = Math.round(totalTime / n);
        //System.out.println("Zeit: " + avgTime);
        double[] returnValues = {avgTime, hit};
        return returnValues;

    }

    private double compare(String s1, String s2) {
        if (s1.equals(s2)) {
            return 1.0;
        }

        Set<String> q1 = tokenizer.qgrams(s1, q);
        Set<String> q2 = tokenizer.qgrams(s2, q);

        if (q1.isEmpty() || q2.isEmpty()) {
            return 0.0; // division will fail
        }

        int common = 0;
        for (String gram : q1) {
            if (q2.contains(gram)) {
                common++;
            }
        }

        return formula.compute(common, q1, q2);
    }

    private void sortByQGram(ArrayList<String> list1, ArrayList<String> list2) {
        sortByQGram = new HashMap<>();
        for (String string1 : list1) {
            Set<String> gramSet = tokenizer.qgrams(string1, q);
            if (gramSet.isEmpty()) {
                continue;
            } else {
                for (String gram : gramSet) {
                    if (sortByQGram.containsKey(gram)) {
                        sortByQGram.get(gram).add(string1);
                    } else {
                        HashSet<String> labelSet = new HashSet<>();
                        labelSet.add(string1);
                        sortByQGram.put(gram, labelSet);
                    }
                }
            }
        }

        for (String string1 : list2) {
            Set<String> gramSet = tokenizer.qgrams(string1, q);
            if (gramSet.isEmpty()) {
                continue;
            } else {
                for (String gram : gramSet) {
                    if (sortByQGram.containsKey(gram)) {
                        sortByQGram.get(gram).add(string1);
                    } else {
                        HashSet<String> labelSet = new HashSet<>();
                        labelSet.add(string1);
                        sortByQGram.put(gram, labelSet);
                    }
                }
            }
        }
    }

    private void sortByLength(ArrayList<String> list1, ArrayList<String> list2) {
        sortByLengthList1 = new HashMap<>();
        sortByLengthList2 = new HashMap<>();
        for (String string1 : list1) {
            if (sortByLengthList1.containsKey(string1.length())) {
                sortByLengthList1.get(string1.length()).add(string1);
            } else {
                ArrayList<String> labelList = new ArrayList<>();
                labelList.add(string1);
                sortByLengthList1.put(string1.length(), labelList);
            }
        }

        for (String string2 : list2) {
            if (sortByLengthList2.containsKey(string2.length())) {
                sortByLengthList2.get(string2.length()).add(string2);
            } else {
                ArrayList<String> labelList = new ArrayList<>();
                labelList.add(string2);
                sortByLengthList2.put(string2.length(), labelList);
            }
        }
    }

    private static String pad(String s, int q, boolean front) {
        StringBuffer buf = new StringBuffer(q);
        if (!front) {
            buf.append(s);
        }
        for (int ix = 0; ix < q - s.length(); ix++) {
            buf.append('.');
        }
        if (front) {
            buf.append(s);
        }
        return buf.toString();
    }

    public enum Formula {

        JACCARD {
                    public double compute(int common, Set<String> q1, Set<String> q2) {
                        return (double) common / (double) (q1.size() + q2.size() - common);
                    }
                };

        public double compute(int common, Set<String> q1, Set<String> q2) {
            System.out.println("Unknown formula: " + this);
            return 0;
        }
    }

    public enum Tokenizer {

        BASIC {
                    public Set<String> qgrams(String s, int q) {
                        Set<String> grams = new HashSet();
                        for (int ix = 0; ix < s.length() - q + 1; ix++) {
                            grams.add(s.substring(ix, ix + q));
                        }
                        return grams;
                    }
                },
        ENDS {
                    public Set<String> qgrams(String s, int q) {
                        Set<String> grams = new HashSet();
                        for (int ix = 1; (ix < q) && (ix < s.length()); ix++) {
                            grams.add(pad(s.substring(0, ix), q, true));
                        }
                        for (int ix = 0; ix < s.length() - q + 1; ix++) {
                            grams.add(s.substring(ix, ix + q));
                        }
                        for (int ix = 1; (ix < q) && (ix < s.length()); ix++) {
                            grams.add(pad(s.substring(s.length() - ix), q, false));
                        }

                        return grams;
                    }
                };

        public Set<String> qgrams(String s, int q) {
            System.out.println("Unknown tokenizer: " + this);
            return null;
        }
    }

    public enum Validation {

        ALWAYS_TRUE {
                    public boolean hasValidLengthDiff(String string1, String string2, int q, double threshold) {
                        return true;
                    }
                },
        BASIC {
                    public boolean hasValidLengthDiff(String string1, String string2, int q, double threshold) {
                        double size1 = string1.length();
                        double size2 = string2.length();

                        if (size1 - (q - 1) <= 0 || size2 - (q - 1) <= 0) {
                            return false;
                        }

                        if ((Math.min(size1, size2) - (q - 1)) / (Math.max(size1, size2) - (q - 1)) >= threshold) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                },
        ENDS {
                    public boolean hasValidLengthDiff(String string1, String string2, int q, double threshold) {
                        double size1 = string1.length();
                        double size2 = string2.length();

                        if ((Math.min(size1, size2) + (q - 1)) / (Math.max(size1, size2) + (q - 1)) >= threshold) {
                            return true;
                        } else {
                            return false;
                        }
                    }

                };

        public boolean hasValidLengthDiff(String string1, String string2, int q, double threshold) {
            System.out.println("Unknown validation option: " + this);
            return false;
        }

    }

    public enum Bounds {

        NO_BOUNDS {
                    public Integer[] getBounds(int size, int q, double threshold) {
                        return null;
                    }
                },
        BASIC {
                    public Integer[] getBounds(int size, int q, double threshold) {
                        Integer[] bounds = new Integer[2];

                        if (size - (q - 1) <= 0) {
                            bounds[0] = 1;
                        } else {
                            bounds[0] = (int) (threshold * (size - (q - 1)) + (q - 1)) + 1;
                        }

                        bounds[1] = (int) ((size - (q - 1)) / threshold + (q - 1));

                        return bounds;
                    }
                },
        ENDS {
                    public Integer[] getBounds(int size, int q, double threshold) {
                        Integer[] bounds = new Integer[2];

                        bounds[0] = (int) (threshold * (size + (q - 1)) - (q - 1)) + 1;

                        bounds[1] = (int) ((size + (q - 1)) / threshold - (q - 1));

                        return bounds;
                    }
                };

        public Integer[] getBounds(int size, int q, double threshold) {
            System.out.println("Unknown validation option");
            return null;
        }
    }
}
