import java.util.*;

/**
 * Vector holds an x, y and z position and can calculate various operations between itself and another vector.
 * Can also be used to be a simple point in 3 dimensions.
 */
public class Vector{
  double x;
  double y;
  double z;
  public static Vector origin = new Vector(0,0,0);
  public static Vector xHat = new Vector(1,0,0);
  public static Vector yHat = new Vector(0,1,0);
  public static Vector zHat = new Vector(0,0,1);

    /**
     * Constructor using new coordinates
     * @param x - x coordinate
     * @param y - y coordinate
     * @param z - z coordinate
     */
  public Vector(double x,double y,double z){
    this.x=x;
    this.y=y;
    this.z=z;
  }

    /**
     * Constructor giving in another vector.
     * @param p - vector.
     */
  public Vector(Vector p){
    this.x=p.getX();
    this.y=p.getY();
    this.z=p.getZ();
  }

    /**
     * Finds the angle between two vectors.
     * @param v2 - other vector
     * @return angle (in radians)
     */
  public double angleBetween(Vector v2){ //return the angle between vectors in degrees
    return Math.acos(this.dot(v2)/this.magnitude()/v2.magnitude())*180/Math.PI;
  }

    /**
     * Rotates vector on X axis
     * returns a Vector instead of changing the current
     * @param angle
     * @param center
     * @return
     */
  public Vector rotateX(double angle, Vector center){
    double[] tempVector = new double[3];
    double rad = Math.toRadians(angle);
    double tx=getX()-center.getX();
    double ty=getY()-center.getY();
    double tz=getZ()-center.getZ();
    tempVector[0]=tx;
    tempVector[1]=ty*Math.cos(rad)-tz*Math.sin(rad);
    tempVector[2]=ty*Math.sin(rad)+tz*Math.cos(rad);
    return new Vector(tempVector[0]+center.getX(),tempVector[1]+center.getY(),tempVector[2]+center.getZ());
  }
    /**
     * Rotates vector on Y axis
     * returns a Vector instead of changing the current
     * @param angle
     * @param center
     * @return
     */
  public Vector rotateY(double angle, Vector center){//returns a Vector insteado of changing the current
    double[] tempVector = new double[3];
    double rad = Math.toRadians(angle);
    double tx=getX()-center.getX();
    double ty=getY()-center.getY();
    double tz=getZ()-center.getZ();
    tempVector[0]=tx*Math.cos(rad)+tz*Math.sin(rad);
    tempVector[1]=ty;
    tempVector[2]=-1*tx*Math.sin(rad)+tz*Math.cos(rad);
    return new Vector(tempVector[0]+center.getX(),tempVector[1]+center.getY(),tempVector[2]+center.getZ());
  }
    /**
     * Rotates vector on Z axis
     * returns a Vector instead of changing the current
     * @param angle
     * @param center
     * @return
     */
  public Vector rotateZ(double angle, Vector center){//returns a Vector insteado of changing the current
    double[] tempVector = new double[3];
    double rad = Math.toRadians(angle);
    double tx=getX()-center.getX();
    double ty=getY()-center.getY();
    double tz=getZ()-center.getZ();
    tempVector[0]=tx*Math.cos(rad)-ty*Math.sin(rad);
    tempVector[1]=tx*Math.sin(rad)+ty*Math.cos(rad);
    tempVector[2]=tz;
    return new Vector(tempVector[0]+center.getX(),tempVector[1]+center.getY(),tempVector[2]+center.getZ());
  }
  /**
   * Rotates vector on an arbitrary axis
   * returns a Vector
   * @param angle
   * @param center
   @param vector
   * @return the rotated vector
   */
  public Vector returnRotateOnVector(double angle, Vector center, Vector vector){//rotates by angle degrees on arb. axis. returns a Vector insteado of changing the current
    Vector v = vector.clone();
    if(!(v.getY()==1 && v.magnitude()==1)){
      v = vector.setY(0);
    }
    Vector tempV = this.clone();
    double toY = vector.angleBetween(Vector.yHat);
    double toZ = v.angleBetween(Vector.zHat);
    tempV = tempV.rotateY(-toZ,center);
    tempV = tempV.rotateX(-toY,center);
    tempV = tempV.rotateY(angle,center);
    tempV = tempV.rotateX(toY,center);
    tempV = tempV.rotateY(toZ,center);
    return tempV;
  }

    /**
     * Scales vector coordinates.
     * @param s - scalar variable
     * @return new vector with scaled coordinates.
     */
  public Vector scale(double s){ //scales the point
    return new Vector(getX()*s,getY()*s,getZ()*s);
  }

    /**
     * Adds vector and other vector to give out new vector.
     * @param p - other vector
     * @return - newly added vector.
     */
  public Vector plus(Vector p){ //adds vector
    return new Vector(x+p.getX(),y+p.getY(),z+p.getZ());
  }

    /**
     * takes current vector, subtracts other vector from it and return new vector with new coordinates
     * @param p - other vector
     * @return newly subtracted vector
     */
  public Vector minus(Vector p){ //subtracts vector
    return new Vector(x-p.getX(),y-p.getY(),z-p.getZ());
  }

    /**
     * Does a dot product between current vector and other vector
     * @param p - other vector
     * @return dot product as a double.
     */
  public double dot(Vector p){//dots a vector
    return x*p.getX()+y*p.getY()+z*p.getZ();
  }

    /**
     * Does a cross product between current vector and another vector and returns a new vector from it
     * @param p - other vector
     * @return - new crossed vector.
     */
  public Vector cross(Vector p){//crosses a vector
    return new Vector(getY()*p.getZ()-getZ()*p.getY(), getZ()*p.getX()-getX()*p.getZ(), getX()*p.getY()-getY()*p.getX());
  }

    /**
     * Gets x coordinate
     * @return x coordinate
     */
  public double getX(){

    return this.x;
  }
    /**
     * Gets y coordinate
     * @return y coordinate
     */
  public double getY(){

    return this.y;
  }
    /**
     * Gets z coordinate
     * @return z coordinate
     */
  public double getZ(){

    return this.z;
  }
    /**
     * Sets x coordinate
     * @return new vector with new x coordinate
     */
  public Vector setX(double x){

    return new Vector(x,this.y,this.z);
  }
    /**
     * Sets y coordinate
     * @return new vector with new y coordinate
     */
  public Vector setY(double y){

    return new Vector(this.x,y,this.z);
  }
    /**
     * Sets z coordinate
     * @return new vector with new z coordinate
     */
  public Vector setZ(double z){

    return new Vector(this.x,this.y,z);
  }

    /**
     * Gets magnitude of vector.
     * @return magnitude of vector.
     */
  public double magnitude(){ //gets magnitude of this

    return Math.sqrt(Math.pow(getX(),2)+Math.pow(getY(),2)+Math.pow(getZ(),2));
  }
    /**
     * Returns identical vector
     * @return this vector.
     */
  public Vector clone(){
    return new Vector(getX(),getY(),getZ());
  }

    /**
     * toString method, gives x, y  and z coordinates as string.
     * @return x, y  and z coordinates as string.
     */
  public String toString(){
    return getX()+", "+getY()+", "+getZ();
  }

    /**
     * rounds double into ints.
     * @return int array of coordinates ([x,y,z])
     */
  public int[] toInt(){
    return new int[]{
      (int)Math.round(getX()), (int)Math.round(getY()), (int)Math.round(getZ())
    };
  }

  /**
   * Gets the unit vector (divides coordinates by magnitude
   * @return - scaled vector.
   */
  public Vector normalize(){
    return new Vector(x/magnitude(),y/magnitude(),z/magnitude());
  }

}
