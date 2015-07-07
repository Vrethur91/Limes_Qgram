package limes_qgram;

import java.util.ArrayList;

/**
 *
 * @author Bene
 */
public class Evaluation {
    
    public static void main(String[] args) {
        Evaluation eva = new Evaluation();
        //eva.plotThetaAgainstRuntime();
        eva.plotSizeAgainstRuntime();
    }
    
    public void runEvaluation(){
        
    }
    
    private void plotSizeAgainstRuntime(){
        System.out.println("Size X Runtime");
        ArrayList<Configuration> configs = new ArrayList<>();
        
        configs.add(new Configuration("Basic - - "));
        configs.add(new Configuration("Basic + - "));
        configs.add(new Configuration("Basic - + "));
        configs.add(new Configuration("Basic + + "));
        configs.add(new Configuration("Ends - - "));
        configs.add(new Configuration("Ends + - "));
        configs.add(new Configuration("Ends - + "));
        configs.add(new Configuration("Ends + + "));

        FileSampler sampler = new FileSampler();
        sampler.preloadList("./data/labels.txt");
        
        double theta = 0.6;
        
        LimesQgram qgram = new LimesQgram();
        qgram.setQ(3);
        qgram.setThreshold(theta);
        int listSize = 0;
        for(int i = 0;i<3;i++){
            if(i==0){
                listSize = 500;
            } else{
                listSize *= 10;
            }
            System.out.println(listSize);
            ArrayList<String> list1 = sampler.getSample(listSize);
            ArrayList<String> list2 = sampler.getSample(listSize);
            double[] values;
            System.out.println("0%");
            
            //----------------------------------------------
            qgram.setTokenizer(LimesQgram.Tokenizer.BASIC);
            qgram.setBounds(LimesQgram.Bounds.NO_BOUNDS);
            qgram.setValidation(LimesQgram.Validation.ALWAYS_TRUE);

            values = qgram.computeSimilarities(list1, list2);
            configs.get(0).addData(new Data(theta, values[0], (int)values[1], listSize));
            System.out.println("12,5%");
            
            //----------------------------------------------
            qgram.setTokenizer(LimesQgram.Tokenizer.BASIC);
            qgram.setBounds(LimesQgram.Bounds.BASIC);
            qgram.setValidation(LimesQgram.Validation.ALWAYS_TRUE);

            values = qgram.computeSimilarities(list1, list2);
            configs.get(1).addData(new Data(theta, values[0], (int)values[1], listSize));
            System.out.println("25%");
            
            //----------------------------------------------
            qgram.setTokenizer(LimesQgram.Tokenizer.BASIC);
            qgram.setBounds(LimesQgram.Bounds.NO_BOUNDS);
            qgram.setValidation(LimesQgram.Validation.BASIC);

            values = qgram.computeSimilarities(list1, list2);
            configs.get(2).addData(new Data(theta, values[0], (int)values[1], listSize));
            System.out.println("37,5%");
            
            //----------------------------------------------
            qgram.setTokenizer(LimesQgram.Tokenizer.BASIC);
            qgram.setBounds(LimesQgram.Bounds.BASIC);
            qgram.setValidation(LimesQgram.Validation.BASIC);

            values = qgram.computeSimilarities(list1, list2);
            configs.get(3).addData(new Data(theta, values[0], (int)values[1], listSize));
            System.out.println("50%");
            
            //----------------------------------------------
            qgram.setTokenizer(LimesQgram.Tokenizer.ENDS);
            qgram.setBounds(LimesQgram.Bounds.NO_BOUNDS);
            qgram.setValidation(LimesQgram.Validation.ALWAYS_TRUE);

            values = qgram.computeSimilarities(list1, list2);
            configs.get(4).addData(new Data(theta, values[0], (int)values[1], listSize));
            System.out.println("62,5%");
            
            //----------------------------------------------
            qgram.setTokenizer(LimesQgram.Tokenizer.ENDS);
            qgram.setBounds(LimesQgram.Bounds.ENDS);
            qgram.setValidation(LimesQgram.Validation.ALWAYS_TRUE);

            values = qgram.computeSimilarities(list1, list2);
            configs.get(5).addData(new Data(theta, values[0], (int)values[1], listSize));
            System.out.println("75%");
            
            //----------------------------------------------
            qgram.setTokenizer(LimesQgram.Tokenizer.ENDS);
            qgram.setBounds(LimesQgram.Bounds.NO_BOUNDS);
            qgram.setValidation(LimesQgram.Validation.ENDS);

            values = qgram.computeSimilarities(list1, list2);
            configs.get(6).addData(new Data(theta, values[0], (int)values[1], listSize));
            System.out.println("87,5%");
            
            //----------------------------------------------
            qgram.setTokenizer(LimesQgram.Tokenizer.ENDS);
            qgram.setBounds(LimesQgram.Bounds.ENDS);
            qgram.setValidation(LimesQgram.Validation.ENDS);

            values = qgram.computeSimilarities(list1, list2);
            configs.get(7).addData(new Data(theta, values[0], (int)values[1], listSize));
            System.out.println("100%");
        }
        PlotWriter.writePlotSizeAgainstRuntime(configs);
    }

    private void plotThetaAgainstRuntime(){
        System.out.println("Theta X Runtime");
        ArrayList<Configuration> configs = new ArrayList<>();
        
        configs.add(new Configuration("Basic - - "));
        configs.add(new Configuration("Basic + - "));
        configs.add(new Configuration("Basic - + "));
        configs.add(new Configuration("Basic + + "));
        configs.add(new Configuration("Ends - - "));
        configs.add(new Configuration("Ends + - "));
        configs.add(new Configuration("Ends - + "));
        configs.add(new Configuration("Ends + + "));

        FileSampler sampler = new FileSampler();
        
        LimesQgram qgram = new LimesQgram();
        qgram.setQ(3);
            
        ArrayList<String> list1 = sampler.loadList("./data/drugbank.txt");
        ArrayList<String> list2 = list1;
        int listSize = list1.size();
        Double[] thetaList = {0.6,0.7,0.8,0.9};
        
        for(int i = 0;i<thetaList.length;i++){
            double theta = thetaList[i];
            qgram.setThreshold(theta);
            System.out.println(theta);
            double[] values;
            
            //----------------------------------------------
            qgram.setTokenizer(LimesQgram.Tokenizer.BASIC);
            qgram.setBounds(LimesQgram.Bounds.NO_BOUNDS);
            qgram.setValidation(LimesQgram.Validation.ALWAYS_TRUE);

            values = qgram.computeSimilarities(list1, list2);
            configs.get(0).addData(new Data(theta, values[0], (int)values[1], listSize));
            
            //----------------------------------------------
            qgram.setTokenizer(LimesQgram.Tokenizer.BASIC);
            qgram.setBounds(LimesQgram.Bounds.BASIC);
            qgram.setValidation(LimesQgram.Validation.ALWAYS_TRUE);

            values = qgram.computeSimilarities(list1, list2);
            configs.get(1).addData(new Data(theta, values[0], (int)values[1], listSize));
            
            //----------------------------------------------
            qgram.setTokenizer(LimesQgram.Tokenizer.BASIC);
            qgram.setBounds(LimesQgram.Bounds.NO_BOUNDS);
            qgram.setValidation(LimesQgram.Validation.BASIC);

            values = qgram.computeSimilarities(list1, list2);
            configs.get(2).addData(new Data(theta, values[0], (int)values[1], listSize));
            
            //----------------------------------------------
            qgram.setTokenizer(LimesQgram.Tokenizer.BASIC);
            qgram.setBounds(LimesQgram.Bounds.BASIC);
            qgram.setValidation(LimesQgram.Validation.BASIC);

            values = qgram.computeSimilarities(list1, list2);
            configs.get(3).addData(new Data(theta, values[0], (int)values[1], listSize));
            
            //----------------------------------------------
            qgram.setTokenizer(LimesQgram.Tokenizer.ENDS);
            qgram.setBounds(LimesQgram.Bounds.NO_BOUNDS);
            qgram.setValidation(LimesQgram.Validation.ALWAYS_TRUE);

            values = qgram.computeSimilarities(list1, list2);
            configs.get(4).addData(new Data(theta, values[0], (int)values[1], listSize));
            
            //----------------------------------------------
            qgram.setTokenizer(LimesQgram.Tokenizer.ENDS);
            qgram.setBounds(LimesQgram.Bounds.ENDS);
            qgram.setValidation(LimesQgram.Validation.ALWAYS_TRUE);

            values = qgram.computeSimilarities(list1, list2);
            configs.get(5).addData(new Data(theta, values[0], (int)values[1], listSize));
            
            //----------------------------------------------
            qgram.setTokenizer(LimesQgram.Tokenizer.ENDS);
            qgram.setBounds(LimesQgram.Bounds.NO_BOUNDS);
            qgram.setValidation(LimesQgram.Validation.ENDS);

            values = qgram.computeSimilarities(list1, list2);
            configs.get(6).addData(new Data(theta, values[0], (int)values[1], listSize));
            
            //----------------------------------------------
            qgram.setTokenizer(LimesQgram.Tokenizer.ENDS);
            qgram.setBounds(LimesQgram.Bounds.ENDS);
            qgram.setValidation(LimesQgram.Validation.ENDS);

            values = qgram.computeSimilarities(list1, list2);
            configs.get(7).addData(new Data(theta, values[0], (int)values[1], listSize));
            
        }
        PlotWriter.writePlotThetaAgainstRuntime(configs);
    }
    
    public double[] beta(){
        double[] returnValues = {50.0, 1.0};
        return returnValues;
    }
}
