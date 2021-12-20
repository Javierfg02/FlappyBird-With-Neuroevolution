package Flappy;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
     import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Objects;

public class Bird {
    private Circle birdBody;
    private double currentVelocity;

    public Bird(Pane flappyPane) {
        this.birdBody = new Circle(FlapConstants.BIRD_RADIUS,
                0, FlapConstants.BIRD_RADIUS);
        this.currentVelocity = 0;
        this.makeRoadManBird();
        flappyPane.getChildren().add(this.birdBody);
    }

    public void gravity() {
        double updatedVelocity = this.currentVelocity + (FlapConstants.DURATION * FlapConstants.GRAVITY);
        double updatedPosition = this.getBirdY() + (updatedVelocity * FlapConstants.DURATION);
        this.currentVelocity = updatedVelocity;
        this.setYLoc(updatedPosition);
    }

    private void makeRoadManBird() {
        Image image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("roadmanBird.png")));
        ImagePattern imagePattern = new ImagePattern(image);
        this.birdBody.setFill(imagePattern);
    }

    public void setYLoc(double y) {
        this.birdBody.setCenterY(y);
    }

    public double getBirdY() {
        return this.birdBody.getCenterY();
    }

    public void setCurrentVelocity(double v) {
        this.currentVelocity = v;
    }

}
