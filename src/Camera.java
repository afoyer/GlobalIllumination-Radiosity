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
    Graphics2D g2d = image.createGraphics();
    ArrayList<Pixel> pixels = new ArrayList<Pixel>();
    for(int f=0; f<faces.length; f++){
      Dot[] dots = faces[f].dots;
      for(int d=0; d<dots.length; d++){
        Vector ray = dots[d].position.minus(position);
        Boolean dotIsVisible = true;
        for(int fi=0; fi<faces.length; fi++){ //check all other faces for intersection
          if(faces[fi]!=faces[f]){ //excluding self
            Vector faceIntersection = faces[fi].getIntersection(position,ray);
            if(faces[fi].contains(faceIntersection)){
              if(faceIntersection.minus(position).magnitude() < ray.magnitude()){ //if the intersected point is closer than the dot in question
                dotIsVisible=false;
              }
            }
          }
        }
        if(dotIsVisible){
          int g2dPixels[][] = new int[2][4];
          for(int i=0; i<g2dPixels[0].length; i++){
            Vector frameIntersection = frame.getIntersection(position, dots[d].vertices[i].minus(position));
            Vector cameraFrame = frameIntersection.minus(position);
            g2dPixels[0][i]=(int)cameraFrame.getX();
            g2dPixels[1][i]=(int)cameraFrame.getY();
          }
          g2d.setColor(dots[d].renderedColor);
          g2d.fillPolygon(g2dPixels[0],g2dPixels[1],4);
        }
      }
    }

    return image;
  }
}
