package ui.gui.panels.spinners;

import javax.swing.*;
import java.text.DateFormatSymbols;

//The MonthSpinner class implements the Spinner interface and its main purpose is to be the spinner that displays
//the month
public class MonthSpinner implements Spinner {
    private int month = 1;
    private JSpinner spinner;

    //EFFECTS: changes the month value to the current value of the spinner
    @Override
    public void changeState() {
        spinner.addChangeListener(e -> month = Integer.parseInt(spinner.getValue().toString()));
    }


    //source: https://stackoverflow.com/questions/1038570/how-can-i-convert-an-integer-to-localized-month-name-in-java
    //REQUIRES: month must be between 1-12
    //EFFECTS: returns the corresponding month to a given int
    public String convertMonth() {
        return new DateFormatSymbols().getMonths()[month - 1];
    }

    //EFFECTS: sets the spinner and stylizes it
    @Override
    public void setSpinner(SpinnerNumberModel spinnerNumberModel) {
        spinner = new JSpinner(spinnerNumberModel);
        spinner.setFont(FONT);
    }

    //EFFECTS: returns the spinner
    @Override
    public JSpinner getSpinner() {
        return spinner;
    }

    //EFFECTS: returns the month
    public int getMonth() {
        return month;
    }
}
