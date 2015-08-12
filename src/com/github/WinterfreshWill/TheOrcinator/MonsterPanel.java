/**MonsterPanel.java
 * Panel that displays a monster's information
 */

package com.github.WinterfreshWill.TheOrcinator;

import javax.swing.JPanel;

public class MonsterPanel extends JPanel {
	private MonsterInfo monster;
	
	public MonsterPanel() {
		monster = new MonsterInfo();
		init();
	}
	public MonsterPanel(String mon) {
		monster = new MonsterInfo(mon);
		init();
	}
	
	private void init() {
		
	}
}
