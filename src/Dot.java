import java.util.*;
import java.awt.*;
public class Dot{
  Light light;
  Color matColor;
  Color renderedColor;
  Vector position;
  Color sourceLightColor;
  public Dot(Vector position){
    this.position = position;
    sourceLightColor=Color.black;
  }
  public Vector getPosition(){
    return position;
  }
  public void setLight(Light light){
      this.light=light;
  }
}
