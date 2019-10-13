
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
import java.awt.image.*;


public class Canvas extends JPanel
{
	Renderer renderer;
	Camera camera;
	Light light;
	Face face;
	public Canvas ()
	{
		//The following is another way to guarantee correct size.
		setPreferredSize(new Dimension(500,500));
		setBackground(Color.lightGray);

		face = new Face(new Vector[]{new Vector(300,-300,-300),new Vector(300,300,-300),new Vector(-300,300,-300),new Vector(-300,-300,-300)});
		CameraFrame cameraFrame = new CameraFrame(new Vector[]{new Vector(100,-100,-50),new Vector(100,100,-50),new Vector(-100,100,-50),new Vector(-100,-100,-50)});
		camera = new Camera(Vector.origin,cameraFrame);
		light = new Light(Vector.origin, 100, Color.white);
		renderer = new Renderer();
		renderer.addLight(light);
		renderer.loadFaces(new Face[]{face});
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
		renderer.bake(0);
		BufferedImage bi = camera.draw(renderer.bakedDots);
		g2d.drawImage(bi, 0,0,null);

	}

}// SimpleCanvas
