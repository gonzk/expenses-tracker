package ui.gui.panels.spinners;

import javax.swing.*;
import java.awt.*;

//The Spinner interface is utilized in the spinners seen on the monthly chart frame
public interface Spinner {
    static Font FONT = new Font("Century Gothic",Font.BOLD, 30);

    //EFFECTS: changes the state of the spinner
    public void changeState();

    //EFFECTS: sets the spinner to a SpinnerNumberModel
    public void setSpinner(SpinnerNumberModel spinnerNumberModel);

    //EFFECTS: returns the spinner
    public JSpinner getSpinner();
}
