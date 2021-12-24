package Flappy;

import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.List;

public class ComputerBird extends Bird {
    private Matrix syn0; // holds the weights between the input layer and the hidden layer
    private Matrix syn1; // holds the weights between the hidden layer and the output layer
    private double[] inputNodes;
    private double[] outputNodes; // TODO may not need

    public ComputerBird(Pane flappyPane) {
        super(flappyPane);
        this.inputNodes = new double[1]; // TODO change to size 2 later
        this.outputNodes = new double[1];
        this.syn0 = new Matrix(1, 0); // start by creating a (1,1,1) neural network for simplicity
        this.syn1 = new Matrix(1, 0);
        this.syn0.randomizeWeights();
        System.out.println(this.syn0.getData());
        this.syn1.randomizeWeights();
    }

    @Override
    public void flap(double xDistanceToPipe, double yDistanceToPipe) {
        this.inputNodes[0] = xDistanceToPipe;
        System.out.println("input node: " + this.inputNodes[0]);
        this.forwardPropagation(this.inputNodes);
    }

    public List<Double> forwardPropagation(double[] inputNodes) {
        Matrix inputMatrix = Matrix.fromArray(inputNodes);
        Matrix hidden = Matrix.dotProduct(this.syn0, inputMatrix);
        hidden.sigmoid(); // applies activation function to the hidden layer

        Matrix output = Matrix.dotProduct(hidden, this.syn1);
        System.out.println(output);
        List<Double> outputNode = output.toArray();
        System.out.println("outputNode List size: " + outputNode.size());
        return outputNode;
    }

}