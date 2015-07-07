package limes_qgram;

import java.util.ArrayList;

/**
 *
 * @author Bene
 */
public class Configuration {
    private String config;
    private ArrayList<Data> dataList;

    public Configuration(String config){
        this.dataList = new ArrayList<>();
        this.config = config;
    }
    
    /**
     * @return the config
     */
    public String getConfig() {
        return config;
    }

    /**
     * @param config the config to set
     */
    public void setConfig(String config) {
        this.config = config;
    }

    /**
     * @return the dataList
     */
    public ArrayList<Data> getDataList() {
        return dataList;
    }
   
    public void addData(Data data){
        this.dataList.add(data);
    }
    
}
