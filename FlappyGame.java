package Flappy;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Objects;

public class FlappyGame {
    private Pane flappyPane;
    private Bird bird;
    private Timeline timeline;
    private ArrayList<Pipe> pipeStorage;
    private int score;
    private int highScore;
    private Label scoreLabel;
    private Label highScoreLabel;
    private ParallelTransition backgroundController;
    private ArrayList<ComputerBird> computerBirdStorage;
    private double fitness;
    private double xDistanceToPipe;
    private double yDistanceToPipe;
    private Controls controls;

    public FlappyGame(Pane flappyPane) {
        this.flappyPane = flappyPane;
        this.pipeStorage = new ArrayList<>();
        this.computerBirdStorage = new ArrayList<>();
        this.setUpTimeline();
        this.createFirstPipe();
        this.score = 0;
        this.backgroundImage();
    }

    private void backgroundImage() {
        Image backgroundImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(
                "background.png")));
        ImageView background1 = new ImageView(backgroundImage);
        ImageView background2 = new ImageView(backgroundImage);

        this.flappyPane.getChildren().add(background1);
        this.flappyPane.getChildren().add(background2);
        background1.toBack();
        background2.toBack();

        TranslateTransition trans1 = new TranslateTransition(Duration.seconds(15), background1);
        trans1.setFromX(0);
        trans1.setToX(-1 * FlapConstants.BACKGROUND_WIDTH);
        trans1.setInterpolator(Interpolator.LINEAR);

        TranslateTransition trans2 = new TranslateTransition(Duration.seconds(15), background2);
        trans2.setFromX(FlapConstants.BACKGROUND_WIDTH);
        trans2.setToX(0);
        trans2.setInterpolator(Interpolator.LINEAR);

        this.backgroundController = new ParallelTransition(trans1, trans2);
        this.backgroundController.setCycleCount(Animation.INDEFINITE);
        this.backgroundController.play();
    }

    public void setPlayers(int gameMode) {
        if (gameMode == FlapConstants.MANUAL_GAME_MODE) {
            this.bird = new ManualBird(this.flappyPane);
//        } else {
//            for (int i = 0; i < FlapConstants.POPULATION; i++) {
//                ComputerBird computerBird= new ComputerBird(this.flappyPane);
//                this.computerBirdStorage.add(computerBird);
//            }
        } else {
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
        if (this.bird != null) { // before clicking play the bird is null.
            this.fitness++;
            this.bird.setFitness(this.fitness);
            this.updateInputNodes();
            this.bird.flap(this.xDistanceToPipe, this.yDistanceToPipe);
            this.bird.gravity();
            this.bird.rotateBird();
            this.keepBirdInScreen();
            if (this.bird.checkIntersection(this.nearestPipe())) {
                this.gameOver();
            }
            if (this.scoreLabel != null) {
                this.updateScore();
            }
            if (this.highScoreLabel != null) {
                this.updateHighScore();
            }
            this.scrollPipes();
            this.createPipes();
            this.deletePipes();
        }
    }

    private void keepBirdInScreen() {
        this.bird.keepBirdInScreen();
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

    /**
     * Method that will get the nearest Pipe in front of the Bird
     * @return the nearest Pipe in front of the bird.
     */
    private Pipe nearestPipe() {
        double distanceToBird = FlapConstants.LARGE_NUMBER;
        Pipe nearestPipe = null;
        for (Pipe pipe : this.pipeStorage) {
            double updatedDistanceToBird = pipe.getPipeX() - this.bird.getBirdX();
            if (updatedDistanceToBird < distanceToBird &&
                    updatedDistanceToBird > (-1 * (FlapConstants.PIPE_WIDTH/2 + FlapConstants.BIRD_RADIUS))) {
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

    private void updateScore() {
        Pipe nearestPipe = this.nearestPipe();
        if ((this.bird.getBirdX() > (nearestPipe.getPipeX() - 2)) &&
                (this.bird.getBirdX() < (nearestPipe.getPipeX() + 2))) {
            this.score++;
            this.scoreLabel.setText(String.valueOf(this.score));
            this.scoreLabel.toFront();
        }
    }

    private void updateHighScore() {
        this.highScoreLabel.toFront();
        if (this.highScore <= this.score) {
            this.highScore = this.score;
            this.highScoreLabel.setText("High score: " + this.highScore);
        }
    }

    private void gameOver() {
        this.timeline.stop();
        this.backgroundController.stop();
        // if the bird is of type manual:
        if (this.bird.isBirdManual()) {
            this.manualGameOver();
        } else { // if the bird is a computer bird
            this.computerGameOver();
        }
    }

    private void manualGameOver() {
        this.deadAnimation();
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
        this.flappyPane.setOnKeyPressed(null);
        this.flappyPane.getChildren().add(labelBox);
        labelBox.setFocusTraversable(false);
    }

    private void computerGameOver() {
        this.bird.IOFileHandler(this.fitness);
        this.controls.resetHandler();
    }

    void setControls(Controls controls) {
        this.controls = controls;
    }

    private void deadAnimation() {
        this.bird.dropDead();
    }

    private void updateInputNodes() {
        double xDistanceToPipe = (this.nearestPipe().getPipeX() - this.bird.getBirdX()); // always positive anyway
        this.xDistanceToPipe = this.normalizeInputs(xDistanceToPipe,
                (-1 * (FlapConstants.PIPE_WIDTH/2 + FlapConstants.BIRD_RADIUS/2)),
                (FlapConstants.PIPE_X_SPACING  - FlapConstants.BIRD_RADIUS/2 - FlapConstants.PIPE_WIDTH/2));
        System.out.println("xDistance: " + xDistanceToPipe);
        System.out.println(this.xDistanceToPipe);

        double yDistanceToMidpoint = Math.abs(this.nearestPipe().getGapMidpoint() - this.bird.getBirdY());
        double maxDistanceToMidpoint = Math.max(this.nearestPipe().getGapMidpoint(),
                FlapConstants.APP_HEIGHT - FlapConstants.CONTROLS_PANE_HEIGHT - FlapConstants.BIRD_RADIUS/2 -
                        this.nearestPipe().getGapMidpoint());
        this.yDistanceToPipe = this.normalizeInputs(yDistanceToMidpoint, 0, maxDistanceToMidpoint);

    }

    private double normalizeInputs(double v, double min, double max) {
        return (v - min)/(max - min);
    }

    public void setScoreLabel(Label scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    public void setHighScoreLabel(Label highScoreLabel) {
        this.highScoreLabel = highScoreLabel;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getHighScore() {
        return this.highScore;
    }

    public void setRate(double rate) {
        this.timeline.setRate(rate);
        this.backgroundController.setRate(rate);
    }
}