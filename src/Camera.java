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
    for(int i=0; i<frame.getWidth(); i++){
      for(int j=0; j<frame.getHeight(); j++){
      }
    }
    //image.setRGB(i,j,c.getRGB());
    return image;
  }
}
