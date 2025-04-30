package view.frames;
import javax.swing.*;

import view.components.buttons.StartButton;
import view.components.buttons.ButtonCursor;
import view.components.labels.AnimatedUnoLogo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class StartFrame extends JFrame {
    private Image backgroundImage;
    private AnimatedUnoLogo unoLogo;
    private StartButton startButton;
    private ButtonCursor buttonCursor;
    
    private static final int LOGO_WIDTH = 700; 
    private static final int LOGO_HEIGHT = 2000;
    private static final int BUTTON_WIDTH = 600; 
    private static final int BUTTON_HEIGHT = 120; 
    private static final int CURSOR_WIDTH = 80;
    private static final int CURSOR_HEIGHT = 80;
    
    //relative paths to try for the background image
    private static final String[] IMAGE_PATHS = {
        "/view/images/uno_background.jpg",
        "/images/uno_background.jpg",
        "/resources/images/uno_background.jpg", 
        "/uno_background.jpg",
        "uno_background.jpg",
        "./view/images/uno_background.jpg",
        "../images/uno_background.jpg",
        "../../images/uno_background.jpg"
    };
    
    public StartFrame() {
        setTitle("UNO Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); 
        setResizable(true);
        
        loadBackgroundImage();
        
        
        JPanel backgroundPanel = createBackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        
        //boxLayout
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        //the uno logo
        unoLogo = new AnimatedUnoLogo();
        unoLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        unoLogo.setPreferredSize(new Dimension(LOGO_WIDTH, LOGO_HEIGHT));
        unoLogo.setMaximumSize(new Dimension(LOGO_WIDTH, LOGO_HEIGHT));
        unoLogo.startAnimation();
        
        //start button
        startButton = new StartButton("Let's Get Started", new StartButton.StartButtonListener() {
            @Override
            public void onButtonClicked() {
                System.out.println("Start button clicked!");
                //here for the transition to the next page
            }
        });
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        startButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        
        //animated cursor pointing to the button
        buttonCursor = new ButtonCursor();
        buttonCursor.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonCursor.setPreferredSize(new Dimension(CURSOR_WIDTH, CURSOR_HEIGHT));
        buttonCursor.setMaximumSize(new Dimension(CURSOR_WIDTH, CURSOR_HEIGHT));
        buttonCursor.startAnimation();
        
        //components to the content panel with proper spacing
        contentPanel.add(Box.createVerticalGlue()); 
        
        contentPanel.add(unoLogo);
        
        contentPanel.add(Box.createRigidArea(new Dimension(0, -80)));
        
        //create a panel for the button and cursor with better alignment
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, -5, 0));
        buttonPanel.add(buttonCursor);
        buttonPanel.add(startButton);
        
        //add button panel to content panel
        contentPanel.add(buttonPanel);
        
        //add more space at the bottom to keep the button higher on screen
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(Box.createVerticalGlue());
        
        //add the content panel to the center of the background panel
        backgroundPanel.add(contentPanel, BorderLayout.CENTER);
        
        setContentPane(backgroundPanel);
    }
   
    private JPanel createBackgroundPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    //fallback gradient if image can't be loaded
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
    }
    
    private void loadBackgroundImage() {
        System.out.println("Attempting to load background image...");
        
        for (String path : IMAGE_PATHS) {
            try {
                URL imageUrl = getClass().getResource(path);
                if (imageUrl != null) {
                    backgroundImage = new ImageIcon(imageUrl).getImage();
                    System.out.println("Successfully loaded background image from resource: " + path);
                    return;
                }
            } catch (Exception e) {
                System.out.println("Failed to load from resource path: " + path);
            }
        }
        
        for (String path : IMAGE_PATHS) {
            try {
                URL imageUrl = getClass().getClassLoader().getResource(path);
                if (imageUrl != null) {
                    backgroundImage = new ImageIcon(imageUrl).getImage();
                    System.out.println("Successfully loaded background image from class loader: " + path);
                    return;
                }
            } catch (Exception e) {
                System.out.println("Failed to load from class loader path: " + path);
            }
        }
        
        File currentDir = new File(".");
        System.out.println("Current directory: " + currentDir.getAbsolutePath());
        
        String[] filePaths = {
            "src/main/resources/images/uno_background.jpg",
            "src/resources/images/uno_background.jpg",
            "resources/images/uno_background.jpg",
            "images/uno_background.jpg",
            "src/main/java/view/images/uno_background.jpg",
            "src/view/images/uno_background.jpg",
            "view/images/uno_background.jpg",
            "uno_background.jpg"
        };
        
        for (String path : filePaths) {
            try {
                File file = new File(path);
                if (file.exists()) {
                    backgroundImage = new ImageIcon(file.getAbsolutePath()).getImage();
                    System.out.println("Successfully loaded background image from file: " + file.getAbsolutePath());
                    return;
                }
            } catch (Exception e) {
                System.out.println("Failed to load from file path: " + path);
            }
        }
        
        System.err.println("Could not find background image after trying multiple paths. Using fallback gradient.");
        
        createFallbackBackgroundImage();
    }
    
    private void createFallbackBackgroundImage() {
        int width = 800;
        int height = 600;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(23, 107, 135), 
            width, height, new Color(0, 45, 60)
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
        
        g2d.setColor(new Color(255, 69, 0, 100)); 
        g2d.fillOval(width/2 - 200, height/2 - 200, 400, 400);
        
        g2d.dispose();
        backgroundImage = image;
    }
    
    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
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