package Flappy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.ArrayList;

public class FlappyGame {
    private final Pane flappyPane;
    private Bird bird;
    private Timeline timeline;
    private final ArrayList<Pipe> pipeStorage;
    private int score;
    private Label scoreLabel;

    public FlappyGame(Pane flappyPane) {
        this.flappyPane = flappyPane;
        this.pipeStorage = new ArrayList<>();
        this.setUpTimeline();
        this.createFirstPipe();
        this.createScoreLabel();
        this.score = 0;
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
            if (this.bird.checkIntersection(this.nearestPipe())) {
                this.gameOver();
            }
            this.updateScore();
            this.scrollPipes();
            this.createPipes();
            this.deletePipes();
        }
    }

    private void createScoreLabel() {
        this.scoreLabel = new Label("0");
        this.scoreLabel.setStyle("-fx-font: italic bold 75px arial, serif;-fx-text-fill: #ffd007;");
        this.scoreLabel.setTextFill(Color.BLACK);
        this.scoreLabel.setLayoutX((FlapConstants.APP_WIDTH / 2) - 18);
        this.scoreLabel.setLayoutY(75);
        this.flappyPane.getChildren().add(this.scoreLabel);
        this.scoreLabel.toFront();
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

    private void createFirstPipe() {
        double yPos = FlapConstants.PIPE_HIGH_BOUND +
                ((FlapConstants.PIPE_LOW_BOUND - FlapConstants.PIPE_HIGH_BOUND) * Math.random());

        Pipe pipe = new Pipe(this.flappyPane, FlapConstants.FIRST_PIPE_X, yPos);
        this.pipeStorage.add(pipe);
    }

    private void createPipes() {
        Pipe nearestPipe = nearestPipe();
        while (this.pipeStorage.size() <= 1) {
            double yPos = FlapConstants.PIPE_HIGH_BOUND +
                    ((FlapConstants.PIPE_LOW_BOUND - FlapConstants.PIPE_HIGH_BOUND) * Math.random());

            double xPos = ((nearestPipe.getPipeX() + FlapConstants.PIPE_X_SPACING));

            Pipe nextPipe = new Pipe(this.flappyPane, xPos, yPos);
            this.pipeStorage.add(nextPipe);
        }
    }

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

    // TODO if you can find a way to update the score that is not so dodgy then feel free to replace this. But, this method works.
    private void updateScore() {
        Pipe nearestPipe = this.nearestPipe();
        if ((this.bird.getBirdX() > (nearestPipe.getPipeX() - 2)) &&
                (this.bird.getBirdX() < (nearestPipe.getPipeX() + 2))) {
            this.score++;
            this.scoreLabel.setText(String.valueOf(this.score));
        }
    }

    private void gameOver() {
        // TODO add animation for bird dying.
        this.timeline.stop(); // stops timeline
        Label label = new Label("Wasted");
        VBox labelBox = new VBox(label);
        labelBox.setAlignment(Pos.CENTER);
        labelBox.setPrefHeight(this.flappyPane.getHeight());
        labelBox.setPrefWidth(this.flappyPane.getWidth());
        label.setStyle("-fx-font: italic bold 75px arial, serif;-fx-text-alignment: center;-fx-text-fill: #ff0000;");
        Color[] colors = new Color[]{Color.LIGHTGREY, Color.GREY, Color.DARKGREY, Color.BLACK};
        DropShadow shadow = new DropShadow(BlurType.GAUSSIAN, Color.LIGHTCORAL, 0.0D, 10.0D, 2.0D, 2.0D);

        for (Color color : colors) { // loops through to add a 3D effect to the letters
            DropShadow temp = new DropShadow(BlurType.GAUSSIAN, color, 0.0D, 10.0D, 2.0D, 2.0D);
            temp.setInput(shadow);
            shadow = temp;
        }

        label.setEffect(shadow);
        this.flappyPane.getChildren().add(labelBox);
        this.flappyPane.setOnKeyPressed(null); // makes doodle unresponsive to key input
        labelBox.setFocusTraversable(false);
    }
}

