import java.awt.*;

public class Face{
  /**
   * Face holds Vector point vertices, normal, and dots
   */
  Vector[] vertices;
  Vector normal;
  private Color color;
  Dot[] dots;
  double dotArea;
  private double totalarea;

  /**
   * Sets up points for the face. calculates normal vector.
   * @param vertices - vertices of the face.
   */
  public Face(Vector[] vertices){
    this.vertices = vertices;
    normal = vertices[1].minus(vertices[0]).cross(vertices[2].minus(vertices[1])).normalize();
    totalarea = calculateArea(vertices[0], vertices[1], vertices[2] , 1);
  }

  /**
   * checks if point lies within face on the same plane using area equations. if the area of the point and all other points of the face is greater
   * than the area of the face, returns false.
   * @param point - point to check if inside face
   * @return - true if point lies inside face.
   */
  public boolean contains(Vector point){
    if(point!=null){
      double area1 = calculateArea(vertices[0], vertices[1], point, 2);
      double area2 = calculateArea(vertices[1], vertices[2], point , 2);
      double area3 = calculateArea(vertices[2], vertices[3], point , 2);
      double area4 = calculateArea(vertices[3], vertices[0], point , 2);
      double totalPointArea = area1 + area2 + area3 + area4;
      if(Math.abs(totalPointArea - totalarea) < 0.0001){
        return true;
      }
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

    return start.plus(direction.normalize().scale(t));
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
  public void generateDots(double dotArea,double initialRadiantFlux){
    this.dotArea=dotArea;
     //Unsure if this will be one below.
    int counter = 0;
    double length =Math.sqrt(dotArea); //Movement indicator (not area)
    Vector vectorH = vertices[3].minus(vertices[0]);
    Vector vectorV = vertices[1].minus(vertices[0]);

    int sectionV = (int) (vectorV.magnitude()/length); //get
    int sectionH = (int) (vectorH.magnitude()/length);
    dots = new Dot[(sectionH-1)*(sectionV-1)];

    for(double i = 1; i < sectionH; i++){
      Vector vH = vertices[0].plus(vectorH.scale(i/sectionH));//shift on "horizontal" vector
      vH = vH.minus(vertices[0]);
      for(double j = 1; j < sectionV; j++){
        Vector vV = vertices[0].plus(vectorV.scale(j/sectionV));//shift on "horizontal" vector
        vV = vV.minus(vertices[0]);
        vV = vV.plus(vH);
        Vector position = new Vector(vertices[0].plus(vV));
        dots[counter] = new Dot(position);
        dots[counter].matColor = color;
        dots[counter].setLight(new Light(position,initialRadiantFlux,color));

        double buffer=0.1*length;
        dots[counter].vertices[0]=position.minus(vectorH.normalize().scale(buffer+length/2)).minus(vectorV.normalize().scale(buffer+length/2));
        dots[counter].vertices[1]=position.minus(vectorH.normalize().scale(buffer+length/2)).plus(vectorV.normalize().scale(buffer+length/2));
        dots[counter].vertices[2]=position.plus(vectorH.normalize().scale(buffer+length/2)).plus(vectorV.normalize().scale(buffer+length/2));
        dots[counter].vertices[3]=position.plus(vectorH.normalize().scale(buffer+length/2)).minus(vectorV.normalize().scale(buffer+length/2));
        counter++;
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
  public double calculateArea(Vector v1, Vector v2, Vector v3, double factor){
    Vector v1v2 = v2.minus(v1);
    Vector v2v3 = v3.minus(v2);
    return (v1v2.cross(v2v3).magnitude())/factor;
  }
  /**
   * Gets total area of face
   * @return - area of the face
   */
  public double getTotalarea(){
    return totalarea;
  }
  /**
   * Gets color
   * @return - the color of the face
   */
  public Color getColor(){
    return color;
  }
  /**
   * Sets color
   * @param color
   */
  public void setColor(Color color){
    this.color = color;
  }


}
