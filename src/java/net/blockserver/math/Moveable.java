package net.blockserver.math;

public abstract class Moveable extends Vector3d{
    protected Vector3f speed;
    protected Moveable(Vector3d vectors){
        this(vectors.getX(), vectors.getY(), vectors.getZ());
    }
    protected Moveable(Vector3d vectors, Vector3f speed){
        this(vectors.getX(), vectors.getY(), vectors.getZ(), speed);
    }
    protected Moveable(double x, double y, double z, Vector3f speed){
        super(x, y, z);
        setSpeed(speed);
    }
    protected Moveable(double x, double y, double z){
        this(x, y, z, new Vector3f(0, 0, 0));
    }
    protected void setSpeed(Vector3f speed){
        this.speed = speed;
    }
    public void onTickUpdate(){
        
    }
    public void add(Vector3f delta){
        x += delta.getX();
        y += delta.getY();
        z += delta.getZ();
    }
}
