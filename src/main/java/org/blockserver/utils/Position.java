package org.blockserver.utils;


/**
 * Created by jython234 on 1/13/2015.
 */
public class Position {
    private double x;
    private double y;
    private double z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getZ(){
        return z;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public void setZ(double z){
        this.z = z;
    }
}
