package net.blockserver.math;

public class Vector3
{
    private float x, y, z;

    public Vector3(float v)
    {
        this.x = v;
        this.y = v;
        this.z = v;
    }

    public Vector3(float x, float y)
    {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public Vector3(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public float getX()
    {
        return this.x;
    }

    public float getY()
    {
        return this.y;
    }

    public float getZ()
    {
        return this.z;
    }


    public void setX(float v)
    {
        this.x = v;
    }

    public void setY(float v)
    {
        this.y = v;
    }

    public void setZ(float v)
    {
        this.z = v;
    }
}
