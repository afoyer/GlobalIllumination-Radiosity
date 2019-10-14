import java.awt.*;

/**
 * Light holds a radiant flux which is the strength (or "brightness") of that light, a position and a Color which are set
 * at construction.
 */
public class Light{
  double radiantFlux;
  Color color;
  Vector position;

  /**
   * Constructor. Sets position, radiant flux and light color.
   * @param p - position of light
   * @param rf - radiant flux of light
   * @param c - color of light
   */
  public Light(Vector p,double rf,Color c){
    position=p;
    radiantFlux=rf;
    color=c;
  }
}
