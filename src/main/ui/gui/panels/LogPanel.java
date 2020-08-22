package ui.gui.panels;

import model.Expense;
import model.ExpensesLog;
import persistence.Write;
import ui.gui.panels.tools.Table;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

// The LogPanel sets the log frame of the expenses tracker panel
public class LogPanel extends ExpenseTrackerPanel {
    private JButton remove = new JButton("Remove");
    private Container logPanel;
    private JLabel total;
    private JFrame logFrame = new JFrame("Expenses Tracker");
    private JComboBox filterBy;
    private JTextField filterText;
    private Write writer = new Write();
    private Table table;

    //MODIFIES: this
    //EFFECTS: allows the user to see a table of all their expenses from the expenses log
    protected void seeLog() {
        homeFrame.dispose();
        table = new Table(this);
        setFrame();

        logPanel = logFrame.getContentPane();
        logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.Y_AXIS));

        setLabel(logPanel, "EXPENSES LOG");

        JScrollPane scrollPane = new JScrollPane(table.makeTable());
        scrollPane.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT * 4));
        logFrame.add(scrollPane);
        logFrame.add(Box.createRigidArea(new Dimension(0, 30)));

        filter();

        total = new JLabel("Your total is: " + expensesLog.findTotalExpenses(), JLabel.CENTER);
        total.setFont(new Font("Century Gothic", Font.BOLD, 45));
        total.setAlignmentX(Component.CENTER_ALIGNMENT);
        logPanel.add(total);

        setRemoveButton(logPanel, logFrame);
        setHomeButton();
    }

    //EFFECTS: sets the frame for the expenses log
    @Override
    protected void setFrame() {
        logFrame.getContentPane().setBackground(BG_COLOUR);
        logFrame.setSize(WIDTH, HEIGHT);
        logFrame.setLocation(X_LOC, Y_LOC);
        logFrame.setVisible(true);
        logFrame.setResizable(false);

        logFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        logFrame.setLayout(new GridLayout(3, 1, 5, 5));
    }

    //MODIFIES: this
    //EFFECTS: returns to the home page from the expenses log
    @Override
    protected void setHome() {
        super.setFrame();
        Container panel = homeFrame.getContentPane();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        setTitle(title, panel);
        panel.add(title);

        setButton(panel, addExpenses);
        setButton(panel, log);
        setButton(panel, monthly);
    }

    //EFFECTS: places the Home Button on the expenses log frame
    @Override
    protected void setHomeButton() {
        super.setHomeButton();
        formatButton(home, logFrame, logFrame.getContentPane());
        logFrame.getContentPane().add(home);
        home.addActionListener(e -> {
            homeFrame.setVisible(true);
            logFrame.dispose();
        });
    }

    //EFFECTS: sets up the filter/search box
    private void filter() {
        JPanel filterPane = new JPanel(new GridLayout(2, 2, 40, 0));
        filterPane.setFont(FONT);
        filterPane.setBackground(BG_COLOUR);
        filterPane.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT + 10));

        filterBy = new JComboBox(new Object[]{"Nothing", "Item", "Category"});
        filterText = new JTextField(20);
        filterBox(filterPane);

        filterBy.setSelectedIndex(0);
        filterActionListener();

        filterDocumentListener();
    }

    //MODIFIES: this
    //EFFECTS: sets the Remove Button
    //source: http://1bestcsharp.blogspot.com/2015/05/java-jtable-add-delete-update-row.html
    protected void setRemoveButton(Container panel, JFrame frame) {
        formatButton(remove, frame, panel);
        panel.add(remove);

        remove.addActionListener(e -> {
            removeExpense();
        });
    }

    private void removeExpense() {
        int i = table.getSelectedRow();
        if (i >= 0) {
            LocalDate date = (LocalDate) table.getTableModel().getValueAt(i, 0);
            int year = date.getYear();
            int month = date.getMonthValue();
            int day = date.getDayOfMonth();

            String item = (String) table.getTableModel().getValueAt(i, 1);
            Expense.Category category = (Expense.Category) table.getTableModel().getValueAt(i, 2);
            BigDecimal cost = (BigDecimal) table.getTableModel().getValueAt(i, 3);
            double price = cost.doubleValue();
            expensesLog.removeExpense(new Expense(year, month, day, item, category, price));
            total.setText("Your total is: " + expensesLog.findTotalExpenses());
            try {
                writer.write(expensesLog.getExpenses(), FILE_NAME);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            table.getTableModel().removeRow(i);
        }
    }

    //source: https://stackoverflow.com/a/26031896
    //MODIFIES: this
    //EFFECTS: filters the table and changes the total displayed
    public void updateFilter() {
        Object selected = filterBy.getSelectedItem();
        TableRowSorter<TableModel> sorter = (TableRowSorter<TableModel>) table.getRowSorter();
        String text = "(?i)" + filterText.getText();
        if ("Nothing".equals(selected)) {
            sorter.setRowFilter(null);
            total.setText("Your total is: " + expensesLog.findTotalExpenses());
        } else {
            int col = -1;
            if ("Item".equals(selected)) {
                col = 1;
                total.setText("Your total is: " + expensesLog.findTotalExpensesItem(filterText.getText()));

            } else if ("Category".equals(selected)) {
                col = 2;
                isCategory();
            }
            sorter.setRowFilter(RowFilter.regexFilter(text, col));
        }
    }

    //EFFECTS: if any of the filter text is a category, then total will be set to that category
    private void isCategory() {
        if (filterText.getText().equalsIgnoreCase("meal")
                || (filterText.getText().equalsIgnoreCase("groceries"))
                || (filterText.getText().equalsIgnoreCase("TRAVEL"))
                || (filterText.getText().equalsIgnoreCase("ENTERTAINMENT"))
                || (filterText.getText().equalsIgnoreCase("BILLS"))
                || (filterText.getText().equalsIgnoreCase("SHOPPING"))
                || (filterText.getText().equalsIgnoreCase("TECH"))
                || (filterText.getText().equalsIgnoreCase("TRANSPORTATION"))
                || (filterText.getText().equalsIgnoreCase("Other"))) {
            total.setText("Your total is: " + expensesLog.findTotalExpensesCategory(filterText.getText()));
        }
    }

    //EFFECTS: filters the table
    private void filterActionListener() {
        filterBy.addActionListener(e -> updateFilter());
    }

    //EFFECTS: updates the filter
    private void filterDocumentListener() {
        filterText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFilter();
            }
        });
    }

    //MODIFIES: filterPane
    //EFFECTS: formats the filter box
    private void filterBox(JPanel filterPane) {
        filterPane.add(filterBy);
        filterPane.add(filterText);
        logFrame.add(filterPane);
        filterBy.setFont(FONT);
        filterText.setFont(FONT);
        filterPane.add(Box.createRigidArea(new Dimension(0, 20)));
        filterPane.add(Box.createRigidArea(new Dimension(0, 20)));
    }


    //EFFECTS: returns the expenses log
    public ExpensesLog getLog() {
        return this.expensesLog;
    }

}
