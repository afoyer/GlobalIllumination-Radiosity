
/**
 * SimpleCanvas.java
 *
 * Part of the basic graphics Template.
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;


public class SimpleCanvas extends JPanel
{
	/**
	 * Variables
	 */


	/**
	 * Sets up points to be drawn.
	 */
	public SimpleCanvas ()
	 {
		//The following is another way to guarantee correct size.	 
		setPreferredSize(new Dimension(300,300));
		setBackground(Color.lightGray);

    }

	/**
	 * Draws and fills components made by SimpleCanvas
	 * @param g
	 */
	public void paintComponent(Graphics g)
    {
		super.paintComponent(g);  //without this no background color set.
	
		Graphics2D g2d = (Graphics2D)g; //cast so we can use JAVA2D.
		g2d.translate(getWidth()/2,getHeight()/2);

		
	 }

}// SimpleCanvas
