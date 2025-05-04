package View.components.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

/**
 * A custom panel that simulates a modern glass/blur effect with rounded corners
 * and subtle highlights
 */
public class GlassPanel extends JPanel {

	private final int cornerRadius;
	private final float opacity;
	private final Color baseColor;
	private final boolean addBorder;

	public GlassPanel(int cornerRadius, float opacity, Color baseColor, boolean addBorder) {
		this.cornerRadius = cornerRadius;
		this.opacity = opacity;
		this.baseColor = baseColor;
		this.addBorder = addBorder;

		setOpaque(false);
	}

	/**
	 * Creates a new glass panel with default settings
	 */
	public GlassPanel() {
		this(20, 0.7f, new Color(255, 255, 255), true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();

		// Enable antialiasing for smooth edges
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		int width = getWidth();
		int height = getHeight();

		// Create the main semi-transparent fill
		// Mix the base color with a darker overlay for glass effect
		Color glassColor = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(),
				Math.round(opacity * 255));

		// Create the glass shape
		RoundRectangle2D glassShape = new RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius);

		// Fill with semi-transparent color
		g2d.setColor(glassColor);
		g2d.fill(glassShape);

		// Add subtle gradient overlay for depth
		GradientPaint gradientOverlay = new GradientPaint(0, 0, new Color(255, 255, 255, 25), 0, height,
				new Color(255, 255, 255, 5));
		g2d.setPaint(gradientOverlay);
		g2d.fill(glassShape);

		// Add top highlight for glass effect
		GradientPaint highlightGradient = new GradientPaint(0, 0, new Color(255, 255, 255, 60), 0, height / 3,
				new Color(255, 255, 255, 0));
		g2d.setPaint(highlightGradient);

		// Only highlight the top portion
		g2d.fill(new RoundRectangle2D.Float(1, 1, width - 2, height / 3, cornerRadius - 1, cornerRadius - 1));

		// Add subtle border if requested
		if (addBorder) {
			g2d.setColor(new Color(255, 255, 255, 50));
			g2d.setStroke(new BasicStroke(1.0f));
			g2d.draw(glassShape);
		}

		g2d.dispose();
	}
}