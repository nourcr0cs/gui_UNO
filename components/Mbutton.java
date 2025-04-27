

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Mbutton extends JButton{
    
private JButton boutonInterne;

     // counstructeur plus beau 
     public Mbutton(String text, Color normalColor, Color hoverColor, Color textColor, Font font) {
        setLayout(new BorderLayout());
        setOpaque(false); // pas de fond pour le JPanel, c'est le bouton qui sera visible

        boutonInterne = new JButton(text);
        boutonInterne.setFocusPainted(false);
        boutonInterne.setForeground(textColor);
        boutonInterne.setFont(font);
        boutonInterne.setBackground(normalColor);
        boutonInterne.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        boutonInterne.setContentAreaFilled(true);

        // Bordures arrondies + enlever fond carré
        boutonInterne.setBorderPainted(false);
        boutonInterne.setOpaque(true);
        boutonInterne.setCursor(new Cursor(Cursor.HAND_CURSOR));





        boutonInterne.setOpaque(false);          // Pas d'opacité du fond
        boutonInterne.setContentAreaFilled(false); // Pas de fond
        boutonInterne.setBorderPainted(false);   // Pas de bordure





        // Ajout effet hover (survol)
        boutonInterne.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boutonInterne.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boutonInterne.setBackground(normalColor);
            }
        });

        // Ajoute le bouton dans le JPanel
        add(boutonInterne, BorderLayout.CENTER);
    }




    // constructeur ta3 boutoum standard 
    public Mbutton(String text) {
        this(text, new Color(33, 150, 243), new Color(30, 136, 229), Color.WHITE, new Font("Arial", Font.BOLD, 16));
    }







  
}
