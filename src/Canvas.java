
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
	final double dotarea = 4;
	Renderer renderer;
	CameraFrame cameraFrame;
	Camera camera;
	Light light;
	Light light2;
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
		face.generateDots(dotarea);
		Face face2 = new Face(new Vector[]{new Vector(-300,300,0),new Vector(-300,-300,0),new Vector(-300,-300,-300),new Vector(-300,300,-300)});
		face2.setColor(Color.red);
		face2.generateDots(dotarea);
		Face face3 = new Face(new Vector[]{new Vector(300,300,-300),new Vector(300,-300,-300),new Vector(300,-300,0),new Vector(300,300,0)});
		face3.setColor(Color.green);
		face3.generateDots(dotarea);
		Face face4 = new Face(new Vector[]{new Vector(-100,100,-250),new Vector(-100,-100,-250),new Vector(100,-100,-250),new Vector(100,100,-250)});
		face4.setColor(Color.white);
		face4.generateDots(dotarea);
		Face face5 = new Face(new Vector[]{new Vector(100,100,-251),new Vector(100,-100,-251),new Vector(-100,-100,-251),new Vector(-100,100,-251)});
		face5.setColor(Color.white);
		face5.generateDots(dotarea);
		int frameScale=50; //change this for camera frame size
		int frameWidth=10*frameScale;
		int frameHeight=10*frameScale;
		int frameDepth=3*frameScale;
		cameraFrame = new CameraFrame(new Vector[]{new Vector(frameWidth/2,-frameHeight/2,-frameDepth),new Vector(frameWidth/2,frameHeight/2,-frameDepth),new Vector(-frameWidth/2,frameHeight/2,-frameDepth),new Vector(-frameWidth/2,-frameHeight/2,-frameDepth)});
		Face[] faces = new Face[]{face,face2, face3, face4};
		camera = new Camera(Vector.origin,cameraFrame,faces);
		light = new Light(new Vector(190,0,50), 50000, Color.white);
		light2 = new Light(new Vector(-200,0,50), 50000, Color.white);
		renderer = new Renderer();
		renderer.addLight(light);

		renderer.addLight(light2);
		renderer.addLight(new Light(new Vector(0,50,50), 50000, Color.white));
		renderer.loadFaces(faces);
		renderer.bake(0);

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
