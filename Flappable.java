package Flappy;

import java.util.ArrayList;

/**
 * Interface which is implemented by Manual and Computer birds and defines method such as Flap().
 */
public interface Flappable {

    void flap();

    void gravity();

    double getBirdY();

    void setBirdY(double y);

    void setCurrentVelocity(double v);

    boolean checkIntersection(Pipe pipe);
}