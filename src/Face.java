public class Face{
  Vector[] vertices;
  Vector normal;
  public Face(Vector[] vertices){
    this.vertices = vertices;
    normal = getNormal(vertices);


  }

  public boolean contains(Vector point){
    return false;
  }
  public Vector getIntersection(Vector start, Vector direction){
    return null;
  }
  public Vector getNormal(Vector[] points){
        return points[1].minus(points[0].cross(points[2].minus(points[1])));

  }

}
