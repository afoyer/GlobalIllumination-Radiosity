import java.awt.*;
import java.awt.image.*;
import java.util.*;
public class Renderer{
  ArrayList<Light> directLights;
  ArrayList<Light> indirectLights;
  Face[] faces;
  int width;
  int height;
  double totalRadiantFlux;
  Camera camera;

  double deltaPhi=2;
  double deltaTheta=2;
  double maxPhi=90;
  double maxTheta=360;

  public Renderer(Camera c, int width, int height){
    directLights=new ArrayList<Light>();
    indirectLights=new ArrayList<Light>();
    this.width=width;
    this.height=height;
    totalRadiantFlux=0;
    camera=c;
  }
  public void addLight(Light l){
    directLights.add(l);
  }
  public void loadFaces(Face[] fs){
    faces=fs;
  }
  private void generateIndirectLights(){
    for(int l=0; l<directLights.size(); l++){ //for each direct and indirect light source (unit area of radiant flux and color in question)
      for(double phi=0; phi<maxPhi; phi+=deltaPhi){ //for each coordinate on hemisphere shell around the origin
        for(double theta=0; theta<maxTheta; theta+=deltaTheta){
          Vector start = directLights.get(l).position;
          Vector target = directLights.get(l).getNormal().returnRotateX(phi,start).returnRotateZ(theta,start);
          for(int i=0; i<faces.length; i++){ //for each face
            Vector intersection = faces[i].getIntersection(start, target.minus(start));
            if(faces[i].contains(intersection)){ //if the face contains the intersection
              double deltaRadiantFlux = directLights.get(l).radiantFlux*deltaPhi*deltaTheta/(maxPhi*maxTheta); //the radiant flux of that unit area by the light source
              float r,g,b;
              r=directLights.get(i).color.getRed()*(float)deltaRadiantFlux*faces[i].color.getRed();
              g=directLights.get(i).color.getGreen()*(float)deltaRadiantFlux*faces[i].color.getGreen();
              b=directLights.get(i).color.getBlue()*(float)deltaRadiantFlux*faces[i].color.getBlue();
              Color c = new Color(r,g,b);
              indirectLights.add(new Light(intersection,deltaRadiantFlux,false,c));
            }
          }
        }
      }
    }
  }
  public Boolean bake(int maxPass, int pass){ //calculate the indirect diffused light sources
    if(pass==0){
      generateIndirectLights();
    }
    for(int l=0; l<indirectLights.size(); l++){ //for each indirect light source (unit area of radiant flux and color in question)
      if(pass<maxPass){
        for(int ls=0; ls<indirectLights.size(); ls++){ //consider every other light source and calculate the radiant flux at the current point
          if(ls!=l){//excluding self
            double deltaRadiantFlux = indirectLights.get(ls).radiantFlux*deltaPhi*deltaTheta/(maxPhi*maxTheta);
            float r,g,b;
            r=indirectLights.get(ls).color.getRed()*(float)deltaRadiantFlux*indirectLights.get(l).color.getRed();
            g=indirectLights.get(ls).color.getGreen()*(float)deltaRadiantFlux*indirectLights.get(l).color.getGreen();
            b=indirectLights.get(ls).color.getBlue()*(float)deltaRadiantFlux*indirectLights.get(l).color.getBlue();
            Color c = new Color(r,g,b);
            Light light = new Light(indirectLights.get(l).position,deltaRadiantFlux,false,c);
            indirectLights.set(l,light);
          }
        }
        pass++;
        bake(maxPass,pass++);
      }
    }
    return true; //returns true when baking is finished
  }
}
