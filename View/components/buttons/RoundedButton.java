/******************  Supposed to be in the Framework ***************************/
package View.components.buttons;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    public RoundedButton(String text) {
        super(text);
        setFont(new Font("Arial Black", Font.PLAIN, 14));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gp;
        if (getModel().isPressed()) {
            gp = new GradientPaint(0, 0, new Color(0xCC6600), 0, getHeight(), new Color(0x994C00));
        } else if (getModel().isRollover()) {
            gp = new GradientPaint(0, 0, new Color(0xFFB347), 0, getHeight(), new Color(0xCC6600));
        } else {
            gp = new GradientPaint(0, 0, new Color(0xFF9900), 0, getHeight(), new Color(0xCC6600));
        }

        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        // Draw text normally (centered)
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

        g2.setFont(getFont());
        g2.setColor(getForeground());
        g2.drawString(getText(), x, y);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0x994C00));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
        g2.dispose();
    }
}
