package frames;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import components.AnimatedUnoLabel;
import components.buttons.StartButton;

public class StartFrame extends JFrame {
    private Image backgroundImage;
    
    public StartFrame() {
        // Set up the frame
        setTitle("UNO Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the window
        setResizable(true); // Prevent resizing to maintain design integrity
        
        // Load the background image
        try {
            // You'll need to replace this with the path to your image
            URL imageUrl = getClass().getResource("uno_background.jpg");
            if (imageUrl != null) {
                backgroundImage = new ImageIcon(imageUrl).getImage();
            } else {
                System.err.println("Could not find background image");
            }
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Create a custom panel with the background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    // Draw the background image to fill the entire panel
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Fallback to a teal color similar to the image if image loading fails
                    g.setColor(new Color(23, 107, 115));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        
        backgroundPanel.setLayout(null);
        
        // Create animated UNO logo label
        AnimatedUnoLabel unoTitleLabel = new AnimatedUnoLabel();
        unoTitleLabel.setBounds(250, 80, 300, 120);
        // Start the animation
        unoTitleLabel.startAnimation();
        backgroundPanel.add(unoTitleLabel);
        
        // Create our new StartButton component
        StartButton startButton = new StartButton("let's get started", new StartButton.StartButtonListener() {
            @Override
            public void onButtonClicked() {
            }
        });
        
        // Position the button to match the image
        startButton.setBounds(175, 220, 450, 80);
        backgroundPanel.add(startButton);
        
        // Set the background panel as the content pane
        setContentPane(backgroundPanel);
    }
    
    /**
     * Creates a styled JLabel with optional shadow effect
     */
    private JLabel createStyledLabel(String text, Font font, Color foreground, Color shadowColor, int shadowOffset) {
        JLabel label = new JLabel(text, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                // Draw shadow if needed
                if (shadowColor != null && shadowOffset > 0) {
                    g2d.setFont(getFont());
                    g2d.setColor(shadowColor);
                    g2d.drawString(getText(), shadowOffset, getFont().getSize() + shadowOffset);
                }
                
                // Draw the main text
                g2d.setColor(getForeground());
                g2d.setFont(getFont());
                g2d.drawString(getText(), 0, getFont().getSize());
                
                g2d.dispose();
            }
        };
        
        label.setFont(font);
        label.setForeground(foreground);
        
        return label;
    }
    
   
    
    
    
    public static void main(String[] args) {
        // Enable anti-aliasing for better text rendering
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StartFrame frame = new StartFrame();
                frame.setVisible(true);
            }
        });
    }
}