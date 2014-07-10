package net.blockserver.math;

public class Vector3
{
    private int x, y, z;

    public Vector3(int v)
    {
        this.x = v;
        this.y = v;
        this.z = v;
    }

    public Vector3(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public Vector3(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public  int getZ()
    {
        return this.z;
    }


    public void setX(int v)
    {
        this.x = v;
    }

    public void setY(int v)
    {
        this.y = v;
    }

    public void setZ(int v)
    {
        this.z = v;
    }
}
