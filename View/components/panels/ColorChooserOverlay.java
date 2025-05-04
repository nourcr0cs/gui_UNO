package View.components.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ColorChooserOverlay extends JPanel {

	private Consumer<String> onColorChosen;

	public ColorChooserOverlay(Consumer<String> callback) {
		this.onColorChosen = callback;
		setOpaque(false);
		setLayout(null);
		setBounds(0, 0, 800, 600); // Adapter à ta fenêtre

		// Positions circulaires simples
		add(createColorCircle("Red", Color.RED, 300, 100));
		add(createColorCircle("Green", Color.GREEN, 500, 100));
		add(createColorCircle("Blue", Color.BLUE, 300, 300));
		add(createColorCircle("Yellow", Color.YELLOW, 500, 300));
	}

	private JButton createColorCircle(String name, Color color, int x, int y) {
		JButton btn = new JButton();
		btn.setBackground(color);
		btn.setBounds(x, y, 80, 80);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setContentAreaFilled(true);
		btn.setOpaque(true);
		btn.setToolTipText(name);
		btn.addActionListener(e -> {
			onColorChosen.accept(name); // Appeler callback
		});
		return btn;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// Ajoute un fond semi-transparent
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setColor(new Color(0, 0, 0, 100));
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.dispose();
	}
}

/*
 * package view.components.panels;
 * 
 * import javax.swing.*; import java.awt.*; import java.awt.event.*; import
 * java.util.function.Consumer;
 * 
 * public class ColorChooserOverlay extends JPanel {
 * 
 * private final Consumer<String> onColorChosen;
 * 
 * public ColorChooserOverlay(Consumer<String> callback) { this.onColorChosen =
 * callback;
 * 
 * setOpaque(false); setLayout(null); setBounds(0, 0, 800, 600); // ⚠️ Adapter
 * si ta fenêtre a une autre taille
 * 
 * // Ajouter les cercles de couleurs add(createColorCircle("Red", Color.RED,
 * 300, 100)); add(createColorCircle("Green", Color.GREEN, 500, 100));
 * add(createColorCircle("Blue", Color.BLUE, 300, 300));
 * add(createColorCircle("Yellow", Color.YELLOW, 500, 300)); }
 * 
 * private JButton createColorCircle(String name, Color color, int x, int y) {
 * JButton btn = new JButton(); btn.setBackground(color); btn.setBounds(x, y,
 * 80, 80); btn.setBorderPainted(false); btn.setFocusPainted(false);
 * btn.setContentAreaFilled(true); btn.setOpaque(true);
 * btn.setToolTipText(name);
 * 
 * btn.addActionListener(e -> { // Appeler le callback avec la couleur choisie
 * onColorChosen.accept(name);
 * 
 * // Supprimer l'overlay de son parent (le faire disparaître) Container parent
 * = this.getParent(); if (parent != null) { parent.remove(this);
 * parent.repaint(); parent.revalidate(); } });
 * 
 * return btn; }
 * 
 * @Override protected void paintComponent(Graphics g) { // Fond
 * semi-transparent noir Graphics2D g2 = (Graphics2D) g.create();
 * g2.setColor(new Color(0, 0, 0, 100)); g2.fillRect(0, 0, getWidth(),
 * getHeight()); g2.dispose(); } }
 */
