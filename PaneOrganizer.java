package Flappy;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class PaneOrganizer {
    private final BorderPane root;

    public PaneOrganizer() {
        this.root = new BorderPane();
        Pane flappyPane = new Pane();
        flappyPane.setFocusTraversable(true);
        FlappyGame flappyGame = new FlappyGame(flappyPane);
        this.root.setCenter(flappyPane);
        Controls controls = new Controls(flappyGame);
        this.root.setBottom(controls.getPane());
    }

    public Pane getRoot() {
        return this.root;
    }
}
