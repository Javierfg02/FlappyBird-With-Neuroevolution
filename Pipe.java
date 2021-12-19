package Flappy;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Pipe {
    private Pane flappyPane;
    private Rectangle pipe;

    public Pipe(Pane flappyPane) {
        this.flappyPane = flappyPane;
        this.pipe = new Rectangle();
    }
}
