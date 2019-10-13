import java.awt.*;
public class Face{
  Vector[] vertices;
  Vector normal;
  Color color;
  Dot[] dots;
  double dotArea;
  double totalarea;
  public Face(Vector[] vertices){
    this.vertices = vertices;
    normal = vertices[1].minus(vertices[0]).cross(vertices[2].minus(vertices[1]));
    totalarea = getArea(vertices[0], vertices[1], vertices[2] , 1);
  }

  public boolean contains(Vector point){
    double area1 = getArea(vertices[0], vertices[1], point, 2);
    double area2 =getArea(vertices[1], vertices[2], point , 2);
    double area3 = getArea(vertices[2], vertices[3], point , 2);
    double area4 = getArea(vertices[3], vertices[0], point , 2);
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

  /**
   * generateDots goes through intersecting vectors and generates dots in between them using an area variable
   * goes -->  once then fills dots downwards. then moves --> again using loops
   *     ____________           ____________
   *   /  .         /         /  . .       /    and so forth.
   *  /  .         /   -->   /  . .       /
   * /____________/         /____________/

   * @param dotArea - area that the dot would create between each dots.
   */
  public void generateDots(double dotArea){
    dots = new Dot[(int)(totalarea/dotArea)]; //Unsure if this will be one below.
    int counter = 0;
    double length =Math.sqrt(dotArea); //Movement indicator (not area)
    Vector vectorH = vertices[3].minus(vertices[0]);
    Vector vectorV = vertices[1].minus(vertices[0]);
    double sectionV = vectorV.magnitude()/length; //get
    double sectionH = vectorH.magnitude()/length;

    for(double i = length; i < sectionH; i += length){
      double xHmov = vertices[0].getX() + i*vectorH.getX(); //shift in x position on "horizontal" vector
      double yHmov = vertices[0].getY() + i*vectorH.getY(); //shift in y position on "horizontal" vector
      double zHmov = vertices[0].getZ() + i*vectorH.getZ(); //shift in z position on "horizontal" vector
      for(double j = length; j < sectionV; j+= length){
        double xVmov = vertices[0].getX() + vectorV.getX()*j; //shift in x position on "vertical" vector
        double yVmov = vertices[0].getY() + vectorV.getX()*j; //shift in y position on "vertical" vector
        double zVmov = vertices[0].getZ() + vectorV.getX()*j; //shift in z position on "vertical" vector
        dots[counter] = new Dot(new Vector(xHmov+xVmov, yHmov+yVmov, zHmov+zVmov));
        counter ++;
      }
    }
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
