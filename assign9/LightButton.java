package assign9;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * A button which can be turned on or off.
 * @author Christina Foreman
 */
public class LightButton extends JButton{
	private int row;
	private int column;
	private boolean lightOn;
	private ImageIcon lightOnIcon;
	private ImageIcon lightOffIcon;

	/**
	 * LightButton constructor. Button is randomly set to on or off.
	 */
	public LightButton(int row, int column) {
		this.row = row;
		this.column = column;
		setFont(new Font("Dialog", Font.BOLD, 24));
		
		lightOnIcon = new ImageIcon("src/assign9/images/lightOn.gif");
		lightOnIcon = resizeImage(lightOnIcon);
		
		lightOffIcon = new ImageIcon("src/assign9/images/lightOff.gif");
		lightOffIcon = resizeImage(lightOffIcon);
		
		setOpaque(true);
		randomize();
	}

	/**
	 * Resizes an icon.
	 * @return 
	 */
	public ImageIcon resizeImage(ImageIcon icon_) {
		Image img = icon_.getImage();
		Image newImg = img.getScaledInstance(75,  95,  java.awt.Image.SCALE_SMOOTH);
		icon_ = new ImageIcon(newImg);
		return icon_;
	}

	/**
	 * Toggles on or off. 
	 * If light is off, turn it on. If light is on, turn it off. 
	 */
	public void toggle() {
		if (isLightOn() == false) {
			turnOn();
		}
		else {
			turnOff();
		}
	}
	
	/**
	 * Turns a light on. Sets button's text to ON and the background to yellow.
	 * Method setLightOn is set to true.
	 */
	public void turnOn() {
//		setText("ON");
		setLightOn(true);
		setBackground(Color.YELLOW);
		setIcon(lightOnIcon);
	}
	
	/**
	 * Turns a light off. Sets button's text to OFF and the background to gray.
	 * Method setLightOn is set to false.
	 */
	public void turnOff() {
//		setText("OFF");
		setLightOn(false);
		setBackground(Color.GRAY);
		setIcon(lightOffIcon);
	}
	
	/**
	 * Returns button's row.
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Returns button's column.
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Sets up a random number and calls turnOn if the number is greater than .5; calls turnOff if the number is less than or equal to .5
	 */
	public void randomize() {
		double randomNumber = Math.random();
		if (randomNumber > 0.5)
			turnOn();
		else
			turnOff();
	}

	/**
	 * Returns true if the light is on.
	 */
	public boolean isLightOn() {
		return lightOn;
	}
	
	/**
	 * Sets lightOn.
	 */
	public void setLightOn(boolean lightOn) {
		this.lightOn = lightOn;
	}
}
//about 37 lines?