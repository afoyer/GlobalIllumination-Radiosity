
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
	final double dotarea = 49;
	Renderer renderer;
	CameraFrame cameraFrame;
	Camera camera;
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
		face.generateDots(dotarea,0);
		Face face2 = new Face(new Vector[]{new Vector(-300,300,0),new Vector(-300,-300,0),new Vector(-300,-300,-300),new Vector(-300,300,-300)});
		face2.setColor(Color.red);
		face2.generateDots(dotarea,0);
		Face face3 = new Face(new Vector[]{new Vector(300,300,-300),new Vector(300,-300,-300),new Vector(300,-300,0),new Vector(300,300,0)});
		face3.setColor(Color.green);
		face3.generateDots(dotarea,0);
		Face face4 = new Face(new Vector[]{new Vector(-100,100,-250),new Vector(-100,-100,-250),new Vector(100,-100,-250),new Vector(100,100,-250)});
		face4.setColor(Color.white);
		face4.generateDots(dotarea,0);
		Face face5 = new Face(new Vector[]{new Vector(100,100,-250),new Vector(100,-100,-250),new Vector(-100,-100,-250),new Vector(-100,100,-250)});
		face5.setColor(Color.white);
		face5.generateDots(dotarea,0);
		Face areaLight = new Face(new Vector[]{new Vector(50,-300,50),new Vector(50,-300,0),new Vector(-50,-300,0),new Vector(-50,-300,50)});
		areaLight.setColor(Color.white);
		areaLight.generateDots(dotarea,1000);
		int frameScale=100; //change this for camera frame size (smaller=more pixallated but less space in between pixels)
		int frameWidth=10*frameScale;
		int frameHeight=10*frameScale;
		int frameDepth=3*frameScale;
		cameraFrame = new CameraFrame(new Vector[]{new Vector(frameWidth/2,-frameHeight/2,-frameDepth),new Vector(frameWidth/2,frameHeight/2,-frameDepth),new Vector(-frameWidth/2,frameHeight/2,-frameDepth),new Vector(-frameWidth/2,-frameHeight/2,-frameDepth)});
		Face[] faces = new Face[]{face,face2,face3,face4,areaLight};
		camera = new Camera(Vector.origin,cameraFrame,faces);
		renderer = new Renderer();
		renderer.loadFaces(faces);
		renderer.bake(2);

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
