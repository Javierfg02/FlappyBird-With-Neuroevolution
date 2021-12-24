package Flappy;

import javafx.scene.layout.Pane;

public class ComputerBird extends Bird {
    private Matrix syn0; // holds the weights between the input layer and the hidden layer
    private Matrix syn1; // holds the weights between the hidden layer and the output layer
    private double[] inputNodes;
    private double[] hiddenNodes;
    private double[] outputNodes;

    public ComputerBird(Pane flappyPane) {
        super(flappyPane);
        this.inputNodes = new double[1]; // TODO change to size 2 later
        this.outputNodes = new double[1];
        this.syn0 = new Matrix(1, 0); // start by creating a (1,1,1) neural network for simplicity
        this.syn1 = new Matrix(1, 0);
        this.inputNodes[0] = this.getXDistanceToPipe();
    }

    @Override
    public void flap() { }

    public double forwardPropagation(double[] inputNodes) {

    }

}