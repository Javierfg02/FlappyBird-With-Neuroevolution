package Flappy;

import javafx.scene.layout.Pane;

import java.io.*;
import java.util.ArrayList;

public class ComputerBird extends Bird {
    private Matrix syn0; // holds the weights between the input layer and the hidden layer
    private Matrix syn1; // holds the weights between the hidden layer and the output layer
    private double[] inputNodes;
    private ArrayList<String> beginningFileLines;
    private ArrayList<String> endFileLines;
    private File file;

    public ComputerBird(Pane flappyPane) {
        super(flappyPane);
        this.beginningFileLines = new ArrayList<>();
        this.endFileLines = new ArrayList<>();
        this.inputNodes = new double[2];
        this.syn0 = new Matrix(1, 2);
        this.syn1 = new Matrix(1, 1);
        this.giveBirdWeights();
    }

    private void giveBirdWeights() {
        this.file = new File("/Users/javier/IdeaProjects/FlappyBird/output.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader("/Users/javier/IdeaProjects/FlappyBird/output.txt"));

            if (this.file.length() == 0) {
                // if there are no recorded weights then we want to randomize the weights (starting case)
                this.syn0.randomizeWeights();
                this.syn1.randomizeWeights();
                bufferedReader.close();
                // if there are recorded weights then we want to get those weights
            } else {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    this.beginningFileLines.add(line);
                }
                this.mutateWeights();
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void IOFileHandler(double fitness) {
        // Read the file
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader("/Users/javier/IdeaProjects/FlappyBird/output.txt"));
            // if the first line of the file is empty (meaning nothing is written on the file) then write the
            // bird's fitness and weights
            if (this.file.length() == 0) {
                this.writeFile(true, fitness);
                bufferedReader.close();
            } else {
                // the reader will return null once it gets to a line that does not have any input
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    this.endFileLines.add(line);
                }
                this.writeFile(false, fitness);
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFile(boolean isFileNull, double fitness) {
        try {
            if (isFileNull) {
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new FileWriter("/Users/javier/IdeaProjects/FlappyBird/output.txt"));
                // write the fitness as the first line
                bufferedWriter.write(fitness + "\n");

                // write the input weights as the second and third lines
                ArrayList<Double> syn0 = this.syn0.toArray();
                bufferedWriter.write(syn0.get(0) + "\n");
                bufferedWriter.write(syn0.get(1) + "\n");

                // write the output weights as the fourth line
                ArrayList<Double> syn1 = this.syn1.toArray();
                bufferedWriter.write(String.valueOf(syn1.get(0)));

                // close the file
                bufferedWriter.close();
            } else {
                if (fitness > Double.parseDouble(this.endFileLines.get(FlapConstants.IO_FITNESS))) {
                    BufferedWriter bufferedWriter = new BufferedWriter(
                            new FileWriter("/Users/javier/IdeaProjects/FlappyBird/output.txt"));
                    bufferedWriter.write(fitness + "\n");
                    System.out.println("Fitness beaten or equaled");

                    ArrayList<Double> syn0 = this.syn0.toArray();
                    bufferedWriter.write(syn0.get(0) + "\n");
                    bufferedWriter.write(syn0.get(1) + "\n");

                    ArrayList<Double> syn1 = this.syn1.toArray();
                    bufferedWriter.write(String.valueOf(syn1.get(0)));

                    bufferedWriter.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mutateWeights() {
        double rand = Math.random();
        if (rand > 0.2) {

            double input1Mutation = Math.random() * 2 - 1;
            double input2Mutation = Math.random() * 2 - 1;
            double outputMutation = Math.random() * 2 - 1;

            double[][] syn0Data = new double[this.syn0.getRows()][this.syn0.getCols()];
            double inputWeight1 = Double.parseDouble(this.beginningFileLines.get(1));
            double inputWeight2 = Double.parseDouble(this.beginningFileLines.get(2));
            if (inputWeight1 + input1Mutation < 1 && inputWeight1 + input1Mutation > -1) {
                inputWeight1 = inputWeight1 + input1Mutation;
            }
            if (inputWeight2 + input2Mutation < 1 && inputWeight2 + input2Mutation > -1) {
                inputWeight2 = inputWeight2 + input2Mutation;
            }

            syn0Data[this.syn0.getRows() - 1][this.syn0.getCols() - 2] = inputWeight1;
            syn0Data[this.syn0.getRows() - 1][this.syn0.getCols() - 1] = inputWeight2;

            this.syn0.setData(syn0Data);

            double[][] syn1Data = new double[this.syn1.getRows()][this.syn1.getCols()];
            double outputWeight = Double.parseDouble(this.beginningFileLines.get(3));
            if (outputWeight + outputMutation < 1 && outputWeight + outputMutation > -1) {
                outputWeight = outputWeight + outputMutation;
            }

            syn1Data[this.syn1.getRows() - 1][this.syn1.getCols() - 1] = outputWeight;
            this.syn1.setData(syn1Data);
        }
    }

    @Override
    public void flap(double xDistanceToPipe, double yDistanceToPipe) {
        if (this.forwardPropagation(this.inputNodes) > 0.5) {
            this.setCurrentVelocity(FlapConstants.FLAP_VELOCITY);
        }
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

    @Override
    public boolean isBirdManual() {
        return false;
    }
}