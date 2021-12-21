package Flappy;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.util.Objects;

public class Bird implements Flappable {
    private final Circle birdBody;
    private double currentVelocity;

    public Bird(Pane flappyPane) {
        this.birdBody = new Circle(FlapConstants.BIRD_RADIUS,
                0, FlapConstants.BIRD_RADIUS);
        this.currentVelocity = 0;
        this.makeRoadManBird();
        flappyPane.getChildren().add(this.birdBody);
    }

    /**
     * No need to define method here
     */
    @Override
    public void flap() { }

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

    /**
     * Method which checks intersection of the bird with the Pipe.
     * @param pipe Takes in the nearest Pipe (that is object of type Pipe).
     * @return Returns true if there is an intersection with either the top or bottom pipe and false if there is not.
     */
    public boolean checkIntersection(Pipe pipe) {
        if ((this.birdBody.intersects(pipe.getTopPipeBounds())) ||
                this.birdBody.intersects(pipe.getBottomPipeBounds())) {
            System.out.println("Intersection!");
            return true;
        } else {
            return false;
        }
    }

    public void setYLoc(double y) {
        this.birdBody.setCenterY(y);
    }

    public double getBirdY() {
        return this.birdBody.getCenterY();
    }

    public void setBirdY(double y) {
        this.birdBody.setCenterY(y);
    }

    public void setCurrentVelocity(double v) {
        this.currentVelocity = v;
    }
}
