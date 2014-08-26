package org.blockserver.math;

public interface IVector3<U>{
	public U getX();
	public U getY();
	public U getZ();
	public void setX(U x);
	public void setY(U y);
	public void setZ(U z);
	public IVector3<U> add(IVector3<U> v);
	public IVector3<U> add(U x, U y, U z);
	public IVector3<U> subtract(IVector3<U> v);
	public IVector3<U> subtract(U x, U y, U z);
	public IVector3<U> multiply(U k);
	public IVector3<U> divide(U k);
}
// I wanted to let other vector classes extend it, but I have to convert Double to double before casting to float/int, so I aborted it.
