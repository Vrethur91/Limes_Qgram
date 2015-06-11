package FileSampler;

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
import limes_qgram.LimesQgram;

/**
 *
 * @author Bene
 */
public class FileSampler {

    public ArrayList<String> getSample(ArrayList<String> list, int size) {
        ArrayList<String> sampleList = new ArrayList<>();
        HashSet<Integer> indexSet = new HashSet<>();
        while (sampleList.size() < size) {
            int randomIndex = (int) (Math.random() * list.size());
            if (!indexSet.contains(randomIndex)) {
                sampleList.add(list.get(randomIndex));
                indexSet.add(randomIndex);
            }
        }

        return sampleList;
    }

    public void saveList(String filepath, ArrayList<String> list) {
        try {
            File file = new File(filepath);
            file.createNewFile();
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
            bWriter.write("");
            for (String string : list) {
                bWriter.append(string + "\n");
            }
            bWriter.flush();
            bWriter.close();
        } catch (Exception ex) {
            Logger.getLogger(LimesQgram.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> importData() {
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

    public void convertData() {
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
