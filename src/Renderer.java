import java.awt.*;
import java.awt.image.*;
import java.util.*;
public class Renderer{
  ArrayList<Light> directLights;

  Dot[] bakedDots;

  Face[] faces;
  double totalRadiantFlux;

  double deltaPhi=2;
  double deltaTheta=2;
  double maxPhi=90;
  double maxTheta=360;

  public Renderer(){
    directLights=new ArrayList<Light>();
    totalRadiantFlux=0;
  }
  public void addLight(Light l){
    directLights.add(l);
  }
  public void loadFaces(Face[] fs){
    faces=fs;
  }
  public void bake(int maxPass){
    //render diffused lights on each dot
    for(int l=0; l<directLights.size(); l++){
      for(int f=0; f<faces.length; f++){
        Face targetFace = faces[f];
        for(int d=0; d<targetFace.dots.length; d++){
          Dot targetDot = targetFace.dots[d]; //for each target dot
          Dot sourceDot = new Dot(directLights.get(l).position, directLights.get(l));
          Vector ray = targetDot.position.minus(sourceDot.position); //draw line from light source to target dot
          Boolean dotIsIlluminated = true;
          if(ray.dot(targetFace.getNormal()) < 0){//if target face is facing the light source
            dotIsIlluminated=false;
          }
          else{ //check if there are any obstacles before reaching the target dot
            for(int fi=0; fi<faces.length; fi++){ //if the ray hits a face before reaching the dot in question, the dot won't be illuminated
              Vector intersection = faces[fi].getIntersection(sourceDot.position, ray);
              //if even a single face contains the intersection, the light source does not reach the dot in question
              if(faces[fi].contains(intersection)){
                dotIsIlluminated=false;
                break;
              }
            }
          }
          if(dotIsIlluminated){
            targetDot.light = getLight(ray, sourceDot, targetDot, targetFace);
          }
        }
      }
    }
    baker(maxPass,0);

    ArrayList<Dot> bakedDotList = new ArrayList<Dot>();
    for(int f=0; f<faces.length; f++){
      for(int d=0; d<faces[f].dots.length; d++){
        bakedDotList.add(faces[f].dots[d]);
      }
    }
    bakedDots = bakedDotList.toArray(new Dot[bakedDotList.size()]);
  }
  private void baker(int maxPass, int pass){
    if(pass<maxPass){
      for(int f=0; f<faces.length; f++){
        Face targetFace = faces[f];
        for(int d=0; d<targetFace.dots.length; d++){
          Dot targetDot = targetFace.dots[d]; //for each target dot
          for(int f2=0; f2<faces.length; f2++){
            Face sourceFace = faces[f2];
            for(int d2=0; d2<sourceFace.dots.length; d2++){
              Dot sourceDot = sourceFace.dots[d2]; //loop through all light source dots

              Vector ray = targetDot.position.minus(sourceDot.position); //draw line from light source to target dot
              Boolean dotIsIlluminated = true;
              if(ray.dot(targetFace.getNormal()) < 0 && ray.dot(sourceFace.getNormal()) > 0){//if ray hits a target face and if the ray points away from the source face
                dotIsIlluminated=false;
              }
              else{ //check if there are any obstacles before reaching the target dot
                for(int fi=0; fi<faces.length; fi++){ //if the ray hits a face before reaching the dot in question, the dot won't be illuminated
                  Vector intersection = faces[fi].getIntersection(sourceDot.position, ray);
                  //if even a single face contains the intersection, the light source does not reach the dot in question
                  if(faces[fi].contains(intersection)){
                    dotIsIlluminated=false;
                    break;
                  }
                }
              }
              if(dotIsIlluminated){
                targetDot.light = getLight(ray, sourceDot, targetDot, targetFace);
              }
            }
          }

        }
      }
      pass++;
      baker(maxPass,pass++);
    }
  }

  private Light getLight(Vector ray, Dot sourceDot, Dot targetDot, Face targetFace){
    double radius = ray.magnitude();
    double sphereArea = 4*Math.PI*Math.pow(radius,2);
    //radiant flux by the light on the dot is the dot area divided by the total spherical area at that distance
    double deltaRadiantFlux = sourceDot.light.radiantFlux * (targetFace.dotArea/sphereArea); //radiantflux on a dot is inversly proportional to the distance squared
    float r,g,b;
    r=sourceDot.light.color.getRed()*(float)deltaRadiantFlux*targetDot.light.color.getRed();
    g=sourceDot.light.color.getGreen()*(float)deltaRadiantFlux*targetDot.light.color.getGreen();
    b=sourceDot.light.color.getBlue()*(float)deltaRadiantFlux*targetDot.light.color.getBlue();
    Color c = new Color(r,g,b);
    return new Light(targetDot.position,deltaRadiantFlux,c);

  }
}
