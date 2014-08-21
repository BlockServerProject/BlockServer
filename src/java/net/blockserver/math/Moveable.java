package net.blockserver.math;

import java.util.HashMap;
import java.util.Map;

public abstract class Moveable extends Vector3d{
    public final static String MODIFIER_STANDARD = "net.blockserver.math.Moveable.standard";
    protected Map<String, Vector3d> speed;
    protected Moveable(Vector3d vectors){
        this(vectors.getX(), vectors.getY(), vectors.getZ());
    }
    protected Moveable(Vector3d vectors, Vector3d speed){
        this(vectors.getX(), vectors.getY(), vectors.getZ(), speed);
    }
    protected Moveable(double x, double y, double z, Vector3d speed){
        super(x, y, z);
        initSpeedMap();
    }
    protected Moveable(double x, double y, double z){
        this(x, y, z, new Vector3d(0, 0, 0));
        initSpeedMap();
    }
    protected void initSpeedMap(){
        speed = new HashMap<String, Vector3d>(0xFF);
        setSpeed(getInitialStdSpeed());
    }
    protected Vector3d getInitialStdSpeed(){
        return new Vector3d(0, 0, 0);
    }
    public void setSpeed(Vector3d speed){
        setSpeed(speed, MODIFIER_STANDARD);
    }
    public void setSpeed(Vector3d speed, String name){
        this.speed.put(name, speed);
    }
    public Vector3d getSpeed(String name){
        if(speed.containsKey(name)){
            return speed.get(name);
        }
        return new Vector3d(); // return null;
    }
    public Vector3d getSpeed(){
        Vector3d base = new Vector3d();
        for(Map.Entry<String, Vector3d> entry: speed.entrySet()){
            base = Vector3d.merge(base, entry.getValue());
        }
        return base;
    }
    public void onTickUpdate(){
        instanceAdd(getSpeed());
    }
    public void instanceAdd(Vector3d delta){
        x += delta.getX();
        y += delta.getY();
        z += delta.getZ();
    }
}
