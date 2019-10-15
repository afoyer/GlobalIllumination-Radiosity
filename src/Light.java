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
  public Light[] createLightGrid(Vector p,int length, int density, boolean horizontal){
    Light[] lightarray = new Light[(int)(length/density*(length/density))];
    int counter = 0;
    if (horizontal){
      for (int i = 0; i < length/density; i++){
        for(int j = 0; j < length/density; i++){
          lightarray[counter] = new Light((p.plus(new Vector(j*density,0,i*density))), radiantFlux/(length/density*(length/density)),color);
          counter++;
        }
      }
    }
    else{
      for (int i = 0; i < length/density; i++){
        for(int j = 0; j < length/density; j++){
          lightarray[counter] = new Light((p.plus(new Vector(i*density,j*density, 0))), radiantFlux / (length/density),color);
          counter++;
        }
      }

    }
    return lightarray;
  }
}
