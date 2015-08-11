/**Settings.java
 * Holds global program settings, defaults
 */

package com.github.WinterfreshWill.TheOrcinator;

public class Settings {
	public static final String sVersion_d = "0.0";
	public static final int iWindowSizeX_d = 300;
	public static final int iWindowSizeY_d = 200;
	
	public String sVersion;
	public int iWindowSizeX;
	public int iWindowSizeY;
	
	public Settings() {
		setDefaults();
	}
	public Settings(String file) {
		//TODO initialize all values to those specified in file
	}
	
	public void save(String file) {
		//TODO save current settings to file (in jar)
	}
	public void load(String file) {
		//TODO load settings from file (in jar)
	}
	public void setDefaults() {
		sVersion = sVersion_d;
		iWindowSizeX = iWindowSizeX_d;
		iWindowSizeY = iWindowSizeY_d;
	}
}
