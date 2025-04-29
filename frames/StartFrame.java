package frames;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;

import components.buttons.StartButton;
import components.labels.AnimatedUnoLogo;

public class StartFrame extends JFrame {
    private Image backgroundImage;
    private AnimatedUnoLogo unoLogo;
    private StartButton startButton;
    
    private static final int LOGO_WIDTH = 400; 
    private static final int LOGO_HEIGHT = 200;
    private static final int BUTTON_WIDTH = 450;
    private static final int BUTTON_HEIGHT = 80;
    
    
    public StartFrame() {
        setTitle("UNO Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); 
        setResizable(true);
        
        try {
            URL imageUrl = getClass().getResource("/frames/uno_background.jpg");
            if (imageUrl == null) {
                imageUrl = getClass().getResource("uno_background.jpg");
            }
            if (imageUrl == null) {
                imageUrl = getClass().getResource("/uno_background.jpg");
            }
            
            if (imageUrl != null) {
                backgroundImage = new ImageIcon(imageUrl).getImage();
                System.out.println("Successfully loaded background image from: " + imageUrl);
            } else {
                System.err.println("Could not find background image. Make sure uno_background.jpg exists in the correct location.");
            }
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
            e.printStackTrace();
        }
        
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    Graphics2D g2d = (Graphics2D) g;
                    GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(23, 107, 135), 
                        getWidth(), getHeight(), new Color(0, 45, 60)
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        
        backgroundPanel.setLayout(null);
        
        unoLogo = new AnimatedUnoLogo();
        backgroundPanel.add(unoLogo);
        
        unoLogo.startAnimation();
        
        startButton = new StartButton("Let's Get Started", new StartButton.StartButtonListener() {
            @Override
            public void onButtonClicked() {
                System.out.println("Start button clicked!");
            }
        });
        backgroundPanel.add(startButton);
        
        setContentPane(backgroundPanel);
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repositionComponents();
            }
        });
        
        repositionComponents();
    }
   
    private void repositionComponents() {
        int frameWidth = getContentPane().getWidth();
        int frameHeight = getContentPane().getHeight();
        
        int totalHeight = LOGO_HEIGHT + 5 + BUTTON_HEIGHT; 
        int startY = (frameHeight - totalHeight) - 300;
        
        int logoX = (frameWidth - LOGO_WIDTH) / 2;
        unoLogo.setBounds(logoX, startY, LOGO_WIDTH, LOGO_HEIGHT);
        
        int buttonX = (frameWidth - BUTTON_WIDTH) / 3;
        int buttonY = startY + LOGO_HEIGHT ; 
        startButton.setBounds(buttonX, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT);
    }
    
    public static void main(String[] args) {
        // Enable anti-aliasing
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
        // Start the application on the EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StartFrame frame = new StartFrame();
                frame.setVisible(true);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
    }
}