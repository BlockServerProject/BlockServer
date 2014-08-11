package net.blockserver.math;

public class Vector3d
{
    private double x, y, z;

    public Vector3d(double v)
    {
        this.x = v;
        this.y = v;
        this.z = v;
    }

    public Vector3d(double x, double y)
    {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public Vector3d(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    public double getZ()
    {
        return this.z;
    }


    public void setX(double v)
    {
        this.x = v;
    }

    public void setY(double v)
    {
        this.y = v;
    }

    public void setZ(double v)
    {
        this.z = v;
    }
}
