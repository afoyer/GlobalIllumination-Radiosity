import java.awt.*;
import java.awt.image.*;
import java.util.*;
public class Renderer{
  ArrayList<Light> lights;
  Face[] faces;
  int width;
  int height;
  double totalBrightness;
  Camera camera;

  public Renderer(Camera c, int width, int height){
    lights=new ArrayList<Light>();
    this.width=width;
    this.height=height;
    totalBrightness=0;
    camera=c;
  }
  public void addLight(Light l){
    lights.add(l);
  }
  public void loadFaces(Face[] fs){
    faces=fs;
  }
  public void renderIndirectLight(){
    for(int x=0; x<width; x++){
      for(int y=0; y<height; y++){ //for each pixel
        Vector start=camera.position;
        Vector direction=new Vector(x,y,camera.depth).subtract(start);
        for(int i=0; i<faces.length; i++){ //for each face
          Vector intersection = faces[i].getIntersection(start, direction);
          if(faces[i].contains(intersection)){
            double brightness=0;
            Color c=null;
            totalBrightness+=brightness;
            Light l = new Light(intersection,brightness,c);
            l.setPixel(x,y);
            lights.add(l);
          }
        }
      }
    }
  }
  public BufferedImage render(){
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
    for(int l=0; l<lights.size(); l++){
      Light light = lights.get(l);
      float r=0;
      float g=0;
      float b=0;
      float I=0;
      for(int l2=0; l2<lights.size(); l2++){
        I=(float)lights.get(l2).brightness;
        r+=lights.get(l2).color.getRed()*I;
        g+=lights.get(l2).color.getGreen()*I;
        b+=lights.get(l2).color.getBlue()*I;
      }
      Color c = new Color(r/(float)totalBrightness,g/(float)totalBrightness,b/(float)totalBrightness);
      image.setRGB(light.x,light.y,c.getRGB());
    }

    return null;
  }
}
