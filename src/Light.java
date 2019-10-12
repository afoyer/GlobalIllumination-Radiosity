import java.awt.*;
public class Light{
  double brightness;
  Color color;
  Vector position;
  int x;
  int y;
  public Light(Vector p,double b, Color c){
    position=p;
    brightness=b;
    color=c;
  }
  public void setPixel(int x, int y){
    this.x=x;
    this.y=y;
  }
}
