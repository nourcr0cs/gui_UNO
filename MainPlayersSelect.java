/******************** Supposed to be Players Selecting Panel in Main Frame ***********************/
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import View.components.panels.PlayersSelectPanel;

public class MainPlayersSelect {
	 public static void main(String[] args) {
	    	
	        SwingUtilities.invokeLater(() -> {
	            JFrame frame = new JFrame("UNO - Choose Players");   //IMENE
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setSize(1000, 800);
	            frame.setLocationRelativeTo(null);
	            frame.setResizable(true);
	            frame.setContentPane(new PlayersSelectPanel());
	            frame.setVisible(true);
	        });
	 }
}