package Flappy;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class ManualBird implements Flappable {
    private Pane flappyPane;
    private Bird bird;

    public ManualBird(Pane flappyPane) {
        this.flappyPane = flappyPane;
        this.flappyPane.requestFocus();
        this.bird = new Bird(flappyPane);
    }

    @Override
    public void flap() {
        this.flappyPane.setOnKeyPressed((KeyEvent e) -> this.handleKeyPressed(e));
    }

    private void handleKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.SPACE) {
            this.setCurrentVelocity(FlapConstants.FLAP_VELOCITY);
        }
    }

    @Override
    public void gravity() {
        this.bird.gravity();
    }

    @Override
    public double getBirdY() {
        return this.bird.getBirdY();
    }

    @Override
    public void setBirdY(double y) {
        this.bird.setYLoc(y);
    }

    @Override
    public void setCurrentVelocity(double v) {
        this.bird.setCurrentVelocity(v);
    }

    @Override
    public boolean checkIntersection(Pipe pipe) {
        return this.bird.checkIntersection(pipe);
    }
}
