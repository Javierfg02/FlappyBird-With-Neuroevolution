package Flappy;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Controls {
    private FlappyGame flappyGame;
    private RadioButton[] gameMode;
    private HBox controlsPane;
    private HBox gameModeMenu;
    private boolean settingsApplied;
    private Pane flappyPane;
    private Label scoreLabel;
    private Label highScoreLabel;

    public Controls(FlappyGame flappyGame, Pane flappyPane) {
        this.flappyGame = flappyGame;
        this.flappyPane = flappyPane;
        this.setUpPane();
        this.setupMenu();
        this.setUpButtons();
        this.createHighScoreLabel();
        this.createScoreLabel();
        this.settingsApplied = false;
    }

    public Pane getPane() {
        return this.controlsPane;
    }

    private void setUpPane() {
        this.controlsPane = new HBox();
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
        this.gameModeMenu.setSpacing(20);
        this.gameModeMenu.setAlignment(Pos.CENTER_RIGHT);
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

        this.controlsPane.getChildren().add(this.gameModeMenu);
    }

    private void setUpButtons() {
        Button applySettings = new Button("Play");
        applySettings.setOnAction((ActionEvent e) -> this.play(e));
        applySettings.setFocusTraversable(false);

        Button reset = new Button("Reset");
        reset.setOnAction((ActionEvent e) -> this.resetHandler(e));
        reset.setFocusTraversable(false);

        Button quit = new Button("Quit");
        quit.setOnAction((ActionEvent e) -> Platform.exit());
        quit.setFocusTraversable(false);

        this.gameModeMenu.getChildren().addAll(applySettings, reset, quit);
    }

    private void createHighScoreLabel() {
        if (this.highScoreLabel != null) {
            this.highScoreLabel = new Label(this.highScoreLabel.getText());
            System.out.println(this.highScoreLabel.getText());
        } else {
            this.highScoreLabel = new Label("High score: 0");
        }
        this.highScoreLabel.setStyle("-fx-font: italic bold 30px arial, serif;-fx-text-fill: #ffd007;");
        this.highScoreLabel.setLayoutX((FlapConstants.APP_WIDTH / 2) - 80);
        this.highScoreLabel.setLayoutY(50);
        this.flappyGame.setHighScoreLabel(this.highScoreLabel);

        this.flappyPane.getChildren().add(this.highScoreLabel);
    }

    private void createScoreLabel() {
        this.scoreLabel = new Label("0");
        this.scoreLabel.setStyle("-fx-font: italic bold 75px arial, serif;-fx-text-fill: #ffd007;");
        this.scoreLabel.setLayoutX((FlapConstants.APP_WIDTH / 2) - 22);
        this.scoreLabel.setLayoutY(75);
        this.flappyGame.setScoreLabel(this.scoreLabel);

        this.flappyPane.getChildren().addAll(this.scoreLabel);
    }

    private void play(ActionEvent e) {
        if (!this.settingsApplied) {
            if (this.gameMode[FlapConstants.MANUAL_GAME_MODE].isSelected()) {
                this.flappyGame.setPlayers(FlapConstants.MANUAL_GAME_MODE);
            } else if (this.gameMode[FlapConstants.COMPUTER_GAME_MODE].isSelected()) {
                this.flappyGame.setPlayers(FlapConstants.COMPUTER_GAME_MODE);
            }
            this.settingsApplied = true;
        }
    }

    private void resetHandler(ActionEvent e) {
        this.settingsApplied = false;
        this.flappyPane.getChildren().clear();
        this.flappyGame = new FlappyGame(this.flappyPane);
        this.createHighScoreLabel();
        this.createScoreLabel();
    }
}
