package Flappy;

import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import java.util.Objects;

public class Pipe {
    private Rectangle bottomPipe;
    private Rectangle topPipe;
    private Pane flappyPane;
    private double gapMidpoint;

    public Pipe(Pane flappyPane, double x, double bottomPipeHeight) {
        this.flappyPane = flappyPane;
        // create the bottomPipe with the variables passed in
        this.bottomPipe = new Rectangle(x, bottomPipeHeight, FlapConstants.PIPE_WIDTH, FlapConstants.PIPE_LENGTH);

        // create the top pipe based on the variables passed in to the bottom pipe
        double topPipeHeight = bottomPipeHeight - FlapConstants.PIPE_GAP_HEIGHT;

        this.topPipe = new Rectangle(x, topPipeHeight, FlapConstants.PIPE_WIDTH, FlapConstants.PIPE_LENGTH);

        this.gapMidpoint = bottomPipeHeight - (FlapConstants.PIPE_GAP_HEIGHT / 2);

        // set the image for both pipes
        this.weedPipe(x, topPipeHeight, bottomPipeHeight); // TODO fix :(

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
    private void weedPipe(double x, double topHeight, double bottomHeight) {
        Image image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("weedPipe.png")),
                FlapConstants.PIPE_WIDTH, bottomHeight, true, true);
        ImageView imageView = new ImageView(image);
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        Rectangle2D viewportRect = new Rectangle2D(x - FlapConstants.PIPE_WIDTH, bottomHeight,
                FlapConstants.PIPE_WIDTH, FlapConstants.PIPE_LENGTH);
        imageView.setViewport(viewportRect);
        imageView.toFront();
        this.flappyPane.getChildren().add(imageView);
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

    public double getGapMidpoint() {
        return this.gapMidpoint;
    }
}
