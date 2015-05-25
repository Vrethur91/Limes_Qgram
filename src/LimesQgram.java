

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Bene
 */
public class LimesQgram {

    private HashSet<Integer> indexSet;
    private double threshold = 0.6;
    private int q = 3;

    public static void main(String[] args) {
        LimesQgram qgram = new LimesQgram();

        qgram.setThreshold(0.6);
        qgram.setQ(3);
        qgram.prepareData();
    }

    public LimesQgram() {
        indexSet = new HashSet<>();
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public void prepareData(){
        ArrayList<String> list = importData();
        ArrayList<String> list1 = getSample(list, 10000);
        ArrayList<String> list2 = getSample(list, 5000);
        
        runAnalysisA(list1, list2);
        runAnalysisB(list1, list2);
    }
    
    public void runAnalysisA(ArrayList<String> list1, ArrayList<String> list2) {
        System.out.println("Analysis A BASIC");
        
        QGramComparator comp = new QGramComparator();
        comp.setFormula(QGramComparator.Formula.JACCARD);
        comp.setTokenizer(QGramComparator.Tokenizer.BASIC);
        comp.setQ(q);

        ArrayList<Double> valueList1 = new ArrayList<>();

        long timeStart1 = System.currentTimeMillis();

        for (String string1 : list1) {
            for (String string2 : list2) {
                valueList1.add(comp.compare(string1, string2));
            }
        }

        long timeEnd1 = System.currentTimeMillis();
        long timeProcess1 = timeEnd1 - timeStart1;
        System.out.println("Normal Zeit: " + timeProcess1);

        int overThreshold = 0;
        for (Double value : valueList1) {
            if (value >= threshold) {
                overThreshold++;
            }
        }
        System.out.println("Normal über Threshold: " + overThreshold);

        ArrayList<Double> valueList2 = new ArrayList<>();
        long timeStart2 = System.currentTimeMillis();

        for (String string1 : list1) {
            for (String string2 : list2) {
                if (filterA(string1, string2)) {
                    valueList2.add(comp.compare(string1, string2));
                }
            }
        }

        long timeEnd2 = System.currentTimeMillis();
        long timeProcess2 = timeEnd2 - timeStart2;
        System.out.println("Filter Zeit: " + timeProcess2);
        
        overThreshold = 0;
        for (Double value : valueList1) {
            if (value >= threshold) {
                overThreshold++;
            }
        }
        
        System.out.println("Filter über Threshold: " + overThreshold);
    }
    
    public void runAnalysisB(ArrayList<String> list1, ArrayList<String> list2) {
        System.out.println("Analysis B PADDING");

        QGramComparator comp = new QGramComparator();
        comp.setFormula(QGramComparator.Formula.JACCARD);
        comp.setTokenizer(QGramComparator.Tokenizer.ENDS);
        comp.setQ(q);

        ArrayList<Double> valueList1 = new ArrayList<>();

        long timeStart1 = System.currentTimeMillis();

        for (String string1 : list1) {
            for (String string2 : list2) {
                valueList1.add(comp.compare(string1, string2));
            }
        }

        long timeEnd1 = System.currentTimeMillis();
        long timeProcess1 = timeEnd1 - timeStart1;
        System.out.println("Normal Zeit: " + timeProcess1);

        int overThreshold = 0;
        for (Double value : valueList1) {
            if (value >= threshold) {
                overThreshold++;
            }
        }
        System.out.println("Normal über Threshold: " + overThreshold);

        ArrayList<Double> valueList2 = new ArrayList<>();
        long timeStart2 = System.currentTimeMillis();

        for (String string1 : list1) {
            for (String string2 : list2) {
                if (filterB(string1, string2)) {
                    valueList2.add(comp.compare(string1, string2));
                }
            }
        }

        long timeEnd2 = System.currentTimeMillis();
        long timeProcess2 = timeEnd2 - timeStart2;
        System.out.println("Filter Zeit: " + timeProcess2);
        
        overThreshold = 0;
        for (Double value : valueList1) {
            if (value >= threshold) {
                overThreshold++;
            }
        }
        
        System.out.println("Filter über Threshold: " + overThreshold);
    }

    private boolean filterA(String string1, String string2) {
        int size1 = string1.length();
        int size2 = string2.length();

        if (size1 - (q - 1) <= 0 || size2 - (q - 1) <= 0) {
            return false;
        }

        if ((Math.min(size1, size2) - (q - 1)) / (Math.max(size1, size2) - (q - 1)) >= threshold) {
            return true;
        } else {
            return false;
        }
    }

    private boolean filterB(String string1, String string2) {
        int size1 = string1.length();
        int size2 = string2.length();

        if ((Math.min(size1, size2) + (q - 1)) / (Math.max(size1, size2) + (q - 1)) >= threshold) {
            return true;
        } else {
            return false;
        }
    }

    private ArrayList<String> getSample(ArrayList<String> list, int size) {
        ArrayList<String> sampleList = new ArrayList<>();

        while (sampleList.size() < size) {
            int randomIndex = (int) (Math.random() * list.size());
            if (!indexSet.contains(randomIndex)) {
                sampleList.add(list.get(randomIndex));
                indexSet.add(randomIndex);
            }
        }

        return sampleList;
    }

    private ArrayList<String> importData() {
        ArrayList<String> list = new ArrayList<>();
        String filepath = "./data/labels.txt";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "UTF-8"))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception ex) {
            Logger.getLogger(LimesQgram.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    private void convertData() {
        String filepath = "./data/labels_en.ttl";
        File file = new File("./data/labels.txt");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "UTF-8"))) {
            file.createNewFile();
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
            bWriter.write("");
            String line = "";
            while ((line = br.readLine()) != null) {
                Matcher m = Pattern.compile("\"(.*)\"").matcher(line);
                if (m.find()) {
                    String label = m.group(1);
                    bWriter.append(label + "\n");
                }
            }
            bWriter.flush();
            bWriter.close();
        } catch (Exception ex) {
            Logger.getLogger(LimesQgram.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
