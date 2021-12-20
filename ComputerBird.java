package Flappy;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class ComputerBird implements Flappable {
    private Pane flappyPane;
    private Bird bird;

    public ComputerBird(Pane flappyPane) {
        this.flappyPane = flappyPane;
        this.bird = new Bird(flappyPane);
    }

    @Override
    public void flap() { }

    @Override
    public void gravity() {
        this.bird.gravity();
    }

    @Override
    public double getBirdY() {
        return this.bird.getBirdY();
    }

    @Override
    public void setBirdY(double y) {
        this.bird.setYLoc(y);
    }

    @Override
    public void setCurrentVelocity(double v) {
        this.bird.setCurrentVelocity(v);
    }

    @Override
    public boolean checkIntersection(Pipe pipe) {
        return this.bird.checkIntersection(pipe);
    }
}
