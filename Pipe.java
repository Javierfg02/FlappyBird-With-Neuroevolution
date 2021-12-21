package Flappy;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import java.util.Objects;

public class Pipe {
    private Rectangle bottomPipe;
    private Rectangle topPipe;
    private Pane flappyPane;

    public Pipe(Pane flappyPane, double x, double bottomPipeHeight) {
        this.flappyPane = flappyPane;
        // create the bottomPipe with the variables passed in
        this.bottomPipe = new Rectangle(x, bottomPipeHeight, FlapConstants.PIPE_WIDTH, FlapConstants.PIPE_LENGTH);

        // create the top pipe based on the variables passed in to the bottom pipe
        double topPipeHeight = bottomPipeHeight - FlapConstants.PIPE_GAP_HEIGHT;

        this.topPipe = new Rectangle(x, topPipeHeight, FlapConstants.PIPE_WIDTH, FlapConstants.PIPE_LENGTH);

        // set the image for both pipes
//        this.weedPipe(); // TODO fix :(

        // rotate the top pipe so that it is upside down
        this.rotateTopPipe(this.topPipe);
        this.flappyPane.getChildren().addAll(this.bottomPipe, this.topPipe);
    }

    /**
     * Method that rotates the top pipe so that it is aligned correctly
     * @param topPipe The top pipe that we want to rotate
     */
    private void rotateTopPipe(Rectangle topPipe) {
        double rotationCenterX = (topPipe.getX() + (FlapConstants.PIPE_WIDTH / 2));
        double rotationCenterY = topPipe.getY();
        Rotate rotate = new Rotate();
        rotate.setAngle(180);
        rotate.setPivotX(rotationCenterX);
        rotate.setPivotY(rotationCenterY);
        topPipe.getTransforms().addAll(rotate);
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

    public void scrollPipes() {
        // must have opposite signs because the top pipe is rotated
        this.topPipe.setX(this.topPipe.getX() + FlapConstants.SCROLLING_CONSTANT);
        this.bottomPipe.setX(this.bottomPipe.getX() - FlapConstants.SCROLLING_CONSTANT);
    }

    public void removeFromPane() {
        this.flappyPane.getChildren().removeAll(this.topPipe, this.bottomPipe);
    }

    /**
     * Method that sets the y locations of the pipes so that there is a constant gap between them.
     */
    public Bounds getTopPipeBounds() {
        return this.topPipe.getBoundsInParent(); // this accounts for the rotation of the rectangle
    }

    public Bounds getBottomPipeBounds() {
        return this.bottomPipe.getBoundsInLocal(); // this does not account for rotation of the rectangle
    }

    public double getPipeX() {
        return this.bottomPipe.getX();
    }
}
