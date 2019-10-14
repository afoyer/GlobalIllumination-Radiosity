import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

class GUI extends JPanel implements ChangeListener
 {
 	Canvas canvas;
	JSlider xSlider,ySlider;
 	public GUI(Canvas canvas)
	{
		this.canvas=canvas;

		setLayout(new GridLayout(1,3,30,10));

    xSlider = new JSlider(JSlider.VERTICAL,-1000,1000,0);
		xSlider.addChangeListener(this);
		JLabel label = new JLabel("x pos");
		JPanel x = new JPanel();
		x.setLayout(new BoxLayout(x, BoxLayout.Y_AXIS));
		x.add(label);
    x.add(xSlider);
		add(x);

    ySlider = new JSlider(JSlider.VERTICAL,-1000,1000,0);
		ySlider.addChangeListener(this);
		JLabel ylabel = new JLabel("y pos");
		JPanel y = new JPanel();
		y.setLayout(new BoxLayout(y, BoxLayout.Y_AXIS));
		y.add(label);
    y.add(ySlider);
		add(y);

   }//end contructor

   public void stateChanged(ChangeEvent ev)
   {
     canvas.camera.position=canvas.camera.position.setX(-xSlider.getValue()).setY(-ySlider.getValue());
     canvas.repaint();
	 }//end stateChanged

}
