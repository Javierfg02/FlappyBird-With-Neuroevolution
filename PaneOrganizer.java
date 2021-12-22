package Flappy;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

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
        Image backgroundImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(
                "background.png")));
        ImageView background1 = new ImageView(backgroundImage);
        ImageView background2 = new ImageView(backgroundImage);

        this.root.getChildren().add(background1);
        this.root.getChildren().add(background2);

        TranslateTransition trans1 = new TranslateTransition(Duration.seconds(10), background1);
        trans1.setFromX(0);
        trans1.setToX(-1 * FlapConstants.BACKGROUND_WIDTH);
        trans1.setInterpolator(Interpolator.LINEAR);

        TranslateTransition trans2 = new TranslateTransition(Duration.seconds(10), background2);
        trans2.setFromX(FlapConstants.BACKGROUND_WIDTH);
        trans2.setToX(0);
        trans2.setInterpolator(Interpolator.LINEAR);

        ParallelTransition backgroundWrapper = new ParallelTransition(trans1, trans2);
        backgroundWrapper.setCycleCount(Animation.INDEFINITE);
        backgroundWrapper.play();
    }
}
