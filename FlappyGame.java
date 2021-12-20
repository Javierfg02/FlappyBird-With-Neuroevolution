package Flappy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class FlappyGame {
    private Pane flappyPane;
    private Flappable bird;
    private Timeline timeline;
    private ArrayList<Pipe> pipeStorage;

    public FlappyGame(Pane flappyPane) {
        this.flappyPane = flappyPane;
        this.pipeStorage = new ArrayList<>();
        this.setUpTimeline();
        new Pipe(flappyPane, 300, 300, 50);
    }

    public void setPlayers(int gameMode) {
        if (gameMode == FlapConstants.MANUAL_GAME_MODE) {
            this.bird = new ManualBird(this.flappyPane);
        } else {
            this.bird = new ComputerBird(this.flappyPane);
        }
    }

    private void setUpTimeline() {
        KeyFrame kf = new KeyFrame(Duration.seconds(FlapConstants.DURATION),
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
        if (this.bird.getBirdY() > (this.flappyPane.getHeight() - 20)) {
            this.bird.setCurrentVelocity(0);
            this.bird.setBirdY(this.flappyPane.getHeight() - 20);
        }
    }

//    private void createPipes() {
//        Pipe nearestPipe = this.nearestPipe();
//        while (this.pipeStorage.size() <= 1) {
//
//        }
//    }

//    private Pipe nearestPipe() {
//
//        }
//    }
}
