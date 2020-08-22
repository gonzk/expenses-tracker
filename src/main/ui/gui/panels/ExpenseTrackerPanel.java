package ui.gui.panels;


import model.Expense;
import model.ExpensesLog;
import persistence.Read;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.List;

//The ExpenseTrackerPanel is a class that is displays the entire application
public class ExpenseTrackerPanel implements KeyListener  {
    protected static final int BUTTON_WIDTH = 950;
    protected static final int BUTTON_HEIGHT = 100;
    protected static final int WIDTH = 1500;
    protected static final int HEIGHT = 1150;
    protected static final int X_LOC = 600;
    protected static final int Y_LOC = 450;
    protected static final Color COLOR = new Color(89, 64, 92);
    protected static final Font FONT_TITLE = new Font("Century Gothic", Font.BOLD, 65);
    protected static final Font FONT = new Font("Century Gothic", Font.BOLD, 30);
    protected static final Color BG_COLOUR = new Color(215, 215, 243);

    protected JLabel title = new JLabel("Expenses Tracker", JLabel.CENTER);
    protected static final String FILE_NAME = "./data/logs.json";
    protected JButton addExpenses = new JButton("Add expenses");
    protected JButton log = new JButton("Log");
    protected JButton monthly = new JButton("Monthly Chart");
    protected JButton home;
    protected JFrame homeFrame = new JFrame("Expenses Tracker");
    protected ExpensesLog expensesLog = new ExpensesLog();

    public ExpenseTrackerPanel() {
        Read read = new Read();
        try {
            List<Expense> expenses = read.readExpenses("./data/logs.json");
            for (Expense e : expenses) {
                expensesLog.addExpense(e);
            }
        } catch (IOException exception) {
            setHome();
        }
        setHome();
    }

    //MODIFIES: this
    //EFFECTS: sets the frame of the home page
    protected void setHome() {
        homeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container panel = homeFrame.getContentPane();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        setFrame();
        setTitle(title, panel);
        panel.add(title);

        setButton(panel, addExpenses);
        setButton(panel, log);
        setButton(panel, monthly);
    }

    //MODIFIES: this, panel, button
    //EFFECTS: formats the button and add it to a panel
    protected void setButton(Container panel, JButton button) {
        formatButton(button, homeFrame, panel);
        panel.add(button);
        setActionListener(button);
    }

    //EFFECTS: when the button is pressed, a new frame opens and the homeFrame is disposed
    private void setActionListener(JButton button) {
        button.addActionListener(e -> {
            if (button.equals(monthly)) {
                MonthlyPanel monthlyPanel = new MonthlyPanel();
                monthlyPanel.seeMonthly();
            } else if (button.equals(log)) {
                LogPanel logPanel = new LogPanel();
                logPanel.seeLog();
            } else if (button.equals(addExpenses)) {
                AddPanel addPanel = new AddPanel();
                addPanel.addNew();
            }
            homeFrame.dispose();
        });
    }

    //EFFECTS: creates the Home Button
    protected void setHomeButton() {
        home = new JButton("Home");
    }

    //MODIFIES: button, frame, and panel;
    //EFFECTS: formats the given button
    protected void formatButton(JButton button, JFrame frame, Container panel) {
        button.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        Border border = BorderFactory.createLineBorder(BG_COLOUR, 1);
        button.setBorder(border);
        button.setMargin(new Insets(1,2,3,4));

        button.setFont(new Font("Century Gothic", Font.BOLD, 55));
        Color bg = new Color(201, 182, 228);
        Color fc = new Color(89, 64, 92);
        button.setBackground(bg);
        button.setForeground(fc);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 60)));
    }

    //EFFECTS: sets the home frame
    protected void setFrame() {
        homeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        homeFrame.getContentPane().setBackground(BG_COLOUR);
        homeFrame.setSize(WIDTH, HEIGHT);
        homeFrame.setLocation(X_LOC, Y_LOC);
        homeFrame.setVisible(true);
        homeFrame.setResizable(false);
    }

    //MODIFIES: title, panel, this
    //EFFECTS: formats the title and adds it to panel and tracker
    protected void setTitle(JLabel title, Container panel) {
        panel.add(Box.createRigidArea(new Dimension(0, 80)));
        title.setFont(FONT_TITLE);
        title.setForeground(COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    //MODIFIES: this
    //EFFECTS: sets the title
    protected void setLabel(Container panel, String title) {
        JLabel label = new JLabel(title, JLabel.CENTER);
        setTitle(label, panel);
        panel.add(label);
    }

    //EFFECTS: does nothing
    @Override
    public void keyTyped(KeyEvent e) {
    }

    //EFFECTS: does nothing
    @Override
    public void keyPressed(KeyEvent e) {

    }

    //EFFECTS: does nothing
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
