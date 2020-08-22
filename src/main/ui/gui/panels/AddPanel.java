package ui.gui.panels;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import model.Expense;
import persistence.Write;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.LocalDate;

import static model.Expense.parseCategory;

// The AddPanel sets the add frame of the expenses tracker panel
public class AddPanel extends ExpenseTrackerPanel {
    private JDialog dialog;
    private final String[] categories = {"MEAL", "GROCERIES", "TRAVEL", "ENTERTAINMENT", "BILLS", "SHOPPING", "TECH",
            "TRANSPORTATION", "OTHER"};
    private JFrame addFrame;
    private Write writer = new Write();
    private DatePicker datePicker;

    //MODIFIES: this
    //EFFECTS: sets the frame for adding an expense
    protected void addNew() {
        homeFrame.dispose();
        setFrame();
        addFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container panel = addFrame.getContentPane();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        addFrame.add(inputPanel);
        inputPanel.setBackground(BG_COLOUR);
        inputPanel.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT * 7));

        setLabel(inputPanel, "ADD EXPENSES");

        inputPanel.add(Box.createRigidArea(new Dimension(0, 60)));

        inputPanel.add(setDate());

        inputPanel.add(Box.createRigidArea(new Dimension(0, 60)));

        JTextArea item = new JTextArea("");
        setInput(inputPanel, item);

        JComboBox category = setCategory(inputPanel);

        inputPanel.add(Box.createRigidArea(new Dimension(0, 60)));

        JTextField cost = setCost(inputPanel);

        setAdd(panel, item, cost, category);

        setHomeButton();

    }

    //EFFECTS: sets the layout of the Add Frame
    @Override
    protected void setFrame() {
        addFrame = new JFrame("Expenses Tracker");
        addFrame.getContentPane().setBackground(BG_COLOUR);
        addFrame.setSize(WIDTH, HEIGHT);
        addFrame.setLocation(X_LOC, Y_LOC);
        addFrame.setVisible(true);
        addFrame.setResizable(false);
    }

    //MODIFIES: this
    //EFFECTS: returns to the home page from the add expenses frame
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

    //EFFECTS: sets the Home Button on the add expenses frame
    @Override
    protected void setHomeButton() {
        super.setHomeButton();
        formatButton(home, addFrame, addFrame.getContentPane());
        addFrame.getContentPane().add(home);
        home.addActionListener(e -> {
            homeFrame.setVisible(true);
            addFrame.dispose();
        });

    }

    //MODIFIES: this
    //EFFECTS: creates a date picker
    protected DatePicker setDate() {
        DatePickerSettings dateSettings = new DatePickerSettings();

        dateSettings.setFontCalendarWeekNumberLabels(FONT_TITLE);

        dateSettings.setFontCalendarDateLabels(FONT);
        dateSettings.setFontMonthAndYearNavigationButtons(FONT);
        dateSettings.setFontCalendarWeekdayLabels(FONT);
        dateSettings.setFontTodayLabel(FONT);
        dateSettings.setFontMonthAndYearMenuLabels(FONT);
        dateSettings.setFontValidDate(FONT);
        dateSettings.setFontInvalidDate(FONT);
        dateSettings.setFontClearLabel(FONT);

        dateSettings.setColor(DatePickerSettings.DateArea.DatePickerTextValidDate, COLOR);

        datePicker = new DatePicker(dateSettings);
        datePicker.setEnabled(true);
        datePicker.setMaximumSize(new Dimension(BUTTON_WIDTH - 10, BUTTON_HEIGHT));
        datePicker.setAlignmentX(Component.CENTER_ALIGNMENT);
        datePicker.setDateToToday();

        return datePicker;
    }

    //MODIFIES: this
    //EFFECTS: sets the Add Button
    //source: https://stackoverflow.com/q/12260962
    protected void setAdd(Container panel, JTextArea item, JTextField cost, JComboBox category) {
        JButton add = new JButton("ADD");

        InputMap map = add.getInputMap();
        map.put(KeyStroke.getKeyStroke("ENTER"), "none");
        map.put(KeyStroke.getKeyStroke("released ENTER"), "released");

        formatButton(add, addFrame, panel);
        panel.add(add);

        JRootPane rootPane = SwingUtilities.getRootPane(add);
        rootPane.setDefaultButton(add);

        add.addActionListener(e -> createExpense(datePicker, item, category, cost));
    }

    //MODIFIES: this
    //EFFECTS: sets the Cost text field
    protected JTextField setCost(Container panel) {
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(1, 2, 0, 0));
        container.setBackground(BG_COLOUR);
        container.setMaximumSize(new Dimension(BUTTON_WIDTH - 10, BUTTON_HEIGHT));

        JLabel costLabel = new JLabel("Cost:");
        costLabel.setFont(FONT);
        container.add(costLabel);
        JTextField cost = new JTextField("0.00");
        cost.setFont(FONT);
        container.add(cost);

        panel.add(container);
        return cost;
    }

    //MODIFIES: this
    //EFFECTS: sets the Item text area
    protected void setInput(Container panel, JTextArea item) {
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(1, 2, 0, 0));
        container.setBackground(BG_COLOUR);
        container.setMaximumSize(new Dimension(BUTTON_WIDTH - 10, BUTTON_HEIGHT));

        JLabel itemLabel = new JLabel("Item:");
        itemLabel.setFont(FONT);
        container.add(itemLabel);
        item.setFont(FONT);
        container.add(item);
        panel.add(container);
        panel.add(Box.createRigidArea(new Dimension(0, 60)));

    }

    //MODIFIES: this
    //EFFECTS: creates a drop down menu for the categories in the tracker panel
    protected JComboBox setCategory(Container panel) {
        JComboBox categoryBox = new JComboBox(categories);
        categoryBox.setMaximumSize(new Dimension(BUTTON_WIDTH - 10, BUTTON_HEIGHT));
        categoryBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        categoryBox.setFont(FONT);
        categoryBox.setBackground(Color.white);
        categoryBox.setSelectedIndex(0);

        categoryBox.addItemListener(
                event -> {
                    if (event.getStateChange() == ItemEvent.SELECTED) {
                        categoryBox.setSelectedItem(categoryBox.getSelectedIndex());
                    }
                }
        );
        
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(1, 2, 0, 0));
        container.setBackground(BG_COLOUR);
        container.setMaximumSize(new Dimension(BUTTON_WIDTH - 10, BUTTON_HEIGHT));

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(FONT);
        container.add(categoryLabel);
        container.add(categoryBox);
        panel.add(container);
        return categoryBox;
    }

    //MODIFIES: category and cost
    //EFFECTS: creates an instance of Expense and adds it to the expenses log
    protected void createExpense(DatePicker date, JTextArea item, JComboBox category, JTextField cost) {
        LocalDate localDate = date.getDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        String itemName = item.getText();
        Expense.Category category1 = parseCategory((String) category.getSelectedItem());
        double price = Double.parseDouble(cost.getText());

        Expense expense = new Expense(year, month, day, itemName, category1, price);
        expensesLog.addExpense(expense);
        try {
            writer.write(expensesLog.getExpenses(), FILE_NAME);
        } catch (IOException exception) {
            System.out.println("Something went wrong!!");
        }

        setDialog();

    }

    //EFFECTS: returns a dialog box confirming that the expense has been added
    protected void setDialog() {
        dialog = new JDialog(addFrame, "Message");
        Container pane = dialog.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(Box.createRigidArea(new Dimension(0, 60)));
        dialog.addKeyListener(this);
        dialog.setFocusable(true);

        JButton button = new JButton("OK!");
        button.setFont(FONT);
        button.setBackground(BG_COLOUR);
        button.setMaximumSize(new Dimension(200, 100));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(e -> dialog.dispose());


        JLabel message = new JLabel("Your expense has been added!", JLabel.CENTER);
        message.setFont(FONT);
        message.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialog.setBackground(BG_COLOUR);
        pane.add(message);
        pane.add(Box.createRigidArea(new Dimension(0, 60)));
        pane.add(button);

        dialog.setSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT * 4));
        dialog.setLocation(X_LOC + 275, Y_LOC + 350);
        dialog.setVisible(true);

    }


    //EFFECTS: closes the dialog box when ENTER key is pressed
    //source: https://stackoverflow.com/a/7000311
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            dialog.dispose();
        }
    }


}
