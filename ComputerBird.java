package Flappy;

import javafx.scene.layout.Pane;

import java.io.*;
import java.util.ArrayList;

public class ComputerBird extends Bird {
    private Matrix syn0; // holds the weights between the input layer and the hidden layer
    private Matrix syn1; // holds the weights between the hidden layer and the output layer
    private double[] inputNodes;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private ArrayList<String> fileLines;

    public ComputerBird(Pane flappyPane) {
        super(flappyPane);
        this.fileLines = new ArrayList<>();
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
    public void IOFileHandler(double fitness) {
        // if the file exists
        if (new File("/Users/javier/IdeaProjects/FlappyBird/output.txt").isFile()) {
            // Read the file
            try {
                this.bufferedReader = new BufferedReader(new FileReader("/Users/javier/IdeaProjects/FlappyBird/output.txt"));
                // if the first line of the file is empty (meaning nothing is written on the file) then write the
                // bird's fitness and weights
                if (this.bufferedReader.readLine() == null) {
                    this.writeFile(true, fitness);
                    this.bufferedReader.close();
                } else {
                    // the reader will return null once it gets to a line that does not have any input
                    String line;
                    while ((line = this.bufferedReader.readLine()) != null) {
                        this.fileLines.add(line);
                    }
                    this.writeFile(false, fitness);
                    this.bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeFile(boolean isFileNull, double fitness) {
        try {
            this.bufferedWriter = new BufferedWriter(new FileWriter("/Users/javier/IdeaProjects/FlappyBird/output.txt"));
            if (isFileNull) {
                // write the fitness as the first line
                this.bufferedWriter.write(fitness + "\n");

                // write the input weights as the second and third lines
                ArrayList<Double> syn0 = this.syn0.toArray();
                this.bufferedWriter.write(syn0.get(FlapConstants.IO_INPUT_WEIGHT_1) + "\n");
                this.bufferedWriter.write(syn0.get(FlapConstants.IO_INPUT_WEIGHT_2) + "\n");

                // write the output weights as the fourth line
                ArrayList<Double> syn1 = this.syn1.toArray();
                this.bufferedWriter.write(String.valueOf(syn1.get(FlapConstants.IO_OUTPUT_WEIGHT)));

                // close the file
                this.bufferedWriter.close();
            } else {
                if (fitness >= Double.parseDouble(this.fileLines.get(FlapConstants.IO_FITNESS))) {
                    this.bufferedWriter.write(fitness + "\n");
                    System.out.println("Fitness beaten or equaled");

                    ArrayList<Double> syn0 = this.syn0.toArray();
                    this.bufferedWriter.write(syn0.get(FlapConstants.IO_INPUT_WEIGHT_1) + "\n");
                    this.bufferedWriter.write(syn0.get(FlapConstants.IO_INPUT_WEIGHT_2) + "\n");

                    ArrayList<Double> syn1 = this.syn1.toArray();
                    this.bufferedWriter.write(String.valueOf(syn1.get(FlapConstants.IO_OUTPUT_WEIGHT)));

                    this.bufferedWriter.close();
                }
            }
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