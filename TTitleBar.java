/**************************** Supposed be in Framework ***********************************/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TTitleBar extends JPanel {
	private JPanel controlsPanel;
	private JButton revealBtn;
	private JLabel titleLabel;

	public TTitleBar(JFrame parent) {
		setLayout(new BorderLayout());
		setBackground(new Color(35, 30, 50));
		setPreferredSize(new Dimension(100, 40));

		// Icon
		JLabel iconLabel = new JLabel();
		ImageIcon icon = new ImageIcon("C:\\Users\\Administrateur\\Documents\\ma personalzation\\myicon.png");
		Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		iconLabel.setIcon(new ImageIcon(scaledIcon));
		iconLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
		add(iconLabel, BorderLayout.WEST);

		// Title with gradient effect
		titleLabel = new JLabel("", SwingConstants.CENTER) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				// Gradient effect
				GradientPaint gradient = new GradientPaint(0, 0, new Color(200, 100, 255), getWidth(), getHeight(),
						new Color(255, 180, 255));
				g2.setPaint(gradient);
				g2.setFont(getFont());

				FontMetrics fm = g2.getFontMetrics();
				String text = getText();
				int x = (getWidth() - fm.stringWidth(text)) / 2;
				int y = (getHeight() + fm.getAscent()) / 2 - 4;

				// Glow shadow
				g2.setColor(new Color(120, 60, 180, 80));
				for (int i = 0; i < 3; i++) {
					g2.drawString(text, x - i, y - i);
				}

				// Draw main gradient text
				g2.setPaint(gradient);
				g2.drawString(text, x, y);

				g2.dispose();
			}
		};

		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setOpaque(false);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setPreferredSize(new Dimension(200, 40));
		add(titleLabel, BorderLayout.CENTER);

		// Reveal button
		revealBtn = new JButton();
		revealBtn.setBackground(new Color(160, 90, 180));
		revealBtn.setPreferredSize(new Dimension(20, 20));
		revealBtn.setBorderPainted(false);
		revealBtn.setFocusPainted(false);
		revealBtn.setOpaque(true);
		revealBtn.setContentAreaFilled(true);
		revealBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		revealBtn.setToolTipText("Show Controls");
		revealBtn.setBorder(BorderFactory.createEmptyBorder());
		revealBtn.setUI(new RoundedButtonUI());

		// Control buttons panel
		controlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		controlsPanel.setOpaque(false);
		controlsPanel.setVisible(false);

		controlsPanel.add(createCircleControlButton("–", e -> parent.setState(Frame.ICONIFIED)));
		controlsPanel.add(createCircleControlButton("◻", e -> {
			if ((parent.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
				parent.setExtendedState(JFrame.NORMAL);
			} else {
				parent.setExtendedState(JFrame.MAXIMIZED_BOTH);
			}
		}));
		controlsPanel.add(createCircleControlButton("X", e -> System.exit(0)));

		// Toggle effect on click
		revealBtn.addActionListener(e -> {
			controlsPanel.setVisible(!controlsPanel.isVisible());
		});

		JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		rightPanel.setOpaque(false);
		rightPanel.add(controlsPanel);
		rightPanel.add(revealBtn);
		add(rightPanel, BorderLayout.EAST);
	}

	// Method to set the title text
	public void setTitle(String title) {
		titleLabel.setText(title);
	}

	private JButton createCircleControlButton(String label, ActionListener action) {
		JButton btn = new JButton(label);
		btn.setFont(new Font("Arial", Font.BOLD, 12));
		btn.setForeground(Color.WHITE);
		btn.setBackground(new Color(100, 50, 120));
		btn.setPreferredSize(new Dimension(20, 20));
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setOpaque(true);
		btn.setUI(new RoundedButtonUI());
		btn.addActionListener(action);
		return btn;
	}

	static class RoundedButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
		@Override
		public void installUI(JComponent c) {
			super.installUI(c);
			c.setOpaque(false);
			c.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		}

		@Override
		public void paint(Graphics g, JComponent c) {
			AbstractButton b = (AbstractButton) c;
			Graphics2D g2 = (Graphics2D) g.create();

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			int width = c.getWidth();
			int height = c.getHeight();

			g2.setColor(b.getModel().isPressed() ? b.getBackground().darker()
					: b.getModel().isRollover() ? b.getBackground().brighter() : b.getBackground());

			g2.fillOval(0, 0, width, height);

			FontMetrics fm = g2.getFontMetrics();
			String text = b.getText();
			int x = (width - fm.stringWidth(text)) / 2;
			int y = (height + fm.getAscent()) / 2 - 3;
			g2.setColor(b.getForeground());
			g2.drawString(text, x, y);
			g2.dispose();
		}
	}
}