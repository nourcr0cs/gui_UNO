package view.frames;

import javax.swing.*;

import view.components.panels.TPanel;

import java.awt.*;

public class TFrame extends JFrame {
    public TFrame(String title) {
        setTitle(title);
        setUndecorated(true);
        setSize(800, 600);
        setLocationRelativeTo(null); 

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // setTitle(title);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 225, 240));
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(160, 90, 220), 4));

        mainPanel.add(new TPanel(this), BorderLayout.NORTH);

        add(mainPanel);
        setVisible(true);
    }

    public void setBackgroundImage(String s) {

    }

    public static void main(String[] args) {
        TFrame see = new TFrame(" ~UNO~ ");
        // JFrame objFrame = new JFrame();
        TPanel tee = new TPanel(null);
        //tee.setBackgroundImage("icon.jpg");
        see.add(tee);
        // sse.setVisible(true);
        /*
         * String inputTitle = JOptionPane.showInputDialog("Enter your window title:");
         * String finalTitle;
         * 
         * if (inputTitle == null || inputTitle.isEmpty()) {
         * finalTitle = "My Window";
         * } else {
         * finalTitle = inputTitle;
         * }
         * 
         * SwingUtilities.invokeLater(() -> new TFrame(finalTitle));
         */
    }

}
