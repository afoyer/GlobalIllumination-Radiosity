import java.awt.*;
public class Light{
  double radiantFlux;
  Color color;
  Vector position;
  public Light(Vector p,double rf,Color c){
    position=p;
    radiantFlux=rf;
    color=c;
  }
}
