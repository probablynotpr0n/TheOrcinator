/**MonsterInfo.java
 * Acts as a container class for a monster's information
 */

package com.github.WinterfreshWill.TheOrcinator;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class MonsterInfo {
	private static final String defaultPath = "monsters/";
	private static ArrayList<File> monsterPath = new ArrayList<File>();
	private static boolean isInitialized = false;
	
	private String monsterName;
	private int armorClass;
	private String armorType; // make this more robust later?
	private int hitPoints;
	private int tempHP;
	
	public MonsterInfo() {
		init();
		//TODO make an empty monster
	}
	public MonsterInfo(String mon) {
		init();
		//TODO initialize all fields to matching value of mon
	}
	
	// Adds the default directory to the monster path, and complains if it's missing
	private void init() {
		if(!isInitialized) { //only want to do this once
			File def = new File(defaultPath);
			if(!def.isDirectory() || !def.canRead()) {
				monsterPath.add(def);
			} else {
				// just leave blank. popup notification?
				//TODO add popup telling that no monster directory found, no monsters will be available
			}
			
			isInitialized = true;
		}
	}
	
	// finds an appropriate file for given monster, throws file not found exception
	// if one cannot be found
	public void findFile(String monsterName) throws FileNotFoundException {
		//convert string to preferred format
		String filename = new StringBuilder(monsterName.toLowerCase()).append(".minfo").toString();
		//TODO search minfo path
		//TODO add minfo path, including inside jar (if possible) as first location
	}
	public void parseFile(String filename) {
		//TODO parse a file containing monster info, xml format
	}
	
	// Access functions
	public String getName() {
		return monsterName;
	}
	public int getAC() {
		return armorClass;
	}
	public String getArmorType() {
		return armorType;
	}
	public int getHP() {
		return hitPoints;
	}
	
	public void setName(String newName) {
		monsterName = newName;
	}
}
