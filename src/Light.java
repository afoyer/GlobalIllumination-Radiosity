import java.awt.*;
public class Light{
  double radiantFlux;
  Color color;
  Vector position;
  Boolean isDirect; //is a direct light source
  Vector normal; //if it's a diffused light source, it's face will have a normal
  public Light(Vector p,double rf,Boolean id,Color c){
    position=p;
    radiantFlux=rf;
    color=c;
    isDirect=id;
  }
  public void setNormal(Vector v){
    normal=v;
  }
  public Vector getNormal(){
    return normal;
  }
}
