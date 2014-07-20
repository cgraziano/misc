package raytracing;

import edu.mines.jtk.dsp.*;
import static edu.mines.jtk.util.ArrayMath.*;

/***
 * Earth uses a defined velocity model and time to move rays.
 * @author Chris Graziano June 8th, 2014.
 */
public class Earth {

  /**
   * Construct Earth with a horizontal velocity model (3 layers)
   * @param zs The velocity interface locations.
   * @param vs The velocities.
   * @param dt The sampling interval.
   * @param deltat The time step.
   */
   public Earth(float[] zs, float[] vs, float dt) {
     _nz = zs.length;
     _zs = zs;
     _vs = vs;
     _dt = dt;

   }

   public void startSource(float t, Ray[] rays) {
     int nray = rays.length;

     int nt = (int) (t/_dt) + 1;
     _raypathsX = new float[nray][nt];
     _raypathsZ = new float[nray][nt];
     int it = 0;
     while (it<nt) {
       for (int i=0; i<nray; ++i) {
         _raypathsX[i][it] = rays[i].getRayX();
         _raypathsZ[i][it] = rays[i].getRayZ();
         timeStep(rays[i]);
       }
       ++it;
     }
   }

   public float[][] getRaysX() {
     return _raypathsX;
   }

   public float[][] getRaysZ() {
     return _raypathsZ;
   }

   private void timeStep(Ray ray) {
     float x1 = ray.getRayX(); 
     float z1 = ray.getRayZ();
     float theta1 = ray.getRayTheta();//angle of ray from horizontal.

     //What velocity zone is the ray's head in?
     float v1 = getCurrentVelocity(z1);

     //Calculate new head of ray position given last ray status.
     float x2 = x1 + calcDelX(v1, theta1);
     float z2 = z1 + calcDelY(v1, theta1);
     
     //Check if velocity has changed with new positions
     float v2 = getCurrentVelocity(z2);
      
     //Calculate new ray angle.
     float thetai = (float)PI/2.0f - theta1;//angle of incidence 
     float thetar = getRefracAngle(v1,v2,thetai);
     float theta2 = (float)PI/2.0f - thetar;//new angle of ray from horizontal.

     //Update Ray information
     ray.updateRay(x2,z2,theta2);
   }

   /**
    * Calculates the angle of refraction using Snell's law.
    *@param v1 The initial layer's velocity.
    *@param v2 The final layer's velocity.
    *@param thetai The angle of incidence.
    */
   private float getRefracAngle(float v1, float v2, float thetai) {
      //Check if incident ange is above critical ange
      float thetac = asin(v1/v2);
      if (thetai > thetac) 
        return (float)PI/2.0f;
      else if ((thetai < 0.0f) && (-thetai > thetac))
        return (float)-PI/2.0f;
      else
        return asin(v2*sin(thetai)/v1);
   }

   /**
    * Returns the horizontal distance the object has moved. 
    */
   private float calcDelX(float v, float theta) {
     return v*cos(theta)*_dt;
   }

   private float calcDelY(float v, float theta) {
     return v*sin(theta)*_dt;
   }

   private float getCurrentVelocity(float z) {
      int curiz = 0;
      for (int iz=0; iz<_nz-1; ++iz) {
        if (_zs[iz] < z && z <= _zs[iz+1])
          curiz = iz;
      }
      return _vs[curiz];
   }



   private float[] _zs,_vs;
   private float _dt;
   private float[][] _raypathsX;
   private float[][] _raypathsZ;
   private int _nz;
}
