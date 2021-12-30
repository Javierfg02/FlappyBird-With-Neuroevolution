package Flappy;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.io.*;

public class Controls {
    private FlappyGame flappyGame;
    private RadioButton[] gameMode;
    private VBox controlsPane;
    private HBox gameModeMenu;
    private boolean settingsApplied;
    private Pane flappyPane;
    private Label scoreLabel;
    private Label highScoreLabel;
    private int highScore;
    private int generation;
    private Label generationLabel;
    private Slider slider;
    private Button reset;
    private File file;
    private String highScoreLine;

    public Controls(FlappyGame flappyGame, Pane flappyPane) {
        this.flappyGame = flappyGame;
        this.flappyPane = flappyPane;
        this.setUpPane();
        this.setupMenu();
        this.setUpButtons();
        this.createHighScoreLabel();
        this.createScoreLabel();
        this.settingsApplied = false;
        this.generation = 0;
        this.readFile();
    }

    public Pane getPane() {
        return this.controlsPane;
    }

    private void setUpPane() {
        this.controlsPane = new VBox();
        this.controlsPane.setPrefHeight(FlapConstants.CONTROLS_PANE_HEIGHT);
        this.controlsPane.setMaxHeight(FlapConstants.CONTROLS_PANE_HEIGHT);
        this.controlsPane.setPrefWidth(FlapConstants.APP_WIDTH);
        this.controlsPane.setSpacing(10);
        this.controlsPane.setPadding(new Insets(10));
        this.controlsPane.setAlignment(Pos.CENTER);
        this.controlsPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,
                CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void setupMenu() {
        this.gameMode = new RadioButton[2];

        this.gameModeMenu = new HBox();
        this.gameModeMenu.setSpacing(15);
        this.gameModeMenu.setAlignment(Pos.BOTTOM_CENTER);
        this.gameModeMenu.setPrefWidth(FlapConstants.APP_WIDTH);

        ToggleGroup toggleGroup = new ToggleGroup();

        // Manual play
        RadioButton manual = new RadioButton("Manual");
        manual.setToggleGroup(toggleGroup);
        manual.setFocusTraversable(true);
        this.gameMode[FlapConstants.MANUAL_GAME_MODE] = manual;

        // Computer play
        RadioButton computer = new RadioButton("Computer");
        computer.setToggleGroup(toggleGroup);
        computer.setFocusTraversable(true);
        this.gameMode[FlapConstants.COMPUTER_GAME_MODE] = computer;

        this.gameModeMenu.getChildren().add(manual);
        this.gameModeMenu.getChildren().add(computer);

        this.generationLabel = new Label("Attempt: " + this.generation);
        this.gameModeMenu.getChildren().add(this.generationLabel);

        this.controlsPane.getChildren().add(this.gameModeMenu);
    }

    private void setUpButtons() {
        Button play = new Button("Play");
        play.setOnAction((ActionEvent e) -> this.play());
        play.setFocusTraversable(false);

        this.reset = new Button("Reset");
        this.reset.setOnAction((ActionEvent e) -> this.resetHandler());
        this.reset.setFocusTraversable(false);

        Button quit = new Button("Quit");
        quit.setOnAction((ActionEvent e) -> Platform.exit());
        quit.setFocusTraversable(false);

        this.gameModeMenu.getChildren().addAll(play, this.reset, quit);

        this.slider = new Slider();
        this.slider.setMin(1);
        this.slider.setMax(10);
        this.slider.setBlockIncrement(1);
        this.slider.showTickLabelsProperty();
        this.slider.snapToTicksProperty();

        HBox sliderMenu = new HBox();
        sliderMenu.setAlignment(Pos.TOP_CENTER);
        this.controlsPane.getChildren().add(sliderMenu);

        sliderMenu.getChildren().add(this.slider);
    }

    private void createHighScoreLabel() {
        this.flappyGame.setHighScore(this.highScore);

        this.highScoreLabel = new Label("High score: " + this.highScoreLine);
        this.highScoreLabel.setStyle("-fx-font: italic bold 30px arial, serif;-fx-text-fill: #ffe571;");
        this.highScoreLabel.setLayoutX((FlapConstants.APP_WIDTH / 2) - 85);
        this.highScoreLabel.setLayoutY(50);
        this.flappyGame.setHighScoreLabel(this.highScoreLabel);

        this.flappyPane.getChildren().add(this.highScoreLabel);
    }

    private void createScoreLabel() {
        this.scoreLabel = new Label("0");
        this.scoreLabel.setStyle("-fx-font: italic bold 75px arial, serif;-fx-text-fill: #ffe571;");
        this.scoreLabel.setLayoutX((FlapConstants.APP_WIDTH / 2) - 24);
        this.scoreLabel.setLayoutY(75);
        this.flappyGame.setScoreLabel(this.scoreLabel);

        this.flappyPane.getChildren().addAll(this.scoreLabel);
    }

    private void play() {
        if (!this.settingsApplied) {
            if (this.gameMode[FlapConstants.MANUAL_GAME_MODE].isSelected()) {
                this.flappyGame.setPlayers(FlapConstants.MANUAL_GAME_MODE);
            } else if (this.gameMode[FlapConstants.COMPUTER_GAME_MODE].isSelected()) {
                this.flappyGame.setPlayers(FlapConstants.COMPUTER_GAME_MODE);
            }
            this.settingsApplied = true;
        }
    }

    public void resetHandler() {
        this.settingsApplied = false;
        this.flappyPane.getChildren().clear();
        this.highScore = this.flappyGame.getHighScore();
        this.flappyGame = new FlappyGame(this.flappyPane);
        this.flappyGame.setControls(this);
        this.generation++;
        this.generationLabel.setText("Attempt: " + this.generation);
        this.createHighScoreLabel();
        this.createScoreLabel();
        this.play();
    }

    public void removeResetButton() {
        this.gameModeMenu.getChildren().remove(this.reset);
    }

    public double getSliderValue() {
        return this.slider.getValue();
    }

    public void writeFile() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter("/Users/javier/IdeaProjects/FlappyBird/highscore"));
            bufferedWriter.write(this.highScore);
            System.out.println("file written");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile() {
        this.file = new File("/Users/javier/IdeaProjects/FlappyBird/highscore");
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader("/Users/javier/IdeaProjects/FlappyBird/highscore"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                this.highScoreLine = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
