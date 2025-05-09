
package View.components.unoCards;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

import View.components.UnoColor;

public class UnoNumberCardButton extends UnoCardButton {
	private final int number;

	public UnoNumberCardButton(UnoColor cardColor, int number) {
		super(cardColor);
		if (number < 0 || number > 9) {
			throw new IllegalArgumentException("Number must be between 0 and 9");
		}
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (!isFaceUp) {
			return;
		}

		Graphics2D cmp = (Graphics2D) g.create();
		cmp.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int width = getWidth();
		int height = getHeight();

		// central number
		cmp.setColor(cardColor.getColor());
		cmp.setFont(new Font("Arial", Font.BOLD, height / 4));
		FontMetrics fm = cmp.getFontMetrics();
		String numberText = String.valueOf(number);
		int textWidth = fm.stringWidth(numberText);
		int textX = (width - textWidth) / 2;
		int textBaseline = height / 2 + fm.getAscent() / 2;
		cmp.drawString(numberText, textX, textBaseline);

		// a short line under the digit
		if (number == 6 || number == 9) {
			int lineLength = textWidth / 2;
			int lineX = textX + (textWidth - lineLength) / 2;
			int lineY = textBaseline + 4;

			Stroke oldStk = cmp.getStroke();
			cmp.setStroke(new BasicStroke(2));
			cmp.drawLine(lineX, lineY, lineX + lineLength, lineY);
			cmp.setStroke(oldStk);
		}

		cmp.setFont(new Font("Arial", Font.BOLD, height - 93));
		cmp.setColor(Color.WHITE);
		cmp.drawString(numberText, width / 12 + 3, height / 6 + 4);

		cmp.translate(width, height);
		cmp.rotate(Math.PI);
		cmp.drawString(numberText, width / 12 + 3, height / 6 + 1);

		cmp.dispose();
	}
}