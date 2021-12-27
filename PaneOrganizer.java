package Flappy;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class PaneOrganizer {
    private BorderPane root;

    public PaneOrganizer() {
        this.root = new BorderPane();
        Pane flappyPane = new Pane();
        flappyPane.setFocusTraversable(true);
        FlappyGame flappyGame = new FlappyGame(flappyPane);
        this.root.setCenter(flappyPane);
        Controls controls = new Controls(flappyGame, flappyPane);
        this.root.setBottom(controls.getPane());
        flappyGame.setControls(controls);
    }

    public Pane getRoot() {
        return this.root;
    }
}
