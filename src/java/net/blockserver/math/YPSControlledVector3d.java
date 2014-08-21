package net.blockserver.math;

public class YPSControlledVector3d extends Vector3d {

    protected double yaw, pitch, speed;

    public YPSControlledVector3d(double yaw, double pitch){
        this(yaw, pitch, 1);
    }

    public YPSControlledVector3d(double yaw, double pitch, double speed){
        super(0, 0, 0);
        this.yaw = yaw;
        this.pitch = pitch;
        this.speed = speed;
        setYawPitchOnSubject(yaw, pitch, speed, this);
    }

    public void update(){ // TODO call this method from setters
        setYawPitchOnSubject(yaw, pitch, speed, this);
    }

}
