package boxes;

import java.awt.Color;
import java.awt.Graphics;
import boxes.BouncingBox;

public class DrawGraphics {
    BouncingBox box;
    
    /** Initializes this class for drawing. */
    public DrawGraphics() {
        box = new BouncingBox(50, 50, Color.RED);
        box.setMovementVector(-8,-8);
    }

    /** Draw the contents of the window on surface. Called 20 times per second. */
    public void draw(Graphics surface) {
        box.draw(surface);
    }
} 
