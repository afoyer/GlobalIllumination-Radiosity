import java.util.*;
import java.awt.*;
public class Dot{
  Light light;
  Color matColor;
  Color renderedColor;
  Vector position;
  public Dot(Vector position){
    this.position = position;
  }
  public Vector getPosition(){
    return position;
  }
  public void setLight(Light light){
      this.light=light;
  }
}
