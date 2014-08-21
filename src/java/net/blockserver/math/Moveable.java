package net.blockserver.math;

import java.util.HashMap;
import java.util.Map;

public abstract class Moveable extends Vector3d{
    public final static String MODIFIER_STANDARD = "net.blockserver.math.Moveable.standard";
    protected Map<String, Vector3f> speed;
    protected Moveable(Vector3d vectors){
        this(vectors.getX(), vectors.getY(), vectors.getZ());
    }
    protected Moveable(Vector3d vectors, Vector3f speed){
        this(vectors.getX(), vectors.getY(), vectors.getZ(), speed);
    }
    protected Moveable(double x, double y, double z, Vector3f speed){
        super(x, y, z);
        initSpeedMap();
    }
    protected Moveable(double x, double y, double z){
        this(x, y, z, new Vector3f(0, 0, 0));
        initSpeedMap();
    }
    protected void initSpeedMap(){
        speed = new HashMap<String, Vector3f>(0xFF);
    }
    public void setSpeed(Vector3f speed){
        setSpeed(speed, MODIFIER_STANDARD);
    }
    public void setSpeed(Vector3f speed, String name){
        this.speed.put(name, speed);
    }
    public Vector3f getSpeed(String name){
        if(speed.containsKey(name)){
            return speed.get(name);
        }
        return new Vector3f(); // return null;
    }
    public Vector3f getSpeed(){
        Vector3f base = new Vector3f();
        for(Map.Entry<String, Vector3f> entry : speed.entrySet()){
            base = Vector3f.merge(base, entry.getValue());
        }
        return base;
    }
    public void onTickUpdate(){
        instanceAdd(getSpeed());
    }
    public void instanceAdd(Vector3f delta){
        x += delta.getX();
        y += delta.getY();
        z += delta.getZ();
    }
}
