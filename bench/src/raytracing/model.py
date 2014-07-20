from imports import *

from raytracing import Ray,Earth

def main(args):
  zs = [0,100,200,400,500,20000]#meters
  vs = [1000,1500,2000,2500,2755,3000]#m/s
  dt = .0001#seconds
  t = 1#seconds

  nx,dx,xi = 20, 100, 0# meters
  ntheta,dtheta,thetai = 72, 2.5, 0# degrees
  rays = buildRays(nx,dx,xi,ntheta,dtheta,thetai)
  earth = Earth(zs,vs,dt)
  earth.startSource(t,rays)

  #Plotting
  drawRays(earth,zs,rays)
  

#Draws rays through Earth's velocity model. 
def drawRays(earth,zs,rays):
  raysX = earth.getRaysX()
  raysZ = earth.getRaysZ()
  minX = min(raysX)
  maxX = max(raysX)
  maxZ = max(raysZ)
  nz = len(zs) 
  nr = len(rays)

  sp = SimplePlot(SimplePlot.Origin.UPPER_LEFT)
  #draw horizontal velocity model
  for iz in range(0,nz-1):
    z = zs[iz]
    pv = sp.addPoints([z,z],[minX,maxX])

  #draw rays
  for ir in range(0,nr):
    pv = sp.addPoints(raysZ[ir],raysX[ir])
  pv.setOrientation(PointsView.Orientation.X1DOWN_X2RIGHT)

#nx: The number of ray positions
#xi: Starting ray position
#dx: The spacing between ray's locations 
#ntheta: The number of different thetas at a ray position
#thetai: The starting angle from the horizontal at a ray position
#dtheta: The theta sampling interal at a ray position. At each ray position,
#        theta will increase. 
def buildRays(nx, dx, xi, ntheta, dtheta, thetai):
  z = 0#default z position in meters
  rays = []
  for ix in range(nx):
    for itheta in range(ntheta):
      x = xi + ix*dx 
      theta = thetai + itheta*dtheta
      rays.append(Ray(x,z,theta))
  return rays

  

  



  

#############################################################################
# Do everything on Swing thread.

class RunMain(Runnable):
  def run(self):
    main(sys.argv)
SwingUtilities.invokeLater(RunMain())


