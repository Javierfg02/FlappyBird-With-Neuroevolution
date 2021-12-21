package Flappy;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

    public Controls(FlappyGame flappyGame) {
        this.flappyGame = flappyGame;
        this.setUpPane();
        this.setupMenu();
        this.setUpButtons();
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
        this.controlsPane.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(3))));
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

        this.controlsPane.getChildren().add(gameModeMenu);
    }

    private void setUpButtons() {
        Button applySettings = new Button("Apply Settings");
        applySettings.setOnAction((ActionEvent e) -> this.applySettings(e));
        applySettings.setFocusTraversable(false);

        Button reset = new Button("Reset");
        reset.setOnAction((ActionEvent e) -> this.resetHandler(e));
        reset.setFocusTraversable(false);

        Button quit = new Button("Quit");
        quit.setOnAction((ActionEvent e) -> Platform.exit());
        quit.setFocusTraversable(false);

        this.gameModeMenu.getChildren().addAll(applySettings, reset, quit);
    }

    private void applySettings(ActionEvent e) {
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
    }
}
