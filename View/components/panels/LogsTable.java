package View.components.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import View.components.buttons.RoundedButton;

import java.awt.*;
import java.util.List;
import java.util.Map;


/*********** Supposed to be in the Framework *************/
class TTable extends JTable {
	public TTable(DefaultTableModel model) {
		super(model);
	}
}


/*********** Supposed to be in the Framework too *************/
class TScrollPane extends JScrollPane {
	public TScrollPane(TTable table) {
		super(table);
	}
}


class CustomCellRenderer extends JTextArea implements TableCellRenderer {
    public CustomCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        setText(value != null ? value.toString() : "");

        // Match cell background and font
        setFont(table.getFont());
        setBackground(table.getBackground());

        // Adjust height dynamically
        int lines = getLineCount();
        int lineHeight = getFontMetrics(getFont()).getHeight();
        int neededHeight = lines * lineHeight + 10; 
        if (table.getRowHeight(row) < neededHeight) {
            table.setRowHeight(row, neededHeight);
        }
        return this;
    }
}


// Should use TPanel when fixed
public class LogsTable extends JPanel {

    public LogsTable(String winnerName, Map<String, List<String>> playersLogs, JFrame frame) {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#21565a"));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.decode("#21565a"));
        
        RoundedButton backButton = new RoundedButton("Back");
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        // Player names as column headers
        String[] columns = playersLogs.keySet().toArray(new String[0]);

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        // Zid one row with all moves
        String[] row = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            row[i] = String.join("\n", playersLogs.get(columns[i]));
        }
        model.addRow(row);

        TTable table = new TTable(model);
        table.setFont(new Font("Amasis MT Pro Black", Font.PLAIN, 14));
        table.setEnabled(false);
        table.setShowGrid(true);
        table.setGridColor(new Color(245, 245, 220));
        table.setBackground(new Color(245, 245, 220)); // beige background

        //  Renderer for cells (moves)
        DefaultTableCellRenderer movesRenderer = new DefaultTableCellRenderer() {
            JTextArea textArea = new JTextArea();
            {
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setOpaque(true);
                textArea.setFont(new Font("Amasis MT Pro Black", Font.PLAIN, 14));
                textArea.setBackground(new Color(245, 245, 220)); // beige
                textArea.setForeground(Color.GRAY);
                textArea.setMargin(new Insets(10, 10, 10, 10));
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                textArea.setText(value != null ? value.toString() : "");
                return textArea;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer());
        }

        //  Styled header (same bg color)
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.decode("#21565a"));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Amasis MT Pro Black", Font.BOLD, 16));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        TScrollPane scroll = new TScrollPane(table);
        scroll.getViewport().setBackground(Color.decode("#21565a"));
        scroll.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        add(scroll, BorderLayout.CENTER);

        backButton.addActionListener(e -> {
            frame.setContentPane(new OutroPage(winnerName, playersLogs, frame));
            frame.revalidate();
            frame.repaint();
        });
    }
}