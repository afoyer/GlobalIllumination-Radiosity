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
    //render diffused lights on each dot from the direct light source
    for(int l=0; l<directLights.size(); l++){
      for(int f=0; f<faces.length; f++){
        Face targetFace = faces[f];
        for(int d=0; d<targetFace.dots.length; d++){
          Dot targetDot = targetFace.dots[d]; //for each target dot
          Dot sourceDot = new Dot(directLights.get(l).position);
          sourceDot.setLight(directLights.get(l));
          Vector ray = targetDot.position.minus(sourceDot.position); //draw line from light source to target dot

          Boolean dotIsIlluminated = true;
          //check if the target dot is illuminated by the direct light source
          if(ray.getUnit().dot(targetFace.getNormal()) > 0){//if target face isn't facing the light source
            dotIsIlluminated=false;
          }
          else{ //check if there are any obstacles before reaching the target dot
            for(int fi=0; fi<faces.length; fi++){ //if the ray hits a face before reaching the dot in question, the dot won't be illuminated
              if(faces[fi]!=targetFace){
                Vector intersection = faces[fi].getIntersection(sourceDot.position, ray);
                //if even a single face contains the intersection, the light source does not reach the dot in question
                if(faces[fi].contains(intersection)){
                  dotIsIlluminated=false;
                  break;
                }
              }
            }
          }
          if(dotIsIlluminated){
            //System.out.println(ray.magnitude());
            targetDot.setLight(getLight(ray, sourceDot, targetDot, targetFace));
          }
        }
      }
    }
    globalIlluminationBaker(maxPass,0);

    //save the dots in each face into an array of dots, a kind of a lightmap
    ArrayList<Dot> bakedDotList = new ArrayList<Dot>();
    for(int f=0; f<faces.length; f++){
      for(int d=0; d<faces[f].dots.length; d++){
        bakedDotList.add(faces[f].dots[d]);
      }
    }
    bakedDots = bakedDotList.toArray(new Dot[bakedDotList.size()]);
  }
  //calculates light and color at each dot from diffused light source from other dots
  private void globalIlluminationBaker(int maxPass, int pass){
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
              //checking if the light from the source dot can reach the target dot
              if(ray.getUnit().dot(targetFace.getNormal()) < 0 && ray.getUnit().dot(sourceFace.getNormal()) > 0){//if ray hits a target face and if the ray points away from the source face
                dotIsIlluminated=false;
              }
              else{ //check if there are any obstacles before reaching the target dot
                for(int fi=0; fi<faces.length; fi++){ //if the ray hits a face before reaching the dot in question, the dot won't be illuminated
                  if(faces[fi]!=targetFace){
                    Vector intersection = faces[fi].getIntersection(sourceDot.position, ray);
                    //if even a single face contains the intersection, the light source does not reach the dot in question
                    if(faces[fi].contains(intersection)){
                      dotIsIlluminated=false;
                      break;
                    }
                  }
                }
              }
              //if the light reaches the target dot, calculate the light value at the target dot
              if(dotIsIlluminated){
                targetDot.setLight(getLight(ray, sourceDot, targetDot, targetFace));
              }
            }
          }

        }
      }
      pass++;
      globalIlluminationBaker(maxPass,pass++);
    }
  }

  private Light getLight(Vector ray, Dot sourceDot, Dot targetDot, Face targetFace){
    double radius = ray.magnitude();
    double sphereArea = 4*Math.PI*Math.pow(radius,2);
    //radiant flux by the light on the dot is the dot area divided by the total spherical area at that distance
    //System.out.println("source radiantFlux: "+(float)sourceDot.light.radiantFlux);
    //System.out.println("dot area: "+targetFace.dotArea);
    //System.out.println("sphere area: "+sphereArea);
    double deltaRadiantFlux = sourceDot.light.radiantFlux * (targetFace.dotArea/sphereArea); //radiantflux on a dot is inversly proportional to the distance squared
    float r,g,b;
    r=sourceDot.light.color.getRed()/255*(float)deltaRadiantFlux*targetDot.light.color.getRed()/255;
    //System.out.println("sourcedot r: "+sourceDot.light.color.getRed()/255);
    //System.out.println("deltaRadiantFlux: "+(float)deltaRadiantFlux);
    //System.out.println("r: "+r);
    g=sourceDot.light.color.getGreen()/255*(float)deltaRadiantFlux*targetDot.light.color.getGreen()/255;
    b=sourceDot.light.color.getBlue()/255*(float)deltaRadiantFlux*targetDot.light.color.getBlue()/255;
    Color c = new Color(r,g,b);
    return new Light(targetDot.position,deltaRadiantFlux,c);

  }
}
