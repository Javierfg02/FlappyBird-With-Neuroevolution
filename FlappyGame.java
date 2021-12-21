package Flappy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class FlappyGame {
    private Pane flappyPane;
    private Bird bird;
    private Timeline timeline;
    private ArrayList<Pipe> pipeStorage;
    private Pipe firstPipe;

    public FlappyGame(Pane flappyPane) {
        this.flappyPane = flappyPane;
        this.pipeStorage = new ArrayList<>();
        this.setUpTimeline();
        this.firstPipe = new Pipe(flappyPane, 1500, 500);
        System.out.println(this.firstPipe);
        this.pipeStorage.add(this.firstPipe);
    }

    public void setPlayers(int gameMode) {
        if (gameMode == FlapConstants.MANUAL_GAME_MODE) {
            this.bird = new ManualBird(this.flappyPane);
        } else if (gameMode == FlapConstants.COMPUTER_GAME_MODE) {
            this.bird = new ComputerBird(this.flappyPane);
        }
    }

    private void setUpTimeline() {
        KeyFrame kf = new KeyFrame(Duration.seconds(FlapConstants.DURATION),
                (ActionEvent e) -> this.updateTimeline());
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    private void updateTimeline() {
        if (this.bird != null) { // before applying settings the bird is null.
            this.bird.flap();
            this.bird.gravity();
            this.keepBirdInScreen();
            this.bird.checkIntersection(this.nearestPipe());
            this.scrollPipes();
            this.createPipes();
            this.deletePipes();
        }
    }

    // TODO shouldn't stay on the ground, it should die.
    private void keepBirdInScreen() {
        if (this.bird.getBirdY() > (FlapConstants.FLAPPY_PANE_HEIGHT)) {
            this.bird.setCurrentVelocity(0);
            this.bird.setBirdY(FlapConstants.FLAPPY_PANE_HEIGHT);
        }
    }

    private void scrollPipes() {
        for (Pipe pipe : this.pipeStorage) {
            pipe.scrollPipes();
        }
    }

    private void createPipes() {
        Pipe nearestPipe = nearestPipe(); // TODO change
        while (this.pipeStorage.size() <= 1) {
            double yPos = FlapConstants.PIPE_HIGH_BOUND +
                    ((FlapConstants.PIPE_LOW_BOUND - FlapConstants.PIPE_HIGH_BOUND) * Math.random());

            double xPos = ((nearestPipe.getPipeX() + FlapConstants.PIPE_X_SPACING));

            Pipe nextPipe = new Pipe(this.flappyPane, xPos, yPos);
            this.pipeStorage.add(nextPipe);
        }
    }

    // TODO create method to get the nearest pipe
    private Pipe nearestPipe() {
        double distanceToBird = FlapConstants.LARGE_NUMBER;
        Pipe nearestPipe = null;
        for (Pipe pipe : this.pipeStorage) {
            double updatedDistanceToBird = pipe.getPipeX() - this.bird.getBirdX();
            if (updatedDistanceToBird < distanceToBird) {
                distanceToBird = updatedDistanceToBird;
                nearestPipe = pipe;
            }
        }
        return nearestPipe;
    }

    private void deletePipes() {
        for (int i = 0; i < this.pipeStorage.size(); i++) {
            Pipe pipe = this.pipeStorage.get(i);
            if (pipe.getPipeX() < -1 * FlapConstants.PIPE_WIDTH) {
                pipe.removeFromPane();
                this.pipeStorage.remove(pipe);
            }
        }
    }
}
