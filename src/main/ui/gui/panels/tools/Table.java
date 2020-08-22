package ui.gui.panels.tools;

import model.Expense;
import ui.gui.panels.LogPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static ui.gui.panels.tools.Chart.BG_COLOUR;
import static ui.gui.panels.tools.Chart.FONT;

//This Table class instantiates a table filled with expenses if there's any to be found
public class Table {
    private static final int BUTTON_WIDTH = 950;
    private DefaultTableModel tableModel;
    private JTable table;
    private LogPanel panel;

    public Table(LogPanel logPanel) {
        this.panel = logPanel;
        //source: https://stackoverflow.com/a/3134006
        String[] col = {"Date", "Item", "Category", "Cost"};
        tableModel = new DefaultTableModel(col, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    //EFFECTS: returns a table with all the expenses in the expenses log
    public JTable makeTable() {
        setData();

        table = new JTable(tableModel);
        formatTable();

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT);
        header.setBackground(new Color(201, 182, 228));

        return table;
    }

    //EFFECTS: formats the table
    public void formatTable() {
        table.setFont(FONT);
        table.setRowHeight(40);
        table.setBackground(BG_COLOUR);
        table.setFillsViewportHeight(true);
        table.setGridColor(new Color(239, 187, 207));
        table.setAutoCreateRowSorter(true);
        table.getColumn("Item").setPreferredWidth(BUTTON_WIDTH / 10);
        table.getColumn("Category").setPreferredWidth(BUTTON_WIDTH / 4);

        table.setRowSorter(new TableRowSorter<TableModel>(tableModel));
    }

    //MODIFIES: this
    //EFFECTS: generates an array of expenses
    private void setData() {
        for (int i = 0; i < panel.getLog().getExpenses().size(); i++) {
            LocalDate date = panel.getLog().getExpenses().get(i).getDate();
            String item = panel.getLog().getExpenses().get(i).getItem();
            Expense.Category category = panel.getLog().getExpenses().get(i).getCategory();
            double cost = panel.getLog().getExpenses().get(i).getCost().doubleValue();

            Object[] data = {date, item, category,
                    BigDecimal.valueOf(cost).setScale(2, BigDecimal.ROUND_HALF_UP)};

            tableModel.addRow(data);
        }
    }

    //EFFECTS: returns the selected row
    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    //EFFECTS: returns the table model
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    //EFFECTS: returns the row sorter
    public RowSorter<? extends TableModel> getRowSorter() {
        return table.getRowSorter();
    }
}
