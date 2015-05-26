import java.util.ArrayList;

/**
 *
 * @author Bene
 */
public class FilterTestBasicFilter implements FilterTestInterface {

    private double threshold;
    private int q;

    public void runTest(ArrayList<String> list1, ArrayList<String> list2, double threshold, int q) {
        System.out.println("Run BASIC filter");
        this.q = q;
        this.threshold = threshold;

        QGramComparator comp = new QGramComparator();
        comp.setFormula(QGramComparator.Formula.JACCARD);
        comp.setTokenizer(QGramComparator.Tokenizer.BASIC);
        comp.setQ(q);

        int hit = 0;
        long timeStart1 = System.currentTimeMillis();

        for (String string1 : list1) {
            for (String string2 : list2) {
                if (filter(string1, string2)) {
                    if (comp.compare(string1, string2) >= threshold)
                        hit++;
                }
            }
        }

        long timeEnd1 = System.currentTimeMillis();
        long timeProcess1 = timeEnd1 - timeStart1;
        System.out.println("Zeit: " + timeProcess1);

        System.out.println("über Threshold: " + hit);
    }

    private boolean filter(String string1, String string2) {
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

}
