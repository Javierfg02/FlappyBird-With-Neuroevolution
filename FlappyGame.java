package Flappy;

import doodlejump.Constants;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class FlappyGame {
    private Pane flappyPane;
    private Flappable bird;
    private Timeline timeline;

    public FlappyGame(Pane flappyPane) {
        this.flappyPane = flappyPane;
        this.setUpTimeline();
    }

    public void setPlayers(int gameMode) {
        if (gameMode == FlapConstants.MANUAL_GAME_MODE) {
            this.bird = new ManualBird(this.flappyPane);
        } else {
            this.bird = new ComputerBird(this.flappyPane);
        }
    }

    private void setUpTimeline() {
        KeyFrame kf = new KeyFrame(Duration.seconds(Constants.DURATION),
                (ActionEvent e) -> this.updateTimeline());
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    private void updateTimeline() {
        if (this.bird != null) { // before applying settings the bird is null.
            this.bird.flap();
            this.bird.gravity();
            this.keepBirdInScreen();
        }

    }

    private void keepBirdInScreen() {
        if (this.bird.getBirdY() > (FlapConstants.APP_HEIGHT - 255)) {
            this.bird.setCurrentVelocity(0);
            this.bird.setBirdY(FlapConstants.APP_HEIGHT - 255);
        }
    }
}
