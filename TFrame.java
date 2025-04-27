import javax.swing.*;
import java.awt.*;

public class TFrame extends JFrame {
    private JPanel mainPanel;

    public TFrame(String title) {
        setTitle(title);
        setUndecorated(true);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 225, 240));
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(160, 90, 220), 4));

        // Add the title bar
        TTitleBar titleBar = new TTitleBar(this);
        // Pass the title to the title bar
        titleBar.setTitle(title);

        mainPanel.add(titleBar, BorderLayout.NORTH);

        // Add the main panel to the frame
        add(mainPanel);
    }

    // Method to add content to the center of the main panel
    public void setContentPanel(JPanel panel) {
        mainPanel.add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
