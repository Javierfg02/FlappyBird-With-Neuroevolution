package Flappy;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.util.Objects;

public class Pipe {
    private Rectangle bottomPipe;
    private Rectangle topPipe;
    private Pane flappyPane;

    public Pipe(Pane flappyPane, double x, double bottomPipeHeight) {
        this.flappyPane = flappyPane;
        // create the bottomPipe with the variables passed in
        this.bottomPipe = new Rectangle(x, FlapConstants.FLAPPY_PANE_HEIGHT, FlapConstants.PIPE_WIDTH, bottomPipeHeight);

        // create the top pipe based on the variables passed in to the bottom pipe
        double topPipeHeight = FlapConstants.FLAPPY_PANE_HEIGHT - bottomPipeHeight - FlapConstants.PIPE_GAP_HEIGHT;
        this.topPipe = new Rectangle(x, 0, FlapConstants.PIPE_WIDTH, topPipeHeight);
        System.out.println(this.topPipe.getLayoutY() - this.bottomPipe.getLayoutY());

        // set the image for both pipes
//        this.weedPipe();

        // rotate the top pipe so that it is upside down
//        this.topPipe.setRotate(180);
        this.flappyPane.getChildren().addAll(this.bottomPipe, this.topPipe) ;
    }

    /**
     * Method that sets the weed pipe image for the pipes.
     */
    private void weedPipe() {
        Image image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("weedPipe.png")));
        ImagePattern imagePattern = new ImagePattern(image);
        this.bottomPipe.setFill(imagePattern);
        this.topPipe.setFill(imagePattern);
    }

    /**
     * Method that sets the y locations of the pipes so that there is a constant gap between them.
     */
    public void pipeY() {

    }

    public double getPipeX() {
        return this.bottomPipe.getX();
    }

}
