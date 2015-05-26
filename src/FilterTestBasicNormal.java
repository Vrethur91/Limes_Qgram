
import java.util.ArrayList;

/**
 *
 * @author Bene
 */
public class FilterTestBasicNormal implements FilterTestInterface{

    @Override
    public void runTest(ArrayList<String> list1, ArrayList<String> list2, double threshold, int q) {
        System.out.println("Run BASIC normal");
        
        QGramComparator comp = new QGramComparator();
        comp.setFormula(QGramComparator.Formula.JACCARD);
        comp.setTokenizer(QGramComparator.Tokenizer.BASIC);
        comp.setQ(q);

        int hit = 0;
        long timeStart1 = System.currentTimeMillis();

        for (String string1 : list1) {
            for (String string2 : list2) {
                if (comp.compare(string1, string2) >= threshold)
                    hit++;
            }
        }

        long timeEnd1 = System.currentTimeMillis();
        long timeProcess1 = timeEnd1 - timeStart1;
        System.out.println("Zeit: " + timeProcess1);

        System.out.println("über Threshold: " + hit);
    }
    
}
