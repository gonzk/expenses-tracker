package ui.gui.panels.tools;

import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.PieDataset;

import java.awt.*;
import java.util.List;

//source: https://javabeanz.wordpress.com/2007/08/06/creating-pie-charts-using-custom-colors-jfreechart/
//The PieRenderer class is responsible for setting the colours of each of the slices of the Chart
public class PieRenderer {
    private Color[] color;

    public PieRenderer(Color[] color) {
        this.color = color;
    }

    //MODIFIES: plot
    //EFFECTS: sets the color of the legend and pie slices
    public void setColor(PiePlot plot, PieDataset dataset) {
        List<Comparable> keys = dataset.getKeys();
        int num;

        for (int i = 0; i < keys.size(); i++) {
            num = i % this.color.length;
            plot.setSectionPaint(keys.get(i), this.color[num]);
        }
    }
}

