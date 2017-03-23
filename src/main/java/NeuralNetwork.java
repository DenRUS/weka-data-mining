import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.*;
import java.util.Random;

/**
 * Created by Timur on 14.03.2017.
 */
public class NeuralNetwork {

    public void simpleWekaTrain(String trainFile, String predictedData, String output) {
        try {
            //Reading training arff or csv file
            CSVLoader loader = new CSVLoader();
            loader.setSource(new File(trainFile));
            Instances train = loader.getDataSet();
//            FileReader trainreader = new FileReader(trainFile);
//            Instances train = new Instances(trainreader);
            train.setClassIndex(train.numAttributes() - 1);


            //Instance of NN
            MultilayerPerceptron mlp = new MultilayerPerceptron();
            //Setting Parameters
            mlp.setLearningRate(0.1);
            mlp.setMomentum(0.2);
            mlp.setTrainingTime(2000);
            mlp.setHiddenLayers("3");
            mlp.buildClassifier(train);
            Evaluation eval = new Evaluation(train);
            eval.evaluateModel(mlp, train);
            System.out.println(eval.errorRate()); //Printing Training Mean root squared Error
            System.out.println(eval.toSummaryString()); //Summary of Training


//            eval.crossValidateModel(mlp, train, kfolds, new Random(1));

            Instances datapredict = new Instances(new BufferedReader(new FileReader(predictedData)));
            datapredict.setClassIndex(datapredict.numAttributes() - 1);
            Instances predicteddata = new Instances(datapredict);
            //Predict Part
            for (int i = 0; i < datapredict.numInstances(); i++) {
                double clsLabel = mlp.classifyInstance(datapredict.instance(i));
                predicteddata.instance(i).setClassValue(clsLabel);
            }
                //Storing again in arff
//            BufferedWriter writer = new BufferedWriter(new FileWriter(output));
//            writer.write(predicteddata.toString());
//            writer.newLine();
//            writer.flush();
//            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {

        NeuralNetwork neuralNetwork = new NeuralNetwork();
        neuralNetwork.simpleWekaTrain("x_train.csv", "y_train.csv", null);

    }
}
