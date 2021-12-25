package Flappy;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class ComputerBird extends Bird {
    private Matrix syn0; // holds the weights between the input layer and the hidden layer
    private Matrix syn1; // holds the weights between the hidden layer and the output layer
    private double[] inputNodes;
    private int outputNode; // TODO may not need

    public ComputerBird(Pane flappyPane) {
        super(flappyPane);
        this.inputNodes = new double[1];
        this.outputNode = 1;
        this.syn0 = new Matrix(1, 1);
        this.syn1 = new Matrix(1, 1);
        this.syn0.randomizeWeights();
        this.syn1.randomizeWeights();
    }

    @Override
    public void flap(double xDistanceToPipe, double yDistanceToPipe) {
        this.inputNodes[0] = xDistanceToPipe;
        this.inputNodes[1] = yDistanceToPipe;
        if (this.forwardPropagation(this.inputNodes) > 0.5) {
            this.setCurrentVelocity(FlapConstants.FLAP_VELOCITY);
        }
    }

    public Double forwardPropagation(double[] inputNodes) {
        Matrix inputMatrix = Matrix.fromArray(inputNodes);
        Matrix hidden = Matrix.dotProduct(this.syn0, inputMatrix);
//        hidden.sigmoid(); // applies activation function to the hidden layer

        Matrix output = Matrix.dotProduct(hidden, this.syn1);
//        output.sigmoid();
        ArrayList<Double> outputNode = output.toArray();
        System.out.println(outputNode.get(0));
        return outputNode.get(0); // only have one output node anyway
    }
}