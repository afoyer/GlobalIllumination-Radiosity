public class CameraFrame extends Face{
  public CameraFrame(Vector[] vertices){
    super(vertices);
  }
  public int getWidth(){
    return (int)Math.round(vertices[0].minus(vertices[1]).magnitude());
  }
  public int getHeight(){
    return (int)Math.round(vertices[1].minus(vertices[2]).magnitude());
  }
}
