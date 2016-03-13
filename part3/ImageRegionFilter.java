package part3;

/**
 * An abstract class that ensures all classes representing an image filter
 * implement the filter method as specified in ImageFilter.
 * Also provides data and methods for a region selected from an image to 
 * be filtered.
 * 
 * @author Erin Parker and Christina Foreman
 */
public abstract class ImageRegionFilter implements ImageFilter {

	private Region2d r;
	
	public ImageRegionFilter() {
		r = new Region2d();
	}
	
	public void setRegion(Region2d _r) {
		r = _r;
	}
			
	public void setRegion(int minX, int maxX, int minY, int maxY) {
		r.setPoints(minX, maxX, minY, maxY);
	}
	
	public int getMinX() {
		return r.getMinX();
	}
	
	public int getMaxX() {
		return r.getMaxX();
	}
	
	public int getMinY() {
		return r.getMinY();
	}
	
	public int getMaxY() {
		return r.getMaxY();
	}
	
	/**
	 * Returns a boolean indicating whether there is a region selected or not.
	 * A specific filter can use this and decide how to handle a selected region.
	 */
	public boolean selectedTrue() {
		return r.selectedTrue();
	}
}
