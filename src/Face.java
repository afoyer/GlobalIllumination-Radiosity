import java.awt.*;
public class Face{
  Vector[] vertices;
  Vector normal;
  Color color;
  Vector[] dots;
  double dotArea;
  public Face(Vector[] vertices){
    this.vertices = vertices;
    normal = vertices[1].minus(vertices[0]).cross(vertices[2].minus(vertices[1]));
  }

  public boolean contains(Vector point){
    return false;
  }
  public Vector getIntersection(Vector start, Vector direction){
    return null;
  }
  public void generateDots(double dotArea){
    this.dotArea=dotArea;
    //dots = 
  }
  public Vector getNormal(){
        return normal;

  }

}
