package part3;

/**
 * Represents a two-dimensional region (i.e., a rectangle).
 * 
 * @author Erin Parker
 */
public class Region2d {
	
	private int minX;	
	private int maxX;	
	private int minY;
	private int maxY;
	
	public Region2d(int _minX, int _maxX, int _minY, int _maxY) {
		minX = _minX;
		maxX = _maxX;
		minY = _minY;
		maxY = _maxY;
	}
	
	public Region2d() {
		minX = 0;
		maxX = 0;
		minY = 0;
		maxY = 0;
	}
	
	public int getMinX() {
		return minX;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}
	
	public void setPoints(int _minX, int _maxX, int _minY, int _maxY) {
		minX = _minX;
		maxX = _maxX;
		minY = _minY;
		maxY = _maxY;
	}

	// allows filters to see if there is a region selected or not
	public boolean selectedTrue() {
		if ((getMaxY() - getMinY() > 0) || (getMaxX() - getMinX() > 0))
			return true;
		return false;
	}
}
