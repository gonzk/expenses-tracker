package ui.gui.panels;

import model.ExpensesLog;
import ui.gui.panels.spinners.MonthSpinner;
import ui.gui.panels.spinners.YearSpinner;
import ui.gui.panels.tools.Chart;

import javax.swing.*;
import java.awt.*;

// The MonthlyPanel sets the monthly piechart frame of the expenses tracker panel
public class MonthlyPanel extends ExpenseTrackerPanel {
    private JFrame monthlyFrame = new JFrame("Expenses Tracker");
    private MonthSpinner monthSpinner = new MonthSpinner();
    private YearSpinner yearSpinner = new YearSpinner();
    private Container horizontal = new JPanel();


    //MODIFIES: this
    //EFFECTS: allows the user to see a pie chart for a given month and year
    protected void seeMonthly() {
        homeFrame.dispose();
        setFrame();

        Container panel = monthlyFrame.getContentPane();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        setLabel(panel, "MONTHLY EXPENSES");

        monthSpinner.setSpinner(new SpinnerNumberModel(1,1,12,1));
        yearSpinner.setSpinner(new SpinnerNumberModel(2020,0,null,1));

        setSpinners();

        JButton submit = new JButton("SUBMIT");
        formatButton(submit, monthlyFrame, panel);
        submit.addActionListener(e -> {
            new Chart("Monthly Expenses for "
                    + monthSpinner.convertMonth() + " " + yearSpinner.getYear(), monthSpinner.getMonth(),
                    yearSpinner.getYear());
            monthlyFrame.dispose();
        });


        InputMap map = submit.getInputMap();
        map.put(KeyStroke.getKeyStroke("ENTER"), "none");
        map.put(KeyStroke.getKeyStroke("released ENTER"), "released");

        JRootPane rootPane = SwingUtilities.getRootPane(submit);
        rootPane.setDefaultButton(submit);

        setHomeButton();
    }

    //EFFECTS: sets the frame for the monthly chart
    @Override
    protected void setFrame() {
        monthlyFrame.getContentPane().setBackground(BG_COLOUR);
        monthlyFrame.setSize(WIDTH, HEIGHT);
        monthlyFrame.setLocation(X_LOC, Y_LOC);
        monthlyFrame.setVisible(true);
        monthlyFrame.setResizable(false);

        monthlyFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        monthlyFrame.setLayout(new GridLayout(3, 1, 5, 5));
    }

    //MODIFIES: this
    //EFFECTS: returns to the home page from the monthly chart frame
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

    //EFFECTS: places the Home Button on the monthly chart frame
    @Override
    protected void setHomeButton() {
        super.setHomeButton();
        formatButton(home, monthlyFrame, monthlyFrame.getContentPane());
        monthlyFrame.getContentPane().add(home);
        home.addActionListener(e -> {
            homeFrame.setVisible(true);
            monthlyFrame.dispose();
        });

    }

    //EFFECTS: places the month and year spinners on the monthly chart frame
    protected void setSpinners() {
        Container panel = new JPanel();
        panel.add(Box.createRigidArea(new Dimension(0, 60)));
        monthlyFrame.add(panel);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_COLOUR);

        horizontal.setLayout(new GridLayout(2, 2, 0, 40));
        horizontal.setBackground(BG_COLOUR);
        panel.add(horizontal);
        panel.setMaximumSize(new Dimension(BUTTON_WIDTH / 2, BUTTON_HEIGHT * 4));

        formatLabel(new JLabel("Select a month: "));

        horizontal.add(monthSpinner.getSpinner());

        panel.add(Box.createRigidArea(new Dimension(0, 60)));

        formatLabel(new JLabel("Select a year: "));

        horizontal.add(yearSpinner.getSpinner());

        monthSpinner.changeState();
        yearSpinner.changeState();
    }

    //MODIFIES: label
    //EFFECTS: changes the font of the label and add it to a container
    public void formatLabel(JLabel label) {
        label.setFont(FONT);
        horizontal.add(label);
    }

    //EFFECTS: returns the expenses log
    public ExpensesLog getLog() {
        return this.expensesLog;
    }


}
