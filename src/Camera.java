import java.awt.image.*;
import java.awt.*;
public class Camera{
  Vector position;
  CameraFrame frame;
  public Camera(Vector p, CameraFrame cf){
    position=p;
    frame=cf;
  }
  public BufferedImage draw(Dot[] dots){
    BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_BGR);
    int[][] positions = new int[2][dots.length];
    for(int d=0; d<dots.length; d++){
      Vector intersection = frame.getIntersection(dots[d].position, position.minus(dots[d].position));
      Vector fromCamera = intersection.minus(position);
      //System.out.println(fromCamera);
      positions[0][d]=(int)Math.round(fromCamera.getX());
      positions[1][d]=(int)Math.round(fromCamera.getY());
    }
    int startIndex=0;
    float rSum=0;
    float gSum=0;
    float bSum=0;
    for(int i=0; i<positions[0].length; i++){
      if(positions[0][i]==positions[0][startIndex] && positions[1][i]==positions[1][startIndex]){
        rSum=dots[i].light.color.getRed();
        gSum=dots[i].light.color.getGreen();
        bSum=dots[i].light.color.getBlue();
      }
      else{
        float change = i-startIndex;
        System.out.println(positions[0][i]+", "+positions[1][i]);
        //System.out.println(rSum/change/255+", "+gSum/change/255+", "+bSum/change/255);
        //Color c = new Color(rSum/change/255,gSum/change/255,bSum/change/255);
        System.out.println(rSum/255+", "+gSum/255+", "+bSum/255);
        Color c = new Color(rSum/255,gSum/255,bSum/255);
        image.setRGB(positions[0][i]/2+frame.getWidth()/2,positions[1][i]/2+frame.getHeight()/2,c.getRGB());
        startIndex=i;
      }
    }
    return image;
  }
}
