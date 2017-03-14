import weka.classifiers.trees.J48;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.CSVLoader;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;
import weka.gui.visualize.Plot2D;
import weka.gui.visualize.PlotData2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;


public class ClusteringDemo {


    public void createFrame(Instances data) throws Exception {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int[] pointShapes = null;
        pointShapes = new int[data.numInstances()];
        for (int i = 0; i < data.numInstances(); i++) {
            pointShapes[i] = Plot2D.DIAMOND_SHAPE;
        }
        int[] size = null;
        size = new int[data.numInstances()];
        for (int i = 0; i < data.numInstances(); i++) {
            size[i] = 20;
        }
        Plot2D plot2D = new Plot2D();
        PlotData2D plotData2D = new PlotData2D(data);
        plotData2D.setCustomColour(Color.ORANGE);
        plotData2D.setShapeType(pointShapes);
        plotData2D.setShapeSize(size);
        plot2D.addPlot(plotData2D);
        frame.setPreferredSize(new Dimension(2000, 1000));
        frame.getContentPane().add(plot2D);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public ClusteringDemo(String filename) throws Exception {
        ClusterEvaluation eval;
        Instances data;
        String[] options;
        SimpleKMeans simpleKMeans;


        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(filename));
        data = loader.getDataSet();

        simpleKMeans = new SimpleKMeans();
        simpleKMeans.setNumClusters(2);
        simpleKMeans.buildClusterer(data);


        eval = new ClusterEvaluation();
        eval.setClusterer(simpleKMeans);
        eval.evaluateClusterer(data);

//        ArffLoader arffLoader=new ArffLoader();
//        arffLoader.setSource(new File(filename));
//        data=arffLoader.getDataSet();

//        data.setClassIndex(data.numAttributes() - 1);


        System.out.println("Cluster Evaluation: "+eval.clusterResultsToString());


//        createFrame(data);
    }






    /**
     * usage:
     * DecisionTree arff-file
     */
    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            System.out.println("usage: " + ClusteringDemo.class.getName() + " <arff-file>");
            System.exit(1);
        }

        new ClusteringDemo(args[0]);
    }
}