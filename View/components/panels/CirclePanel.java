package View.components.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class CirclePanel extends JPanel {

	private static final Color BACKGROUND_COLOR = new Color(33, 86, 90);
	private static final Color CIRCLE_COLOR = Color.WHITE;
	private static final int CIRCLE_STROKE = 6;

	public CirclePanel() {

		// setBackground(BACKGROUND_COLOR);
		setOpaque(false);
		setPreferredSize(new Dimension(400, 400));
		setSize(400, 400);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int width = getWidth();
		int height = getHeight();

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// circle dimension
		int diameter = Math.min(width, height) - 40;
		int x = (width - diameter) / 2;
		int y = (height - diameter) / 2;

		g2d.setColor(CIRCLE_COLOR);
		g2d.setStroke(new BasicStroke(CIRCLE_STROKE));
		g2d.drawOval(x, y, diameter, diameter);

		g2d.dispose();
	}

}