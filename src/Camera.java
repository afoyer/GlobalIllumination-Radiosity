import java.awt.image.*;
public class Camera{
  Vector position;
  CameraFrame frame;
  public Camera(Vector p, CameraFrame cf){
    position=p;
    frame=cf;
  }
  public BufferedImage Draw(Light[] lights){
    BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_BGR);

    //image.setRGB(i,j,c.getRGB());
    return image;
  }
}
