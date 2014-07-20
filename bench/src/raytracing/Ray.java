package raytracing;

import static edu.mines.jtk.util.ArrayMath.*;

/***
 * Designed to imitate a seismic ray (representing a wavefield) in the Earth.
 * Ray can only propogate downward.
 * @author Chris Graziano June 8th, 2014.
 */
public class Ray {

  /**
   * Ray only has the ability to propagate downwards.
   * @param x0 Starting x position.
   * @param z0 Starting z position.
   * @param theta0 Starting angle of ray propagation in degrees from horizontal.
   */
  public Ray(float x0, float z0, float theta0) {
    _x = x0;
    _z = z0;
    _theta = toRadians(theta0);
  }

  public void updateRay(float x, float z, float theta) {
    _x = x;
    _z = z;
    _theta = theta;
  }

  public float getRayX() {
    return _x;
  }

  public float getRayZ() {
    return _z;
  }
  public float getRayTheta() {
    return _theta;
  }

  private float _x, _z, _theta;
}

