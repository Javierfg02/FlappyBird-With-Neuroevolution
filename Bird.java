package Flappy;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
     import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Objects;

public class Bird {
    private Pane flappyPane;
    private Circle birdBody;
    private double currentVelocity;

    public Bird(Pane flappyPane) {
        this.flappyPane = flappyPane;
        this.birdBody = new Circle(FlapConstants.BIRD_RADIUS,
                0, FlapConstants.BIRD_RADIUS);
        this.flappyPane.getChildren().add(this.birdBody);
        this.currentVelocity = 0;
        this.makeRoadManBird();
    }

    public void gravity() {
        double updatedVelocity = this.currentVelocity + (FlapConstants.DURATION * FlapConstants.GRAVITY);
        double updatedPosition = this.getBirdY() + (updatedVelocity * FlapConstants.DURATION);
        this.currentVelocity = updatedVelocity;
        this.setYLoc(updatedPosition);
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

    public void makeRoadManBird() {
        Image image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("roadmanBird.png")));
        ImagePattern imagePattern = new ImagePattern(image);
        this.birdBody.setFill(imagePattern);
    }
}
