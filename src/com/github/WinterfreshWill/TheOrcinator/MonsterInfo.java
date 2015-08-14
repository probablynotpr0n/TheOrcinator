/**MonsterInfo.java
 * Acts as a container class for a monster's information
 */

package com.github.WinterfreshWill.TheOrcinator;

import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.TreeWalker;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MonsterInfo{
	private static final String defaultPath = "monsters/";
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
	public MonsterInfo() throws FileNotFoundException {
		monsterFile = findFile("empty");
		parseFile(monsterFile);
	}
	public MonsterInfo(String mon) throws FileNotFoundException {
		monsterFile = findFile(mon);
		parseFile(monsterFile);
	}
	
	// Navigate the DOM until specified nodes in order are found
	//TODO double check this code when it's not after midnight
	public static Node findNode(Document doc, String[] nodes) {
		TreeWalker tw = ((DocumentTraversal)doc).createTreeWalker(doc.getDocumentElement(),
				NodeFilter.SHOW_ELEMENT, null, true);
		Node current = tw.firstChild(); // Enters the document node, in this case <monster>
		
		for(int i = 0; i <= nodes.length; i++) {
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
			if(i == nodes.length) {
				// we're on the final node! turn it in!
				return tw.getCurrentNode(); // not taking any chances with current
			}
		}
		// code should never execute this far, but we've gotta satisfy the compiler
		System.err.printf("[MAYDAY] I HAVE MADE A GRAVE MISTAKE%n");
		Thread.dumpStack();
		return null;
	}
	
	// searches path for an appropriate file for given monster, throws file not found exception
	// if one cannot be found
	public File findFile(String monsterName) throws FileNotFoundException {
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
	public void parseFile(File filename) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			monsterDoc = builder.parse(filename);
		} catch(Exception e) {
			System.err.printf("[ERROR] Specified file could not be parsed.%n");
			e.printStackTrace();
		}
	}
	
	// Access functions
	public String getName() {
		Node n = findNode(monsterDoc, new String[]{"name"});
		if(n == null)
			return null;
		return n.getNodeValue();
	}
	public int getAC() {
		//TODO all these functions
		return 0;
	}
	public String getArmorType() {
		//TODO do it
		return null;
	}
	public int getHP() {
		//TODO don't letyour dreams be dreams
		return 0;
	}
	
	public void setName(String newName) {
		//TODO make this happen
	}
}
