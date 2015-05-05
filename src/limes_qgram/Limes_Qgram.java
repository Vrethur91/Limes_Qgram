package limes_qgram;

/**
 *
 * @author Bene
 */
public class Limes_Qgram {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        QGramComparator comp = new QGramComparator();
        double value = comp.compare("Hello", "Bello");
        System.out.println(value);
    }
    
}
