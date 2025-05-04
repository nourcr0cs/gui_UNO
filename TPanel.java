/**************************** Supposed be in Framework ***********************************/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class TPanel extends JPanel {
	private BufferedImage backgroundImage;
	private String currentImagePath = "";
	private Color backgroundColor = new Color(240, 225, 240); // Default background color
	private boolean stretchToFill = true; // New flag to control stretching behavior

	public TPanel() {
		setLayout(new BorderLayout());
		setOpaque(false);
	}

	// Method to set background color with themes
	public void backgroundColor(String theme) {
		if (theme.equalsIgnoreCase("black")) {
			backgroundColor = new Color(0, 0, 0);
		} else if (theme.equalsIgnoreCase("dark blue")) {
			backgroundColor = new Color(0, 0, 139);
		}
		setBackground(backgroundColor);
		repaint();
	}

	// Set whether to stretch the image to fill the panel or maintain aspect ratio
	public void setStretchToFill(boolean stretch) {
		this.stretchToFill = stretch;
		repaint();
	}

	public boolean setBackgroundImage(String imagePath) {
		System.out.println("Attempting to load image: " + imagePath);

		try {
			// Verify file exists and is readable
			File imageFile = new File(imagePath);
			if (!imageFile.exists()) {
				System.err.println("Error: Image file does not exist: " + imagePath);
				System.err.println("Absolute path: " + imageFile.getAbsolutePath());
				return false;
			}

			if (!imageFile.canRead()) {
				System.err.println("Error: Cannot read image file (permission issue): " + imagePath);
				return false;
			}

			BufferedImage newImage = ImageIO.read(imageFile);
			if (newImage == null) {
				System.err.println("Error: ImageIO.read returned null. Unsupported image format: " + imagePath);
				return false;
			}

			System.out.println(
					"Image loaded successfully. Dimensions: " + newImage.getWidth() + "x" + newImage.getHeight());

			this.backgroundImage = newImage;
			this.currentImagePath = imagePath;
			repaint();
			return true;

		} catch (IOException e) {
			System.err.println("IOException while loading image: " + e.getMessage());
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			System.err.println("Unexpected error while loading image: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Fill with background color
		if (backgroundColor != null) {
			g.setColor(backgroundColor);
			g.fillRect(0, 0, getWidth(), getHeight());
		}

		// Draw the background image if available
		if (backgroundImage != null && getWidth() > 0 && getHeight() > 0) {
			if (stretchToFill) {
				// Stretch the image to fill the entire panel
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
				System.out.println("Drawing stretched image at: 0,0 with size: " + getWidth() + "x" + getHeight());
			} else {
				// Calculate scaling while maintaining aspect ratio
				double widthRatio = (double) getWidth() / backgroundImage.getWidth();
				double heightRatio = (double) getHeight() / backgroundImage.getHeight();
				double ratio = Math.min(widthRatio, heightRatio);

				int scaledWidth = (int) (backgroundImage.getWidth() * ratio);
				int scaledHeight = (int) (backgroundImage.getHeight() * ratio);

				// Center the image
				int x = (getWidth() - scaledWidth) / 2;
				int y = (getHeight() - scaledHeight) / 2;

				g.drawImage(backgroundImage, x, y, scaledWidth, scaledHeight, this);
				System.out.println("Drawing proportional image at: " + x + "," + y + " with size: " + scaledWidth + "x"
						+ scaledHeight);
			}
		} else {
			if (backgroundImage == null) {
				System.out.println("No background image to draw");
			}
		}
	}

	public void debugVisibility() {
		System.out.println("Current image path: " + currentImagePath);
		System.out.println("Panel dimensions: " + getWidth() + "x" + getHeight());
		System.out.println("Image loaded: " + (backgroundImage != null));
		System.out.println("Stretch to fill: " + stretchToFill);
		if (backgroundImage != null) {
			System.out.println("Image dimensions: " + backgroundImage.getWidth() + "x" + backgroundImage.getHeight());
		}
	}
}