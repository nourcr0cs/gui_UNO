package View.components.labels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * A custom component that displays an animated UNO logo with floating and
 * pulsing effects
 */
public class AnimatedUnoLogo extends JPanel {

	private Timer animationTimer;
	private double animationAngle = 0;
	private double scaleAnimation = 0;
	private int animationAmplitude = 5; // Vertical movement amount
	private boolean useImage = false;
	private Image logoImage;

	// Logo dimensions
	private static final int DEFAULT_WIDTH = 150;
	private static final int DEFAULT_HEIGHT = 100;

	// Paths to try for the UNO logo image
	private static final String[] LOGO_PATHS = { "/view/images/uno_logo.png", "/images/uno_logo.png",
			"/resources/images/uno_logo.png", "/uno_logo.png", "uno_logo.png", "./view/images/uno_logo.png",
			"../images/uno_logo.png", "../../images/uno_logo.png" };

	public AnimatedUnoLogo() {
		setOpaque(false);
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

		// Try to load the logo image
		loadLogoImage();

		// Set up animation timer
		animationTimer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateAnimation();
			}
		});
	}

	/**
	 * Attempts to load the UNO logo image from various paths
	 */
	private void loadLogoImage() {
		System.out.println("Attempting to load UNO logo for animation...");

		// Try loading from resources
		for (String path : LOGO_PATHS) {
			try {
				URL imageUrl = getClass().getResource(path);
				if (imageUrl != null) {
					logoImage = new ImageIcon(imageUrl).getImage();
					useImage = true;
					System.out.println("Successfully loaded UNO logo from resource: " + path);
					return;
				}
			} catch (Exception e) {
				System.out.println("Failed to load from resource path: " + path);
			}
		}

		System.out.println("Could not find UNO logo image. Will use dynamic drawing instead.");
	}

	public void startAnimation() {
		if (!animationTimer.isRunning()) {
			animationTimer.start();
			System.out.println("UNO logo animation started");
		}
	}

	public void stopAnimation() {
		if (animationTimer.isRunning()) {
			animationTimer.stop();
		}
	}

	private void updateAnimation() {
		animationAngle += 0.1;
		scaleAnimation = Math.sin(animationAngle / 2) * 0.05 + 1.0; // Scale between 0.95 and 1.05
		repaint();
	}

	public void setAnimationAmplitude(int amplitude) {
		this.animationAmplitude = amplitude;
	}

	public void setAnimationSpeed(int speed) {
		if (speed > 0 && speed <= 10) {
			animationTimer.setDelay(50 - speed * 5);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		int width = getWidth();
		int height = getHeight();

		int yOffset = (int) (Math.sin(animationAngle) * animationAmplitude);

		AffineTransform originalTransform = g2d.getTransform();
		AffineTransform transform = new AffineTransform();

		transform.translate(width / 2, height / 2 + yOffset);
		transform.scale(scaleAnimation, scaleAnimation);
		transform.translate(-width / 2, -height / 2);

		g2d.setTransform(transform);

		if (useImage && logoImage != null) {
			int imageWidth = Math.min(width, DEFAULT_WIDTH);
			int imageHeight = Math.min(height, DEFAULT_HEIGHT);
			int x = (width - imageWidth) / 2;
			int y = (height - imageHeight) / 2;
			g2d.drawImage(logoImage, x, y, imageWidth, imageHeight, this);
		} else {
			drawUnoLogo(g2d, width, height);
		}

		g2d.setTransform(originalTransform);
		g2d.dispose();
	}

	private void drawUnoLogo(Graphics2D g2d, int width, int height) {
		// Draw a red oval background
		int ovalWidth = Math.min(width - 10, DEFAULT_WIDTH - 10);
		int ovalHeight = Math.min(height - 10, DEFAULT_HEIGHT - 10);
		int x = (width - ovalWidth) / 2;
		int y = (height - ovalHeight) / 2;

		GradientPaint gradient = new GradientPaint(x, y, new Color(255, 50, 50), x, y + ovalHeight,
				new Color(200, 20, 20));
		g2d.setPaint(gradient);
		g2d.fillOval(x, y, ovalWidth, ovalHeight);

		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(2f));
		g2d.drawOval(x, y, ovalWidth, ovalHeight);

		int fontSize = Math.min(width, height) / 3;
		Font logoFont = new Font("Arial", Font.BOLD, fontSize);
		g2d.setFont(logoFont);

		// Text measurements
		FontMetrics fm = g2d.getFontMetrics();
		String text = "UNO";
		int textWidth = fm.stringWidth(text);
		int textX = (width - textWidth) / 2;
		int textY = (height + fm.getAscent() - fm.getDescent()) / 2;

		// Draw yellow outline
		g2d.setColor(Color.YELLOW);
		g2d.drawString(text, textX - 1, textY - 1);
		g2d.drawString(text, textX + 1, textY - 1);
		g2d.drawString(text, textX - 1, textY + 1);
		g2d.drawString(text, textX + 1, textY + 1);

		// Draw black text
		g2d.setColor(Color.BLACK);
		g2d.drawString(text, textX, textY);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
}