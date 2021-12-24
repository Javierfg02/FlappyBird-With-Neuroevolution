package Flappy;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    private int rows;
    private int cols;
    private double[][] data;

    public Matrix(int rows,int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    public void randomizeWeights() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.data[i][j] = (Math.random() * 2) - 1; // produces a random number between -1 and 1
            }
        }
    }

    /**
     * Method that returns the dot product between two matrices. The result is a matrix of
     * @param a The first matrix
     * @param b The second matrix
     * @return The matrix given by the dot product of the first and the second matrices
     */
    public static Matrix dotProduct(Matrix a, Matrix b) {
        Matrix temp = new Matrix(a.rows, b.cols);
        for (int i = 0; i < temp.rows; i++) {
            for (int j = 0; j < temp.cols; j++) {
                double sum = 0;
                for (int k = 0; k < a.cols; k++) {
                    sum += a.data[i][k] * b.data[k][j];
                }
                temp.data[i][j] = sum;
            }
        }
        return temp;
    }

    /**
     * Method that applies the sigmoid function to the matrix
     */
    public void sigmoid() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.data[i][j] = 1/(1 + Math.exp(-this.data[i][j]));
            }
        }
    }

    /**
     * Converts an array into a matrix
     * @param x an array which we want to convert into a matrix
     * @return A matrix form of the array
     */
    public static Matrix fromArray(double[] x) {
        Matrix temp = new Matrix(x.length, 1);
        for (int i = 0; i < x.length; i++) {
            temp.data[i][0] = x[i];
        }
        return temp;
    }

    /**
     * Converts the matrix into an Array
     * @return an Array
     */
    public List<Double> toArray() {
        List<Double> temp = new ArrayList<Double>();

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                temp.add(this.data[i][j]);
            }
        }
        return temp;
    }

    public double[][] getData() {
        return this.data;
    }
}
