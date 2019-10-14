
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
	CameraFrame cameraFrame;
	Camera camera;
	Light light;
	Face face;
	int width;
	int height;
	public Canvas ()
	{
		width=1000;
		height=1000;
		//The following is another way to guarantee correct size.
		setPreferredSize(new Dimension(width,height));
		setBackground(Color.lightGray);

		face = new Face(new Vector[]{new Vector(-300,300,-300),new Vector(-300,-300,-300),new Vector(300,-300,-300),new Vector(300,300,-300)});
		face.setColor(Color.white);
		face.generateDots(16);
		Face face2 = new Face(new Vector[]{new Vector(-100,100,-250),new Vector(-100,-100,-250),new Vector(100,-100,-250),new Vector(100,100,-250)});
		face2.setColor(Color.white);
		face2.generateDots(16);
		Face face3 = new Face(new Vector[]{new Vector(200,300,-300),new Vector(200,-300,-300),new Vector(300,-300,0),new Vector(300,300,0)});
		face3.setColor(Color.red);
		face3.generateDots(16);
		cameraFrame = new CameraFrame(new Vector[]{new Vector(100,-100,-50),new Vector(100,100,-50),new Vector(-100,100,-50),new Vector(-100,-100,-50)});
		Face[] faces = new Face[]{face,face3};
		camera = new Camera(Vector.origin,cameraFrame,faces);
		light = new Light(Vector.origin.setX(-100).setZ(-200), 400000, Color.white);
		renderer = new Renderer();
		renderer.addLight(light);
		renderer.loadFaces(faces);
		renderer.bake(1);

	}

	/**
	* Draws and fills components made by SimpleCanvas
	* @param g
	*/
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);  //without this no background color set.
		Graphics2D g2d = (Graphics2D)g; //cast so we can use JAVA2D.
		BufferedImage bi = camera.draw();
		g2d.drawImage(bi, 0, 0, width, height, 0, 0, cameraFrame.getWidth(),cameraFrame.getHeight(), null);

	}

}// SimpleCanvas
