import java.awt.*;
public class Face{
  Vector[] vertices;
  Vector normal;
  Color color;
  Dot[] dots;
  double dotArea;
  public Face(Vector[] vertices){
    this.vertices = vertices;
    normal = vertices[1].minus(vertices[0]).cross(vertices[2].minus(vertices[1]));
  }

  public boolean contains(Vector point){
    double area1 = getArea(vertices[0], vertices[1], point, 2);
    double area2 =getArea(vertices[1], vertices[2], point , 2);
    double area3 = getArea(vertices[2], vertices[3], point , 2);
    double area4 = getArea(vertices[3], vertices[0], point , 2);
    double totalarea = getArea(vertices[0], vertices[1], vertices[2] , 1);
    double totalPointArea = area1 + area2 + area3 + area4;
    if(Math.abs(totalPointArea - totalarea) > 0.0001){
      return true;
    }
    return false;

  }
  public Vector getIntersection(Vector start, Vector direction){
    if(normal.dot(direction.normalize()) ==0){
      return null;
    }
    double t = ((normal.dot(vertices[0]) - normal.dot(start))/ normal.dot(direction.normalize()));

    return start.add(direction.normalize().scale(t));
  }
  public void generateDots(double dotArea){
    this.dotArea=dotArea;
    //dots =
  }
  public Vector getNormal(){
        return normal;

  }
  public double getArea(Vector v1, Vector v2, Vector v3, double factor){
    Vector v1v2 = v2.minus(v1);
    Vector v2v3 = v3.minus(v2);
    return (v1v2.cross(v2v3).magnitude())/factor;
  }


}
