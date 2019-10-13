public class Dot{
  Light light;
  Vector position;
  public Dot(Vector position){
    this.position = position;
  }
  public Vector getPosition(){
    return position;
  }
  public void setLight(Light light){
      this.light = light;
  }
}
