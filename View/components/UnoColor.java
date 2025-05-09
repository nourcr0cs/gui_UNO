
package View.components;

import java.awt.Color;

public enum UnoColor {
	RED(new Color(237, 28, 36)), BLUE(new Color(0, 114, 206)), GREEN(new Color(0, 175, 80)),
	YELLOW(new Color(255, 242, 0)), BLACK(Color.BLACK);

	private final Color color;

	UnoColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	// bch n3ref ama couleur bch nmdha lcarta ta3 sa7
	public static UnoColor fromString(String colorStr) {
		return switch (colorStr.toUpperCase()) {
		case "RED" -> RED;
		case "BLUE" -> BLUE;
		case "GREEN" -> GREEN;
		case "YELLOW" -> YELLOW;
		case "BLACK" -> BLACK;
		default -> throw new IllegalArgumentException("Invalid color: " + colorStr);
		};
	}

}
