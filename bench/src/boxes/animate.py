import os,sys
from boxes import Animator
from boxes import DrawGraphics
from java.awt.event import WindowAdapter
from java.awt.event import WindowEvent
from java.awt.event import WindowListener
from java.awt import Color
from java.awt import Dimension
from javax.swing import JFrame
from javax.swing import SwingUtilities 
from javax.swing import WindowConstants 
from java.lang import Runnable 
from java.lang import Thread 


def main(args):
  _WIDTH = 300
  _HEIGHT = 300
  fps = 20#frames per second
  bgColor = Color.white
  frame = JFrame("Graphics!")
  frame.setBackground(bgColor);
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

  drawgraphics = DrawGraphics()
  content = Animator(drawgraphics, _WIDTH, _HEIGHT, fps)
  content.setBackground(bgColor)
  content.setSize(_WIDTH, _HEIGHT)
  content.setMinimumSize(Dimension(_WIDTH, _HEIGHT))
  content.setPreferredSize(Dimension(_WIDTH, _HEIGHT))

  frame.setSize(_WIDTH, _HEIGHT)
  frame.setContentPane(content)
  frame.setResizable(True)
  frame.pack()

  Thread(content).start()
  frame.setVisible(True)




  

def windowClosing(self, event):
  sys.exit(0)
  #def windowDeiconified(self, event):
    #content.start()
  #def windowIconified(self, event):
    #content.stop()


#############################################################################
# Do everything on Swing thread.

class RunMain(Runnable):
  def run(self):
    main(sys.argv)
SwingUtilities.invokeLater(RunMain())

