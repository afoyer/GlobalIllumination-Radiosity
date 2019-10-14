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
          ArrayList<Dot> sourceDots = new ArrayList<Dot>();
          ArrayList<Vector> rays = new ArrayList<Vector>();
          Dot sourceDot = new Dot(directLights.get(l).position);
          sourceDot.setLight(directLights.get(l));
          Vector ray = targetDot.position.minus(sourceDot.position); //draw line from light source to target dot

          Boolean dotIsIlluminatedBySource = true;
          //check if the target dot is illuminated by the direct light source
          if(ray.getUnit().dot(targetFace.getNormal()) > 0){//if target face isn't facing the light source
            dotIsIlluminatedBySource=false;
          }
          else{ //check if there are any obstacles before reaching the target dot
            for(int fi=0; fi<faces.length; fi++){ //if the ray hits a face before reaching the dot in question, the dot won't be illuminated
              if(faces[fi]!=targetFace){
                Vector intersection = faces[fi].getIntersection(sourceDot.position, ray);
                //System.out.println(intersection);
                //if even a single face contains the intersection, the light source does not reach the dot in question
                if(faces[fi].contains(intersection)){
                  if(intersection.minus(sourceDot.position).magnitude()<ray.magnitude()){
                    dotIsIlluminatedBySource=false;
                    break;
                  }
                }
              }
            }
          }
          if(dotIsIlluminatedBySource){
            sourceDots.add(sourceDot);
            rays.add(ray);
          }
          targetDot.renderedColor = renderColor(rays, sourceDots, targetDot, targetFace,-1);
        }
      }
    }
    /*
    for(int f=0; f<faces.length; f++){
      for(int d=0; d<faces[f].dots.length; d++){
        System.out.println(faces[f].dots[d].light.radiantFlux);
      }
    }
    */
    globalIlluminationBaker(maxPass,0);

    //save the dots in each face into an array of dots, a kind of a lightmap
    ArrayList<Dot> bakedDotList = new ArrayList<Dot>();
    for(int f=0; f<faces.length; f++){
      for(int d=0; d<faces[f].dots.length; d++){
        bakedDotList.add(faces[f].dots[d]);
      }
    }
    bakedDots = bakedDotList.toArray(new Dot[bakedDotList.size()]);
    System.out.println("baking finished");
  }
  //calculates light and color at each dot from diffused light source from other dots
  private void globalIlluminationBaker(int maxPass, int pass){
    if(pass<maxPass){
      for(int f=0; f<faces.length; f++){
        Face targetFace = faces[f];
        for(int d=0; d<targetFace.dots.length; d++){
          Dot targetDot = targetFace.dots[d]; //for each target dot
          ArrayList<Dot> sourceDots = new ArrayList<Dot>();
          ArrayList<Vector> rays = new ArrayList<Vector>();
          for(int f2=0; f2<faces.length; f2++){
            Face sourceFace = faces[f2];
            if(sourceFace!=targetFace){
              for(int d2=0; d2<sourceFace.dots.length; d2++){
                Dot sourceDot = sourceFace.dots[d2]; //loop through all light source dots
                //System.out.println("0. "+targetDot.light.radiantFlux);
                Vector ray = targetDot.position.minus(sourceDot.position); //draw line from light source to target dot
                Boolean dotIsIlluminatedBySource = true;
                //checking if the light from the source dot can reach the target dot
                if(ray.getUnit().dot(targetFace.getNormal()) > 0 || ray.getUnit().dot(sourceFace.getNormal()) < 0){//if ray doesn't hit the target face or if the ray points towards from the source face
                  dotIsIlluminatedBySource=false;
                }
                else{ //check if there are any obstacles before reaching the target dot
                  for(int fi=0; fi<faces.length; fi++){ //if the ray hits a face before reaching the dot in question, the dot won't be illuminated
                    if(faces[fi]!=targetFace){
                      Vector intersection = faces[fi].getIntersection(sourceDot.position, ray);
                      //if even a single face contains the intersection, the light source does not reach the dot in question
                      if(faces[fi].contains(intersection)){
                        if(intersection.minus(sourceDot.position).magnitude()<ray.magnitude()){
                          dotIsIlluminatedBySource=false;
                          break;
                        }
                      }
                    }
                  }
                }
                if(dotIsIlluminatedBySource){
                  sourceDots.add(sourceDot);
                  rays.add(ray);
                }
              }
            }
          }
          targetDot.renderedColor = renderColor(rays, sourceDots, targetDot, targetFace,pass);
        }
      }
      pass++;
      globalIlluminationBaker(maxPass,pass++);
    }
  }

  private Color renderColor(ArrayList<Vector> rays, ArrayList<Dot> sourceDots, Dot targetDot, Face targetFace, int pass){
    //radiant flux by the light on the dot is the dot area divided by the total spherical area at that distance
    float rLight,gLight,bLight;
    rLight=0;
    gLight=0;
    bLight=0;
    double totalRadiantFlux=0;
    for(int i=0; i<sourceDots.size(); i++){
      double radius = rays.get(i).magnitude();
      double deltaRadiantFlux = sourceDots.get(i).light.radiantFlux * (1/Math.pow(radius,2)); //radiantflux on a dot is inversly proportional to the distance squared
      totalRadiantFlux+=deltaRadiantFlux;
      rLight+=(float)deltaRadiantFlux*(float)sourceDots.get(i).light.color.getRed()/255;
      gLight+=(float)deltaRadiantFlux*(float)sourceDots.get(i).light.color.getGreen()/255;
      bLight+=(float)deltaRadiantFlux*(float)sourceDots.get(i).light.color.getBlue()/255;
    }
    if(rLight>1){
      rLight=1;
    }
    if(gLight>1){
      gLight=1;
    }
    if(bLight>1){
      bLight=1;
    }

    float rRendered,gRendered,bRendered;
    rRendered=targetDot.matColor.getRed()/255*rLight;
    gRendered=targetDot.matColor.getGreen()/255*gLight;
    bRendered=targetDot.matColor.getBlue()/255*bLight;
    Color c = new Color(rRendered,gRendered,bRendered);
    targetDot.setLight(new Light(targetDot.position,totalRadiantFlux,c));
    return c;

  }
}
