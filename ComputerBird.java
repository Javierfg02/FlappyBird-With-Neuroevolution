package Flappy;

import javafx.scene.layout.Pane;

import java.io.*; // gives us access to io files
import java.util.ArrayList;

public class ComputerBird extends Bird {
    private Matrix syn0; // holds the weights between the input layer and the hidden layer
    private Matrix syn1; // holds the weights between the hidden layer and the output layer
    private double[] inputNodes;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private ArrayList<String> readFile;

    public ComputerBird(Pane flappyPane) {
        super(flappyPane);
        this.readFile = new ArrayList<>();
        this.inputNodes = new double[2];
        this.syn0 = new Matrix(1, 2);
        this.syn1 = new Matrix(1, 1);
        this.syn0.randomizeWeights();
        this.syn1.randomizeWeights();
    }

    @Override
    public boolean isBirdManual() {
        return false;
    }

    @Override
    public void IOFileHandler(double Fitness) {
        // if the file exists
        if (new File("/Users/javier/IdeaProjects/FlappyBird/output.txt").isFile()) {

            // Read the file
            try {
                this.bufferedReader = new BufferedReader(new FileReader("/Users/javier/IdeaProjects/FlappyBird/output.txt"));
                // the reader will return null once it gets to a line that does not have any input
                String line;
                while ((line = this.bufferedReader.readLine()) != null) {
                    this.readFile.add(line);
                    System.out.println(this.readFile.get(0));
                }
                this.bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Write the file
        try {
            this.bufferedWriter = new BufferedWriter(new FileWriter("/Users/javier/IdeaProjects/FlappyBird/output.txt"));
            this.bufferedWriter.write("File exists");
            this.bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void flap(double xDistanceToPipe, double yDistanceToPipe) {
        this.inputNodes[0] = this.normalizeInputs(xDistanceToPipe, 0, FlapConstants.PIPE_X_SPACING);
        this.inputNodes[1] = this.normalizeInputs(yDistanceToPipe, 0,
                FlapConstants.APP_HEIGHT - FlapConstants.CONTROLS_PANE_HEIGHT);
        if (this.forwardPropagation(this.inputNodes) > 0.5) {
            this.setCurrentVelocity(FlapConstants.FLAP_VELOCITY);
        }
    }

    private double normalizeInputs(double v, double min, double max) {
        return (v - min)/(max - min);
    }

    public Double forwardPropagation(double[] inputNodes) {
        Matrix inputMatrix = Matrix.fromArray(inputNodes);
        Matrix hidden = Matrix.dotProduct(this.syn0, inputMatrix);
        hidden.sigmoid(); // applies activation function to the hidden layer

        Matrix output = Matrix.dotProduct(this.syn1, hidden);
        output.sigmoid();
        ArrayList<Double> outputNode = output.toArray();
//        System.out.println("output: " + outputNode.get(0));

        return outputNode.get(0); // only have one output node anyway
    }


}