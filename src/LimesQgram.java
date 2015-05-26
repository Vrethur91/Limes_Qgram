

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
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
        /*
        ArrayList<String> list = importData();
        ArrayList<String> list1 = getSample(list, 10000);
        ArrayList<String> list2 = getSample(list, 5000);
        
        saveList("./data/sampleA.txt",list1);
        saveList("./data/sampleB.txt",list2);
        */
        
        ArrayList<String> list1 = loadList("./data/sampleA.txt");
        ArrayList<String> list2 = loadList("./data/sampleB.txt");
        
        
        FilterTestInterface basicNormal = new FilterTestBasicNormal();
        basicNormal.runTest(list1, list2, threshold, q);
        /*
        FilterTestInterface basicFilter = new FilterTestBasicFilter();
        basicFilter.runTest(list1, list2, threshold, q);
                */
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

    private ArrayList<String> loadList(String filepath){
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception ex) {
            Logger.getLogger(LimesQgram.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
    
    private void saveList(String filepath, ArrayList<String> list){
        try{
            File file = new File(filepath);
            file.createNewFile();
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
            bWriter.write("");
            for(String string:list){
                bWriter.append(string + "\n");
            }
            bWriter.flush();
            bWriter.close();
        } catch (Exception ex) {
            Logger.getLogger(LimesQgram.class.getName()).log(Level.SEVERE, null, ex);
        }
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
