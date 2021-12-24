package Flappy;

/**
 * Interface which is implemented by Manual and Computer birds and defines method such as Flap().
 */
public interface Flappable {

    void flap(double xDistanceToPipe, double yDistanceToPipe);
}