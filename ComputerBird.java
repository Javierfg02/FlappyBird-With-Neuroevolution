package Flappy;

import javafx.scene.layout.Pane;

public class ComputerBird extends Bird {
    private Pane flappyPane;

    public ComputerBird(Pane flappyPane) {
        super(flappyPane);
        this.flappyPane = flappyPane;
    }

    @Override
    public void flap() { }
}
