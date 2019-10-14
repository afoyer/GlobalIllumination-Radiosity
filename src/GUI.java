import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

class GUI extends JPanel implements ChangeListener
 {
 	Canvas canvas;
	JSlider slider;
 	public GUI(Canvas canvas)
	{
		this.canvas=canvas;

		setLayout(new GridLayout(1,3,30,10));

    slider = new JSlider(JSlider.VERTICAL,-1000,1000,0);
		slider.addChangeListener(this);
		JLabel label = new JLabel("camera pos");
		JPanel x = new JPanel();
		x.setLayout(new BoxLayout(x, BoxLayout.Y_AXIS));
		x.add(label);
    x.add(slider);
		add(x);

   }//end contructor

   public void stateChanged(ChangeEvent ev)
   {
     canvas.camera.position=canvas.camera.position.setX(-slider.getValue());
     canvas.repaint();
	 }//end stateChanged

}
