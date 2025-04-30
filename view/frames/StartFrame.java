package view.frames;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import view.components.buttons.StartButton;
import view.components.buttons.HowToPlayButton;
import view.components.panels.GlassPanel;
import view.components.labels.AnimatedUnoLogo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class StartFrame extends JFrame {
    private Image backgroundImage;
    private StartButton startButton;
    private HowToPlayButton howToPlayButton;
    private AnimatedUnoLogo animatedLogo;
    
    private static final int CONTAINER_WIDTH = 500;  
    private static final int CONTAINER_HEIGHT = 400; 
    private static final int BUTTON_WIDTH = 300; 
    private static final int BUTTON_HEIGHT = 60;
    private static final int LOGO_WIDTH = 150;
    private static final int LOGO_HEIGHT = 100;
    
    
    private static final int VERTICAL_OFFSET = -150;  
    
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
        setSize(1500, 800);
        setLocationRelativeTo(null); 
        setResizable(true);
        
        loadBackgroundImage();
        
        JPanel backgroundPanel = createBackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        
        GlassPanel containerPanel = new GlassPanel(35, 0.7f, new Color(20, 80, 80), true);
        containerPanel.setPreferredSize(new Dimension(CONTAINER_WIDTH, CONTAINER_HEIGHT));
        containerPanel.setLayout(new BorderLayout());
        
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new BorderLayout());
        logoPanel.setBorder(new EmptyBorder(20, 0, 10, 0)); 
        
        animatedLogo = new AnimatedUnoLogo();
        animatedLogo.setPreferredSize(new Dimension(LOGO_WIDTH, LOGO_HEIGHT));
        animatedLogo.setAnimationAmplitude(3);
        animatedLogo.setAnimationSpeed(7);    
        
        JPanel logoWrapperPanel = new JPanel();
        logoWrapperPanel.setOpaque(false);
        logoWrapperPanel.add(animatedLogo);
        
        logoPanel.add(logoWrapperPanel, BorderLayout.CENTER);
        
        containerPanel.add(logoPanel, BorderLayout.NORTH);
        
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setOpaque(false);
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBorder(new EmptyBorder(0, 30, 0, 30));
        
        //"Let's Get Started" title
        JLabel titleLabel = new JLabel("Let's Get Started !");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContentPanel.add(titleLabel);
        
        mainContentPanel.add(Box.createRigidArea(new Dimension(0, 40))); 
        
        startButton = new StartButton("Play Now", new StartButton.StartButtonListener() {
            @Override
            public void onButtonClicked() {
                System.out.println("Play Now button clicked!");
                // Here for the transition to the next page
            }
        });
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        startButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        
        mainContentPanel.add(startButton);
        
        containerPanel.add(mainContentPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBorder(new EmptyBorder(0, 0, 25, 0)); 
        
        howToPlayButton = new HowToPlayButton(new HowToPlayButton.HowToPlayButtonListener() {
            @Override
            public void onButtonClicked() {
                System.out.println("How to Play button clicked!");
            }
        });
        
        bottomPanel.add(howToPlayButton);
        
        containerPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setOpaque(false);
        wrapperPanel.setLayout(null); 
        
        int initialWidth = 1500; 
        int initialHeight = 800; 
        
        int containerX = (initialWidth - CONTAINER_WIDTH) / 2;
        int containerY = (initialHeight - CONTAINER_HEIGHT) / 2 + VERTICAL_OFFSET;
        
        containerPanel.setBounds(containerX, containerY, CONTAINER_WIDTH, CONTAINER_HEIGHT);
        
        wrapperPanel.add(containerPanel);
        
        backgroundPanel.add(wrapperPanel, BorderLayout.CENTER);
        
        setContentPane(backgroundPanel);
        
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                int newContainerX = (getWidth() - CONTAINER_WIDTH) / 2;
                int newContainerY = (getHeight() - CONTAINER_HEIGHT) / 2 + VERTICAL_OFFSET;
                
                containerPanel.setBounds(newContainerX, newContainerY, CONTAINER_WIDTH, CONTAINER_HEIGHT);
            }
        });
        
        SwingUtilities.invokeLater(() -> {
            animatedLogo.startAnimation();
        });
    }
   
    private JPanel createBackgroundPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    Graphics2D g2d = (Graphics2D) g;
                    GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(18, 85, 85), 
                        getWidth(), getHeight(), new Color(10, 45, 45)
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
        
        System.err.println("Could not find background image after trying multiple paths. Using fallback gradient.");
        createFallbackBackgroundImage();
    }
    
    private void createFallbackBackgroundImage() {
        int width = 800;
        int height = 600;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(18, 85, 85), 
            width, height, new Color(10, 45, 45)
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
        
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