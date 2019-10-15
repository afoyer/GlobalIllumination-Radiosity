import java.util.*;
import java.awt.*;
public class Dot{
  Light light;
  Color matColor;
  Color renderedColor;
  Vector position;
  Color sourceLightColor;
  Vector[] vertices;
  public Dot(Vector position){
    this.position = position;
    sourceLightColor=Color.black;
    vertices = new Vector[4];
  }
  public Vector getPosition(){
    return position;
  }
  public void setLight(Light light){
      this.light=light;
  }
}
