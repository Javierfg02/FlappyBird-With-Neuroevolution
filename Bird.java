package Flappy;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.util.Objects;

public class Bird implements Flappable {
    private final Circle birdBody;
    private double currentVelocity;

    public Bird(Pane flappyPane) {
        this.birdBody = new Circle((FlapConstants.APP_WIDTH / 2) - 50,
                0, FlapConstants.BIRD_RADIUS);
        this.currentVelocity = 0;
        this.makeRoadManBird("roadmanBird.png");
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

    private void makeRoadManBird(String picture) {
        Image image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(picture)));
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
            return true;
        } else {
            return false;
        }
    }

    public void dropDead() {
        TranslateTransition dropDead = new TranslateTransition();
        this.makeRoadManBird("deadFlappy.png");
        dropDead.setNode(this.birdBody);
        double height = FlapConstants.APP_HEIGHT - this.birdBody.getCenterY() - FlapConstants.CONTROLS_PANE_HEIGHT -
                FlapConstants.BIRD_RADIUS / 2;
        dropDead.setDuration(Duration.millis(1000));
        dropDead.setCycleCount(1);
        dropDead.setByY(height);
        dropDead.play();
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

    public double getBirdX() {
        return this.birdBody.getCenterX();
    }

    public void setCurrentVelocity(double v) {
        this.currentVelocity = v;
    }
}
