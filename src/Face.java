import java.awt.*;
public class Face{
  Vector[] vertices;
  Vector normal;
  Color color;
  Dot[] dots;
  double dotArea;
  double totalarea;

  /**
   * Sets up points for the face. calculates normal vector.
   * @param vertices - vertices of the face.
   */
  public Face(Vector[] vertices){
    this.vertices = vertices;
    normal = vertices[1].minus(vertices[0]).cross(vertices[2].minus(vertices[1])).getUnit();
    totalarea = getArea(vertices[0], vertices[1], vertices[2] , 1);
  }

  /**
   * checks if point lies within face on the same plane using area equations. if the area of the point and all other points of the face is greater
   * than the area of the face, returns false.
   * @param point - point to check if inside face
   * @return - true if point lies inside face.
   */
  public boolean contains(Vector point){
    double area1 = getArea(vertices[0], vertices[1], point, 2);
    double area2 =getArea(vertices[1], vertices[2], point , 2);
    double area3 = getArea(vertices[2], vertices[3], point , 2);
    double area4 = getArea(vertices[3], vertices[0], point , 2);
    double totalPointArea = area1 + area2 + area3 + area4;
    if(Math.abs(totalPointArea - totalarea) < 0.0001){
      return true;
    }
    return false;

  }

  /**
   * Gets the intersection point between a line(point and vector) and a plane. returns null if no point is found.
   * @param start - starting point
   * @param direction - vector direction of line
   * @return - point on plane
   */
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
    this.dotArea=dotArea;
     //Unsure if this will be one below.
    int counter = 0;
    double length =Math.sqrt(dotArea); //Movement indicator (not area)
    Vector vectorH = vertices[3].minus(vertices[0]);
    Vector vectorV = vertices[1].minus(vertices[0]);

    double sectionV = vectorV.magnitude()/length; //get
    double sectionH = vectorH.magnitude()/length;
    dots = new Dot[ (int) ((sectionH-1)*(sectionH-1))];

    for(double i = 1; i < sectionH; i += 1){
      double xHmov = vertices[0].getX() + i/sectionH*(vectorH.getX()); //shift in x position on "horizontal" vector
      double yHmov = vertices[0].getY() + i/sectionH*(vectorH.getY()); //shift in y position on "horizontal" vector
      double zHmov = vertices[0].getZ() + i/sectionH*(vectorH.getZ()); //shift in z position on "horizontal" vector
      Vector vH = new Vector(xHmov,yHmov,zHmov);
      vH = vH.minus(vertices[0]);
      for(double j = 1; j < sectionV; j+= 1){
        double xVmov = vertices[0].getX() + j/sectionV*(vectorV.getX()); //shift in x position on "vertical" vector
        double yVmov = vertices[0].getY() + j/sectionV*(vectorV.getY()); //shift in y position on "vertical" vector
        double zVmov = vertices[0].getZ() + j/sectionV*(vectorV.getZ()); //shift in z position on "vertical" vector
        Vector vV = new Vector(xVmov,yVmov,zVmov);
        vV = vV.minus(vertices[0]);
        vV = vV.add(vH);
        Vector position = new Vector(vertices[0].add(vV));
        dots[counter] = new Dot(position);
        dots[counter].setLight(new Light(position,0,color));
        counter ++;
      }
    }
  }

  /**
   * Gets normal vector of the face
   * @return - the normal vector.
   */
  public Vector getNormal(){
        return normal;

  }

  /**
   * Gets the area of a polygon using 3 points, can also be used for triangle if factor is set to 2
   * @param v1 - point a
   * @param v2 - point b (must link to a)
   * @param v3 - point c (must link to b)
   * @param factor - dividing factor (used to calculate area of triangle
   * @return - area of polygon.
   */
  public double getArea(Vector v1, Vector v2, Vector v3, double factor){
    Vector v1v2 = v2.minus(v1);
    Vector v2v3 = v3.minus(v2);
    return (v1v2.cross(v2v3).magnitude())/factor;
  }


}
