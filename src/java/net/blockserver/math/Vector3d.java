package net.blockserver.math;

public class Vector3d
{
    protected double x, y, z;

    public static Vector3d fromYawPitch(double yaw, double pitch){
        return fromYawPitch(yaw, pitch, 1);
    }

    public static Vector3d fromYawPitch(double yaw, double pitch, double speed){
        yaw = (yaw + 90) * Math.PI / 180;
        pitch = pitch * Math.PI / 180;
        double y = -Math.sin(pitch) * speed;
        double horizDelta = Math.abs(Math.cos(pitch));
        double x = -horizDelta * Math.sin(yaw);
        double z = horizDelta * Math.cos(yaw);
        return new Vector3d(x, y, z);
    }

    public Vector3d(){
        this(0, 0, 0);
    }

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

    public Vector3d add(Vector3d delta)
    {
        return merge(this, delta);
    }

    public Vector3d add(double dx, double dy, double dz)
    {
        return new Vector3d(x + dx, y + dy, z + dz);
    }

    public Vector3d subtract(Vector3d delta)
    {
        return add(delta.multiply(-1));
    }

    public Vector3d subtract(double dx, double dy, double dz)
    {
        return add(-dx, -dy, -dz);
    }

    public Vector3d multiply(double k)
    {
        return new Vector3d(x * k, y * k, z * k);
    }

    public Vector3d divide(double k)
    {
        return multiply(1 / k);
    }

    public Vector3f toFloat()
    {
        return new Vector3f((float) x, (float) y, (float) z);
    }

    public Vector3 floor()
    {
        return new Vector3((int) x, (int) y, (int) z);
    }

    public static Vector3d merge(Vector3d... vectors)
    {
        Vector3d base = new Vector3d();
        for(Vector3d vector: vectors)
        {
            base = new Vector3d(base.getX() + vector.getX(), base.getY() + vector.getY(), base.getZ() + vector.getZ());
        }
        return base;
    }

}
