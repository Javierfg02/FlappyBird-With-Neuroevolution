package Flappy;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.util.Objects;

public class Bird implements Flappable {
    private Circle birdBody;
    private double currentVelocity;
    private double fitness;

    public Bird(Pane flappyPane) {
        this.birdBody = new Circle(FlapConstants.BIRD_STARTING_X,
                0, FlapConstants.BIRD_RADIUS);
        this.currentVelocity = 0;
        this.makeRoadManBird("roadmanBird.png");
        flappyPane.getChildren().add(this.birdBody);
    }

    /**
     * No need to define method here
     */
    @Override
    public void flap(double xDistanceToPipe, double yDistanceToPipe) { }

    @Override
    public boolean isBirdManual() {
        return false;
    }

    public void IOFileHandler(double Fitness) { }

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

     public void keepBirdInScreen() {
        if (this.birdBody.getCenterY() > (FlapConstants.FLAPPY_PANE_HEIGHT - (FlapConstants.BIRD_RADIUS / 2))) {
            this.setCurrentVelocity(0);
            this.setBirdY((FlapConstants.FLAPPY_PANE_HEIGHT - (FlapConstants.BIRD_RADIUS / 2)));
        } else if (this.birdBody.getCenterY() < FlapConstants.BIRD_RADIUS / 2 ) {
            this.setCurrentVelocity(0);
            this.setBirdY(FlapConstants.BIRD_RADIUS / 2);
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

    public void rotateBird() {
        // if the velocity is positive (bird going downwards), then we want the bird to point down.
        if (this.currentVelocity > 0) {
            double normalizedVelocity = this.normalizeVelocity(this.currentVelocity, 0, 1200);
            double angle = (normalizedVelocity * 90);
            if (angle < 90) {
                this.birdBody.setRotate(angle);
            }
        } else if (this.currentVelocity < 0) {
            double normalizedVelocity = this.normalizeVelocity(this.currentVelocity, 0, 500);
            double angle = normalizedVelocity * 45;
            this.birdBody.setRotate(angle);
        } else {
            this.birdBody.setRotate(0);
        }
    }

    private double normalizeVelocity(double v, double min, double range) {
        return (v - min)/range;
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

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return this.fitness;
    }
}
