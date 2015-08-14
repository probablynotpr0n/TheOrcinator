/**MonsterInfo.java
 * Acts as a container class for a monster's information
 * 
 * @author Will Young
 * @version 0.0
 */

package com.github.WinterfreshWill.TheOrcinator;

import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;

import org.w3c.dom.Document;
//import org.w3c.dom.Element;
import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.TreeWalker;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MonsterInfo {
	/** Default location for the program to look for monster files */
	private static final String defaultPath = "monsters/";
	/** All locations program will look for monster files */
	private static ArrayList<String> monsterPath = new ArrayList<String>();
	
	private File monsterFile;
	private Document monsterDoc;
	
	/* to be stored in a DOM Document
	private String monsterName;
	private int armorClass;
	private String armorType; // make this more robust later?
	private int hitPoints;
	private int tempHP;
	//*/
	
	static { // Adds the default directory to the monster path, and complains if it's missing
		File def = new File(defaultPath);
		if(!def.isDirectory() || !def.canRead()) {
			monsterPath.add(def.getPath());
		} else {
			// just leave monsterPath empty. popup notification?
			System.err.printf("[WARNING] Default path (%s) is missing or unreadable.%n", defaultPath);
		}
	}
	
	// Constructors
	/** Creates an empty instance of MonsterInfo with all fields set to their minimum value or null
	 *  
	 *  @throws FileNotFoundException If the empty.xml file can't be found or doesn't exist
	 *  @see findFile
	 */
	public MonsterInfo() throws FileNotFoundException {
		monsterFile = findFile("empty");
		monsterDoc = parseFile(monsterFile);
	}
	/** Creates an instance of MonsterInfo with existing fields set according to file.
	 * If a field was not present, it will be set to null or its numerical minimum value
	 * 
	 * @param mon The name of the monster to load excluding ".xml" suffix
	 * @throws FileNotFoundException If the &lt;name&gt;.xml could not be found or doesn't exist
	 * @see findFile
	 */
	public MonsterInfo(String mon) throws FileNotFoundException {
		monsterFile = findFile(mon);
		monsterDoc = parseFile(monsterFile);
	}
	
	/** Navigates the DOM until it finds the node we're looking for
	 *  
	 *  @param doc   The Document to search
	 *  @param nodes An array of strings representing in order what node path to follow (try anonymous arrays)
	 *  @return      The specified node at the end of nodes[] or null if it couldn't be found
	 */
	public static Node findNode(Document doc, String[] nodes) {
		TreeWalker tw = ((DocumentTraversal)doc).createTreeWalker(doc.getDocumentElement(),
				NodeFilter.SHOW_ELEMENT, null, true);
		Node current = tw.getCurrentNode();
		
		for(int i = 0; i < nodes.length; i++) {
			while(current != null) {
				if(tw.getCurrentNode().getNodeName().equals(nodes[i])) {
					break; // we found what we're looking for
				} else {
					current = tw.nextSibling(); // move on to the next node in same tier
				}
			}
			
			if(current == null) { // we didn't find the node we're looking for
								  // therefore it doesn't exist
				// generate error message and return null
				StringBuilder error = new StringBuilder("[WARNING] Node %s doesn't exist in ").
						append(nodes[0]);
				for(int j = 0; j < i; j++) {
					error.append("->").append(nodes[j]);
				}
				System.err.printf(error.append("%n").toString(), nodes[i]);
				return null;
			}
			
			// if we've gotten this far, it means that we've found the right node
			if(i == nodes.length - 1) {
				// we're on the final node! turn it in!
				return tw.getCurrentNode(); // not taking any chances with current
			}
		}
		// code should never execute this far, but we've gotta satisfy the compiler
		System.err.printf("[MAYDAY] I HAVE MADE A GRAVE MISTAKE%n");
		Thread.dumpStack();
		return null;
	}
	
	/** Attempts to create a new node to the DOM, creating all necessary nodes in between
	 * 
	 * @param doc   The Document to modify
	 * @param nodes An array of strings representing the new Node and all its parents starting with the root
	 * @return      The newly created Node
	 */
	public static Node insertNode(Document doc, String[] nodes) {
		TreeWalker tw = ((DocumentTraversal)doc).createTreeWalker(doc.getDocumentElement(),
				NodeFilter.SHOW_ELEMENT, null, true);
		Node current = tw.getCurrentNode();
		
		for(int i = 0; i < nodes.length; i++) {
			while(current != null) {
				if(tw.getCurrentNode().getNodeName().equals(nodes[i])) {
					if(i == nodes.length - 1) { // Node already exists
						return tw.getCurrentNode();
					} else { // we're on the right one, increment i and go to the next tier of nodes
						tw.firstChild();
						break;
					}
				} else { // we are not on the right node
					tw.nextSibling();
				}
			}
			
			if(i == 0) {
				// bad news, if we're here then a call to tw.parentNode() will return null and break things
				//TODO deal with this
			}
			
			// if we're here then current == null and we need to create a new Node
			tw.parentNode().appendChild(doc.createElement(nodes[i]));
			tw.firstChild(); // tw was on the parent node due to the call to parentNode()
			i--; // decrement i so that the next loop will search again on the same tier
		}
		
		// code should never execute this far, but we've gotta satisfy the compiler
			System.err.printf("[MAYDAY] I HAVE MADE A GRAVE MISTAKE%n");
			Thread.dumpStack();
			return null;
	}
	
	/** Attempts to parse given file into a DOM
	 * 
	 * @param file XML file to be parsed, probably a monster file
	 * @return The Document object after parsing
	 */
	public static Document parseFile(File file) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(file);
		} catch(Exception e) {
			System.err.printf("[ERROR] Specified file could not be parsed.%n");
			e.printStackTrace();
		}
		
		return null;
	}
	
	/** Searches monsterPath for an appropriate file for given monster
	 * 
	 * @param monsterName Name of monster, excluding ".xml" suffix
	 * @return A File object representing the appropriate file "&lt;monsterName&gt;.xml"
	 * @throws FileNotFoundException If an appropriate file could not be found or doesn't exist
	 */
	public static File findFile(String monsterName) throws FileNotFoundException {
		//convert string to preferred format
		String fileName = new StringBuilder(monsterName.toLowerCase()).append(".xml").toString();
		for(String s : monsterPath) {
			File f = new File(new StringBuilder(s).append(fileName).toString());
			if(f.canRead()) {
				return f;
			}	
		}
		// if it gets here then no file was found
		StringBuilder err = new StringBuilder(
				"The specified monster file cannot be read or does not exist. Current monsterPath contains: ");
		for(String s : monsterPath) {
			err.append(s).append(' ');
		}
		throw new FileNotFoundException(err.toString());
	}
	
	/** Gets the monster's name from the DOM
	 * 
	 * @return The value of the "name" node or null if it wasn't there
	 */
	public String getName() {
		Node n = findNode(monsterDoc, new String[]{"monster", "name"});
		if(n == null)
			return null;
		return n.getTextContent();
	}
	
	/** Gets the monster's AC value from the DOM
	 * 
	 * @return The value of the "armor"->"ac" node or Integer.MIN_VALUE if it wasn't there
	 */
	public int getAC() {
		Node n = findNode(monsterDoc, new String[]{"monster", "armor", "ac"});
		if(n == null)
			return Integer.MIN_VALUE;
		try {
			return Integer.parseInt(n.getTextContent());
		} catch(NumberFormatException e) {
			System.err.printf("[ERROR] Could not parse ac as an integer.%n");
			e.printStackTrace();
		}
		return Integer.MIN_VALUE;
	}
	
	/** Gets the monster's armor type from the DOM
	 * 
	 * @return The value of "armor"->"type" or null if it wasn't there
	 */
	public String getArmorType() {
		Node n = findNode(monsterDoc, new String[]{"monster", "armor", "type"});
		if(n == null)
			return null;
		return n.getTextContent();
	}
	
	/** Gets the monster's average maximum HP from the DOM
	 * 
	 * @return The value of "health"->"average" or Integer.MIN_VALUE if it wasn't there
	 */
	public int getHP() {
		Node n = findNode(monsterDoc, new String[]{"monster", "health", "average"});
		if(n == null)
			return Integer.MIN_VALUE;
		try {
			return Integer.parseInt(n.getTextContent());
		} catch(NumberFormatException e) {
			System.err.printf("[ERROR] Could not parse average as an integer");
			e.printStackTrace();
		}
		return Integer.MIN_VALUE;
	}
	
	public void setName(String newName) {
		Node n = findNode(monsterDoc, new String[]{"monster", "name"});
		if(n == null) {
			insertNode(monsterDoc, new String[]{"monster", "name"}).setTextContent(newName);
		} else {
			n.setTextContent(newName);
		}
	}
}
