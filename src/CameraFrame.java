public class CameraFrame extends Face{
  /**
   * The camera view frame.
   */
   /**
    * Sets up points for the face. calculates normal vector.
    * @param vertices
    */
  public CameraFrame(Vector[] vertices){
    super(vertices);
  }
  /**
   * gets the width of the frame
   * @return the width of the frame
   */
  public int getWidth(){
    return (int)Math.round(vertices[0].minus(vertices[1]).magnitude());
  }
  /**
   * gets the height of the frame
   * @return the height of the frame
   */
  public int getHeight(){
    return (int)Math.round(vertices[1].minus(vertices[2]).magnitude());
  }
}
