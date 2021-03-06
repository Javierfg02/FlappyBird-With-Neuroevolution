package Flappy;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class ManualBird extends Bird {
    private Pane flappyPane;

    public ManualBird(Pane flappyPane) {
        super(flappyPane);
        this.flappyPane = flappyPane;
        this.flappyPane.requestFocus();
    }

    @Override
    public void flap(double xDistanceToPipe, double yDistanceToPipe) {
        this.flappyPane.setOnKeyPressed((KeyEvent e) -> this.handleKeyPressed(e));
    }

    private void handleKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.SPACE) {
            this.setCurrentVelocity(FlapConstants.FLAP_VELOCITY);
//            FlappyGame.playAudio("/Users/javier/IdeaProjects/FlappyBird/swoosh.mp3"); // TODO makes it very laggy
        }
    }
}
