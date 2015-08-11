/**OrcinatorMain.java
 * Main window that displays options, opens
 * other windows
 */

package com.github.WinterfreshWill.TheOrcinator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
//import javax.swing.ImageIcon;

public class OrcinatorMain extends JFrame {
	private static final long serialVersionUID = 1L;
	public Settings global;
	public OrcinatorMain() {
		global = new Settings();
		initGUI();
	}
	private void initGUI() {
		createMenuBar();
		setTitle(new StringBuilder("The Orcinator v").append(global.sVersion).toString());
		setSize(global.iWindowSizeX, global.iWindowSizeY);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	private void createMenuBar() {
		JMenuBar menu = new JMenuBar();
		
		// File
		JMenu menuFile = new JMenu("File");
		// File->Save
		JMenuItem menuFileSave = new JMenuItem("Save");
		menuFileSave.setMnemonic(KeyEvent.VK_S);
		menuFileSave.setToolTipText("Save current state");
		menuFileSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//TODO display save dialog
			}
		});
		// File->Load
		JMenuItem menuFileLoad = new JMenuItem("Load");
		menuFileLoad.setToolTipText("Load a state from file");
		menuFileSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//TODO display load dialog
			}
		});
		// File->Exit
		JMenuItem menuFileExit = new JMenuItem("Exit");
		menuFileExit.setMnemonic(KeyEvent.VK_E);
		menuFileExit.setToolTipText("Quit program");
		menuFileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		
		// Edit
		JMenu menuEdit = new JMenu("Edit");
		// Edit->Settings
		JMenuItem menuEditSettings = new JMenuItem("Settings");
		menuEditSettings.setToolTipText("Change program settings");
		menuEditSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//TODO display settings dialog
			}
		});
		
		menuFile.add(menuFileSave);
		menuFile.add(menuFileLoad);
		menuFile.add(menuFileExit);
		menuEdit.add(menuEditSettings);
		menu.add(menuFile);
		menu.add(menuEdit);
		
		setJMenuBar(menu);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new OrcinatorMain().setVisible(true);
			}
		});
	}

}
