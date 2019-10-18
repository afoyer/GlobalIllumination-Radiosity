
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
	/*
	* Coordinate system is y+ down, x+ right, and z+ out
	*
	*/
	final double dotarea = 100;
	final double cubeDotarea = 100;

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

		Face areaLight = new Face(new Vector[]{new Vector(50,-290,50),new Vector(50,-290,0),new Vector(-50,-290,0-150),new Vector(-50,-290,50)});
		areaLight.setColor(new Color(255,230,175));
		areaLight.generateDots(25,1000);

		face3.generateDots(dotarea,0);
		//CUBE
		//front
		Face face4 = new Face(new Vector[]{new Vector(-200,300,-201),new Vector(-200,0,-201),new Vector(-150,0,-201),new Vector(-150,300,-201)});
		face4.setColor(Color.white);
		face4.generateDots(cubeDotarea,0);
		//back
		Face face9 = new Face(new Vector[]{new Vector(-200,300,-250),new Vector(-200,0,-250),new Vector(-150,0,-250),new Vector(-150,300,-250)});
		face9.flip();
		face9.setColor(Color.white);
		face9.generateDots(cubeDotarea,0);
		//left
		Face face5 = new Face(new Vector[]{new Vector(-200,300,-250),new Vector(-200,0,-250),new Vector(-200,0,-200),new Vector(-200,300,-200)});
		face5.setColor(Color.white);
		face5.generateDots(cubeDotarea,0);
		//right
		Face face6 = new Face(new Vector[]{new Vector(-150,300,-250),new Vector(-150,0,-250),new Vector(-150,0,-200),new Vector(-150,300,-200)});
		face6.flip();
		face6.setColor(Color.white);
		face6.generateDots(cubeDotarea,0);
		//top
		Face face7 = new Face(new Vector[]{new Vector(-200,1,-200),new Vector(-200,1,-250),new Vector(-150,1,-250),new Vector(-150,1,-200)});
		face7.setColor(Color.white);
		face7.generateDots(cubeDotarea,0);
		//bottom
		Face face8 = new Face(new Vector[]{new Vector(-200,300,-200),new Vector(-200,300,-250),new Vector(-150,300,-250),new Vector(-150,300,-200)});
		face8.flip();
		face8.setColor(Color.white);
		face8.generateDots(cubeDotarea,0);
		//FLOOR AND ROOF
		//floor
		Face face10 = new Face(new Vector[]{new Vector(-300,300,-300),new Vector(300,300,-300),new Vector(300,300,0),new Vector(-300,300,0)});
		face10.setColor(Color.white);
		face10.generateDots(dotarea,0);
		//roof
		Face face11 = new Face(new Vector[]{new Vector(-300,-300,0),new Vector(300,-300,0),new Vector(300,-300,-300),new Vector(-300,-300,-300)});
		face11.setColor(Color.white);
		face11.generateDots(dotarea,0);

		int frameScale=100; //change this for camera frame size (smaller=more pixallated but less space in between pixels)
		int frameWidth=10*frameScale;
		int frameHeight=10*frameScale;
		int frameDepth=3*frameScale;
		cameraFrame = new CameraFrame(new Vector[]{new Vector(frameWidth/2,-frameHeight/2,-frameDepth),new Vector(frameWidth/2,frameHeight/2,-frameDepth),new Vector(-frameWidth/2,frameHeight/2,-frameDepth),new Vector(-frameWidth/2,-frameHeight/2,-frameDepth)});

		Face[] faces = new Face[]{face,face2, face3, face4, face5, face6, face7, face8, face9, face10, face11, areaLight};
		camera = new Camera(Vector.origin,cameraFrame,faces);
		renderer = new Renderer();
		renderer.loadFaces(faces);
		renderer.bake(8);



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
