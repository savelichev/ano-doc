package net.anotheria.asg.data;

public abstract class AbstractVO implements DataObject{

	public long getLastUpdateTimestamp() {
		return getDaoUpdated() == 0 ? 
				getDaoCreated() : getDaoUpdated();
	}
	
	public abstract long  getDaoCreated();
	public abstract long  getDaoUpdated();
	public abstract Object clone() throws CloneNotSupportedException;
}
