package View.FramesToBePanelsLater;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import View.components.labels.PlayerLabel;
import View.components.panels.CirclePanel;
import View.components.panels.GameFlowIndicator;

public class GameSpaceFrame extends UnoBackgroundGrid {

	// default game flow (clock-d)
	private boolean gameDirectionClockwise = true;

	private CirclePanel circlePanel;
	private GameFlowIndicator flowIndicator;

	private PlayerLabel[] playerLabels;
	private int currentPlayerIndex = 0;

	public GameSpaceFrame() {
		super();
		setTitle("UNO Game Space");

		circlePanel = new CirclePanel();
		int circlePanelSize = 400;
		int circleX = (getWidth() - circlePanelSize) / 2;
		int circleY = (getHeight() - circlePanelSize) / 2;
		circlePanel.setBounds(circleX, circleY, circlePanelSize, circlePanelSize);
		getLayeredPane().add(circlePanel, JLayeredPane.PALETTE_LAYER);

		flowIndicator = new GameFlowIndicator(Color.WHITE, 4);
		int flowSize = 60;
		int flowX = (getWidth() - flowSize) / 2;
		int flowY = circleY + 10;
		flowIndicator.setBounds(flowX, flowY, flowSize, flowSize);
		flowIndicator.setOpaque(false);
		getLayeredPane().add(flowIndicator, JLayeredPane.MODAL_LAYER);

		initializePlayerLabels(circleX, circleY, circlePanelSize);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int newCircleX = (getWidth() - circlePanel.getWidth()) / 2;
				int newCircleY = (getHeight() - circlePanel.getHeight()) / 2;
				circlePanel.setBounds(newCircleX, newCircleY, circlePanel.getWidth(), circlePanel.getHeight());

				int newFlowX = (getWidth() - flowIndicator.getWidth()) / 2;
				int newFlowY = newCircleY + 30;
				flowIndicator.setBounds(newFlowX, newFlowY, flowIndicator.getWidth(), flowIndicator.getHeight());

				updatePlayerLabelPositions(newCircleX, newCircleY, circlePanel.getWidth(), circlePanel.getHeight());

				Component[] components = getLayeredPane().getComponentsInLayer(JLayeredPane.DRAG_LAYER);
				for (Component comp : components) {
					if (comp instanceof JPanel) {
						comp.setBounds(0, getHeight() - 80, getWidth(), 50);
					}
				}

				getLayeredPane().revalidate();
				getLayeredPane().repaint();
			}
		});
	}

	private void initializePlayerLabels(int circleX, int circleY, int circlePanelSize) {
		playerLabels = new PlayerLabel[4];

		playerLabels[0] = new PlayerLabel("You", 0);
		playerLabels[1] = new PlayerLabel("imene", 1);
		playerLabels[2] = new PlayerLabel("hiba", 2);
		playerLabels[3] = new PlayerLabel("meroua", 3);

		// set the first player as active initially
		playerLabels[0].setActive(true);

		int labelWidth = 100;
		int labelHeight = 90;

		for (PlayerLabel label : playerLabels) {
			getLayeredPane().add(label, JLayeredPane.MODAL_LAYER);
		}
	}

	private void updatePlayerLabelPositions(int circleX, int circleY, int circlePanelWidth, int circlePanelHeight) {
		int labelWidth = 100;
		int labelHeight = 90;

		int bottomX = (getWidth() - labelWidth) / 2;
		int bottomY = circleY + circlePanelHeight - 45;
		playerLabels[0].setBounds(bottomX, bottomY, labelWidth, labelHeight);

		int rightX = circleX + circlePanelWidth - 45;
		int rightY = circleY + (circlePanelHeight - labelHeight) / 2;
		playerLabels[1].setBounds(rightX, rightY, labelWidth, labelHeight);

		int topX = (getWidth() - labelWidth) / 2;
		int topY = circleY - labelHeight + 45;
		playerLabels[2].setBounds(topX, topY, labelWidth, labelHeight);

		int leftX = circleX - labelWidth + 58;
		int leftY = circleY + (circlePanelHeight - labelHeight) / 2 - 10;
		playerLabels[3].setBounds(leftX, leftY, labelWidth, labelHeight);
	}

	public void moveToNextPlayer() {
		playerLabels[currentPlayerIndex].setActive(false);

		if (gameDirectionClockwise) {
			currentPlayerIndex = (currentPlayerIndex + 3) % 4;
		} else {
			currentPlayerIndex = (currentPlayerIndex + 1) % 4;
		}

		// Activate new current player
		playerLabels[currentPlayerIndex].setActive(true);

		System.out.println("Current player: " + playerLabels[currentPlayerIndex].getPlayerName());
	}

	public void setPlayerName(int playerIndex, String name) {
		if (playerIndex >= 0 && playerIndex < playerLabels.length) {
			playerLabels[playerIndex].setPlayerName(name);
		}
	}

	public String getPlayerName(int playerIndex) {
		if (playerIndex >= 0 && playerIndex < playerLabels.length) {
			return playerLabels[playerIndex].getPlayerName();
		}
		return "";
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public void setCurrentPlayer(int playerIndex) {
		if (playerIndex >= 0 && playerIndex < playerLabels.length) {
			playerLabels[currentPlayerIndex].setActive(false);
			currentPlayerIndex = playerIndex;
			playerLabels[currentPlayerIndex].setActive(true);
		}
	}

	public void reverseGameDirection() {
		gameDirectionClockwise = !gameDirectionClockwise;
		flowIndicator.toggleDirection();

		System.out.println("Game direction reversed: " + (gameDirectionClockwise ? "Clockwise" : "Counter-clockwise"));
	}

	public boolean isGameDirectionClockwise() {
		return gameDirectionClockwise;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			GameSpaceFrame frame = new GameSpaceFrame();
			frame.setVisible(true);
		});
	}
}
