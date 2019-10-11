import java.util.*;
public class Vector{
  double x;
  double y;
  double z;
  public static Vector origin = new Vector(0,0,0);
  public static Vector xHat = new Vector(1,0,0);
  public static Vector yHat = new Vector(0,1,0);
  public static Vector zHat = new Vector(0,0,1);

  public Vector(double x,double y,double z){
    this.x=x;
    this.y=y;
    this.z=z;
  }
  public Vector(Vector p){
    this.x=p.getX();
    this.y=p.getY();
    this.z=p.getZ();
  }
  public void rotateX(double angle, Vector center){//rotate a point around a center by angle degrees on its local x Axis
    Vector temp = this.returnRotateX(angle,center);
    x=temp.getX();
    y=temp.getY();
    z=temp.getZ();
  }
  public void rotateY(double angle, Vector center){//rotate a point around a center by angle degrees on its local y Axis
    Vector temp = this.returnRotateY(angle,center);
    x=temp.getX();
    y=temp.getY();
    z=temp.getZ();
  }
  public void rotateZ(double angle, Vector center){//rotate a point around a center by angle degrees on its local z Axis
    Vector temp = this.returnRotateZ(angle,center);
    x=temp.getX();
    y=temp.getY();
    z=temp.getZ();
  }
  public double angleBetween(Vector v2){ //return the angle between vectors in degrees
    return Math.acos(this.dot(v2)/this.magnitude()/v2.magnitude())*180/Math.PI;
  }
  public Vector returnRotateX(double angle, Vector center){//returns a Vector insteado of changing the current
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
  public Vector returnRotateY(double angle, Vector center){//returns a Vector insteado of changing the current
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
  public Vector returnRotateZ(double angle, Vector center){//returns a Vector insteado of changing the current
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
  public Vector returnRotateOnVector(double angle, Vector center, Vector vector){//rotates by angle degrees on arb. axis. returns a Vector insteado of changing the current
    Vector v = vector.clone();
    if(!(v.getY()==1 && v.magnitude()==1)){
      v = vector.setY(0);
    }
    Vector tempV = this.clone();
    double toY = vector.angleBetween(Vector.yHat);
    double toZ = v.angleBetween(Vector.zHat);
    tempV.rotateY(-toZ,center);
    tempV.rotateX(-toY,center);
    tempV.rotateY(angle,center);
    tempV.rotateX(toY,center);
    tempV.rotateY(toZ,center);
    return tempV;
  }
  public Vector scale(double s){ //scales the point
    return new Vector(getX()*s,getY()*s,getZ()*s);
  }
  public Vector add(Vector p){ //adds vector
    return new Vector(x+p.getX(),y+p.getY(),z+p.getZ());
  }
  public Vector subtract(Vector p){ //subtracts vector
    return new Vector(x-p.getX(),y-p.getY(),z-p.getZ());
  }
  public double dot(Vector p){//dots a vector
    return x*p.getX()+y*p.getY()+z*p.getZ();
  }
  public Vector cross(Vector p){//crosses a vector
    return new Vector(getY()*p.getZ()-getZ()*p.getY(), getZ()*p.getX()-getX()*p.getZ(), getX()*p.getY()-getY()*p.getX());
  }

  public double getX(){
    return this.x;
  }
  public double getY(){
    return this.y;
  }
  public double getZ(){
    return this.z;
  }
  public Vector setX(double x){
    return new Vector(x,this.y,this.z);
  }
  public Vector setY(double y){
    return new Vector(this.x,y,this.z);
  }
  public Vector setZ(double z){
    return new Vector(this.x,this.y,z);
  }
  public double magnitude(){ //gets magnitude of this
    return Math.sqrt(Math.pow(getX(),2)+Math.pow(getY(),2)+Math.pow(getZ(),2));
  }
  public Vector getUnit(){
    return new Vector(getX()/magnitude(),getY()/magnitude(),getZ()/magnitude());
  }

  public Vector transformPerspective(double fov){
    Vector p = this.clone();
    p.x=getX()/-getZ()/Math.tan(fov/2);
    p.y=getY()/-getZ()/Math.tan(fov/2);
    return p;
  }

  public Vector clone(){
    return new Vector(getX(),getY(),getZ());
  }

  public String toString(){
    return getX()+", "+getY()+", "+getZ();
  }
  public int[] toInt(){
    return new int[]{
      (int)Math.round(getX()), (int)Math.round(getY()), (int)Math.round(getZ())
    };
  }
  public void toIntArr(int[] xs, int[] ys, int index){
    xs[index] = this.toInt()[0];
    ys[index] = this.toInt()[1];
  }
}
