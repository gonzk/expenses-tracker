package ui.gui.panels.spinners;

import javax.swing.*;

//The YearSpinner class implements the Spinner interface and its main purpose is to be the spinner that displays
//the year
public class YearSpinner implements Spinner {
    private int year = 2020;
    private JSpinner spinner;

    //EFFECTS: changes the year value to the current value of the spinner
    @Override
    public void changeState() {
        spinner.addChangeListener(e -> year = Integer.parseInt(spinner.getValue().toString()));

    }

    //EFFECTS: sets the spinner and stylizes it
    @Override
    //source: https://stackoverflow.com/a/12453923
    public void setSpinner(SpinnerNumberModel spinnerNumberModel) {
        spinner = new JSpinner(spinnerNumberModel);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "#");
        spinner.setEditor(editor);
        spinner.setFont(FONT);
    }

    //EFFECTS: returns the spinner
    public JSpinner getSpinner() {
        return spinner;
    }

    //EFFECTS: returns the year
    public int getYear() {
        return year;
    }
}
