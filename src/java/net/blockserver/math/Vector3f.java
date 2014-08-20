package net.blockserver.math;

public class Vector3f
{
    private float x, y, z;

    public Vector3f(){
        this(0, 0, 0);
    }

    public Vector3f(float v)
    {
        this.x = v;
        this.y = v;
        this.z = v;
    }

    public Vector3f(float x, float y)
    {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public Vector3f(float x, float y, float z)
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

    public Vector3f add(Vector3f delta)
    {
        return merge(this, delta);
    }

    public Vector3d add(float dx, float dy, float dz)
    {
        return new Vector3d(x + dx, y + dy, z + dz);
    }

    public Vector3f subtract(Vector3f delta)
    {
        return add(delta.multiply(-1));
    }

    public Vector3d subtract(float dx, float dy, float dz)
    {
        return add(-dx, -dy, -dz);
    }

    public Vector3f multiply(float k)
    {
        return new Vector3f(x * k, y * k, z * k);
    }

    public Vector3f divide(float k)
    {
        return multiply(1 / k);
    }

    public Vector3d toDouble()
    {
        return new Vector3d(x, y, z);
    }

    public Vector3 floor()
    {
        return new Vector3((int) x, (int) y, (int) z);
    }

    public static Vector3f merge(Vector3f... vectors)
    {
        Vector3f base = new Vector3f();
        for(Vector3f vector: vectors)
        {
            base = new Vector3f(base.getX() + vector.getX(), base.getY() + vector.getY(), base.getZ() + vector.getZ());
        }
        return base;
    }

}
