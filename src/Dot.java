import java.util.*;
import java.awt.*;
public class Dot{
  /**
   * Dot represents "pixels" on each face. The smallest unit of light is a dot.
   */
  Light light;
  Color matColor;
  Color renderedColor;
  Vector position;
  Color sourceLightColor;
  Vector[] vertices;
  /**
   * Constructor. Sets position.
   * @param position - position of dot
   */
  public Dot(Vector position){
    this.position = position;
    sourceLightColor=Color.black;
    vertices = new Vector[4];
  }
  /**
   * Gets position
   * @return the Vector position
   */
  public Vector getPosition(){
    return position;
  }
  /**
   * Sets the light at the dot
   * @param light
   */
  public void setLight(Light light){
      this.light=light;
  }
}
