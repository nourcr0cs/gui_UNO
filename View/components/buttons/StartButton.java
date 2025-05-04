/******************  Supposed to be in the Framework ***************************/
package View.components.buttons;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class StartButton extends JButton {

	private final Color normalColor = Color.WHITE;
	private final Color hoverColor = Color.WHITE; // Keep text white on hover
	private boolean hover = false;
	private final StartButtonListener listener;

	public interface StartButtonListener {
		void onButtonClicked();
	}

	public StartButton(String text, StartButtonListener listener) {
		super(text);
		setHorizontalAlignment(SwingConstants.CENTER);
		this.listener = listener;
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
		setFocusPainted(false);
		setContentAreaFilled(false);

		setFont(new Font("Arial", Font.BOLD, 30));
		setForeground(normalColor);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (StartButton.this.listener != null) {
					StartButton.this.listener.onButtonClicked();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				hover = true;
				setForeground(hoverColor);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				hover = false;
				setForeground(normalColor);
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		int width = getWidth();
		int height = getHeight();

		// Create the rounded rectangle for the button
		RoundRectangle2D roundRect = new RoundRectangle2D.Float(0, 0, width - 1, height - 1, 15, 15);

		// Base colors for the gradient
		Color startColor = new Color(240, 95, 64);
		Color endColor = new Color(255, 183, 76); // yellowish orange

		// If hovering, add a gray tint to the colors
		if (hover) {
			startColor = new Color(Math.max(startColor.getRed() - 30, 0), Math.max(startColor.getGreen() - 30, 0),
					Math.max(startColor.getBlue() - 30, 0));
			endColor = new Color(Math.max(endColor.getRed() - 30, 0), Math.max(endColor.getGreen() - 30, 0),
					Math.max(endColor.getBlue() - 30, 0));
		}

		// Create the gradient for the button
		GradientPaint gradient = new GradientPaint(0, 0, startColor, width, 0, endColor);

		g2d.setPaint(gradient);
		g2d.fill(roundRect);

		// Add the glossy highlight effect
		GradientPaint glossGradient = new GradientPaint(0, 0, new Color(255, 255, 255, 50), 0, height / 2,
				new Color(255, 255, 255, 0));

		g2d.setPaint(glossGradient);
		g2d.fill(new RoundRectangle2D.Float(2, 2, width - 5, height / 2 - 2, 13, 13));

		// Center the text
		FontMetrics fm = g2d.getFontMetrics(getFont());
		int textWidth = fm.stringWidth(getText());
		int textX = (width - textWidth) / 2;
		int textY = ((height - fm.getHeight()) / 2) + fm.getAscent();

		// Add subtle text shadow
		g2d.setColor(new Color(0, 0, 0, 30));
		g2d.setFont(getFont());
		g2d.drawString(getText(), textX + 1, textY + 1);

		// Draw the actual text
		g2d.setColor(getForeground());
		g2d.drawString(getText(), textX, textY);

		g2d.dispose();
	}

	@Override
	public Dimension getPreferredSize() {
		FontMetrics fm = getFontMetrics(getFont());
		int width = fm.stringWidth(getText()) + 60;
		int height = fm.getHeight() + 25;
		return new Dimension(width, height);
	}
}