package boxes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.Dimension;
import boxes.DrawGraphics;


/**
Animator controls what is being animated. 
*/
//implements Runnable means you can override the void run() method, which
//will be executed when the thread is executed.
public class Animator extends JPanel implements Runnable {
  
  /**
   * Constructs one animator to control the animation.
   * ONLY CONSTRUCT ONE ANIMATOR!
   * @param drawer what will be drawn by the animator.
   */
  public Animator(DrawGraphics drawer, int width, int height, int fps) {
    _drawer = drawer;
    _WIDTH = width;
    _HEIGHT = height;
    _fps = fps;
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);//calls paintComponent parent constructor.
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    //setRenderingHint tells Graphics2D how to render the specified drawings.
    _drawer.draw(g2);//we are using Graphics2D to draw what ever is specified by our drawer.
  }
  
  /**When would you like the Animator to start the animation*/
  public synchronized void start() {
    _animate = true;
  }

  /**When would you like the Animator to stop the animation*/
  public synchronized void stop() {
    _animate = false;
  }


  /**Method called when thread is active*/
  public void run() {
    while (true) {
      if (_animate) {
        repaint();
      }
      try {
        Thread.sleep((int)(_fps*2.5));//Approximate conversion between milliseconds and frames per
                                      //second. From MIT open course ware Java course.
      }
      catch (InterruptedException e) {
        System.out.println("Thread interrupted! Shutting down!");
        throw new RuntimeException(e);
      }
    }
  }

  public static int getWIDTH() {
    return _WIDTH;
  }

  public static int getHEIGHT() {
    return _HEIGHT;
  }

  /*public static void main(String args[]) {
        final Animator content = new Animator(new DrawGraphics());

        JFrame frame = new JFrame("Graphics!");
       
        Color bgColor = Color.white;
        frame.setBackground(bgColor);
        content.setBackground(bgColor);
        content.setSize(_WIDTH, _HEIGHT);
        content.setMinimumSize(new Dimension(_WIDTH, _HEIGHT));
        content.setPreferredSize(new Dimension(_WIDTH, _HEIGHT));
        frame.setSize(_WIDTH, _HEIGHT);
        frame.setContentPane(content);
        frame.setResizable(true);
        frame.pack();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
            public void windowDeiconified(WindowEvent e) { content.start(); }
            public void windowIconified(WindowEvent e) { content.stop(); }
        });

        new Thread(content).start();

        frame.setVisible(true);
  }
  */

  private DrawGraphics _drawer;
  private boolean _animate = true;
  private int _fps;//frames per second
  private static int _WIDTH;
  private  static int _HEIGHT;

}

