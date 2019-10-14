import java.awt.image.*;
import java.awt.*;
import java.util.*;
public class Camera{
  Vector position;
  CameraFrame frame;
  Face[] faces;
  public Camera(Vector p, CameraFrame cf, Face[] faces){
    position=p;
    frame=cf;
    this.faces=faces;
  }
  private class Pixel{
    Vector position;
    Color color;
    Pixel(Vector p, Color c){
      position=p;
      color=c;
    }
  }
  public BufferedImage draw(){
    BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_BGR);
    ArrayList<Pixel> pixels = new ArrayList<Pixel>();
    for(int f=0; f<faces.length; f++){
      Dot[] dots = faces[f].dots;
      for(int d=0; d<dots.length; d++){
        for(int fi=0; fi<faces.length; fi++){ //check all other faces for intersection
          if(faces[fi]!=faces[f]){ //excluding self
            Vector ray = dots[d].position.minus(position);
            Vector faceIntersection = faces[fi].getIntersection(position,ray);
            Boolean dotIsVisible = true;
            if(faces[fi].contains(faceIntersection)){
              if(faceIntersection.minus(position).magnitude() < ray.magnitude()){ //if the intersected point is closer than the dot in question
                dotIsVisible=false;
              }
            }
            if(dotIsVisible){
              Vector frameIntersection = frame.getIntersection(position, ray);
              Vector fromCamera = frameIntersection.minus(position);
              Color renderedDotColor = new Color(
                (float)dots[d].light.color.getRed()/255*(float)dots[d].light.radiantFlux,
                (float)dots[d].light.color.getGreen()/255*(float)dots[d].light.radiantFlux,
                (float)dots[d].light.color.getBlue()/255*(float)dots[d].light.radiantFlux
              );
              pixels.add(new Pixel(fromCamera,renderedDotColor));
            }
          }
        }
      }
    }

    int startIndex=0;
    float rSum=0;
    float gSum=0;
    float bSum=0;
    for(int i=0; i<pixels.size(); i++){
      int[] pixelPos = new int[]{(int)pixels.get(i).position.getX(),(int)pixels.get(i).position.getY()};
      int[] pixelStartPos = new int[]{(int)pixels.get(startIndex).position.getX(),(int)pixels.get(startIndex).position.getY()};
      if(pixelPos[0]==pixelStartPos[0] && pixelPos[1]==pixelStartPos[1]){
        rSum=(float)pixels.get(i).color.getRed()/255;
        gSum=(float)pixels.get(i).color.getGreen()/255;
        bSum=(float)pixels.get(i).color.getBlue()/255;
      }
      else{
        float change = i-startIndex;
        //System.out.println("pos :"+positions[0][i]+", "+positions[1][i]);
        //System.out.println(rSum+", "+gSum+", "+bSum);
        Color c = new Color(rSum,gSum,bSum);
        image.setRGB(pixelPos[0]+frame.getWidth()/2,pixelPos[1]+frame.getHeight()/2,c.getRGB());
        startIndex=i;
      }
    }
    return image;
  }
}
