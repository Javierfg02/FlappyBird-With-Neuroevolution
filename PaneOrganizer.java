package Flappy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class PaneOrganizer {
    private BorderPane root;

    public PaneOrganizer() {
        this.root = new BorderPane();
        this.backgroundImage();
        Pane flappyPane = new Pane();
        flappyPane.setFocusTraversable(true);
        FlappyGame flappyGame = new FlappyGame(flappyPane);
        this.root.setCenter(flappyPane);
        Controls controls = new Controls(flappyGame, flappyPane);
        this.root.setBottom(controls.getPane());
    }

    public Pane getRoot() {
        return this.root;
    }

    private void backgroundImage() {
        ImageView backgroundImage = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(
                "background.png"))));
        this.root.getChildren().add(backgroundImage);
    }
}
