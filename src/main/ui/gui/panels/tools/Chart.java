package ui.gui.panels.tools;


import model.Expense;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.VerticalAlignment;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import ui.gui.panels.MonthlyPanel;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

// The Chart classes generates a PieChart of expenses for a given month and year
public class Chart  {
    protected static final int WIDTH = 1500;
    protected static final int HEIGHT = 1150;
    protected static final int X_LOC = 600;
    protected static final int Y_LOC = 450;
    protected static final Color COLOR = new Color(89, 64, 92);
    protected static final Font FONT = new Font("Century Gothic", Font.BOLD, 30);
    protected static final Color BG_COLOUR = new Color(215, 215, 243);

    private JFrame chartFrame;
    private int month;
    private int year;
    private MonthlyPanel panel = new MonthlyPanel();

    public Chart(String title, int month, int year) {
        this.month = month;
        this.year = year;

        setFrame();
        // Create dataset
        PieDataset dataset = createDataset();

        // Create chart
        JFreeChart chart = ChartFactory.createPieChart(
                title, dataset,true,true, false);

        PiePlot plot = (PiePlot) chart.getPlot();
        changeFont(chart);

        //Format Label
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "{0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        plot.setLabelGenerator(labelGenerator);
        plot.setLabelFont(new Font("Century Gothic", Font.BOLD, 15));
        plot.setSectionOutlinesVisible(false);
        plot.setLabelBackgroundPaint(new Color(201, 182, 228));

        Color[] colors = getColors();
        PieRenderer renderer = new PieRenderer(colors);
        renderer.setColor(plot, dataset);

        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        chart.getPlot().setBackgroundPaint(BG_COLOUR);
        chartFrame.setContentPane(panel);
    }

    //EFFECTS: sets the frame of the chart
    protected void setFrame() {
        chartFrame = new JFrame("Expenses Tracker");
        chartFrame.getContentPane().setBackground(BG_COLOUR);
        chartFrame.setSize(WIDTH, HEIGHT);
        chartFrame.setLocation(X_LOC, Y_LOC);
        chartFrame.setVisible(true);
        chartFrame.setResizable(false);
    }

    //source: https://stackoverflow.com/questions/6933187/customize-subtitle-position-jfreechart
    //MODIFIES: chart
    //EFFECTS: changes the font of the text in the chart
    private void changeFont(JFreeChart chart) {
        chart.getTitle().setFont(FONT);
        chart.getTitle().setPaint(COLOR);
        chart.getLegend().setItemFont(new Font("Century Gothic", Font.BOLD, 15));
        chart.getLegend().setItemPaint(COLOR);
        chart.addSubtitle(new TextTitle("Total expenses: $"
                + panel.getLog().findMonthlyTotalExpenses(month, year),
                FONT, COLOR, RectangleEdge.BOTTOM, HorizontalAlignment.CENTER, VerticalAlignment.TOP,
                new RectangleInsets(10,10,10,10)));
    }

    //source: https://www.boraji.com/jfreechart-pie-chart-example
    //EFFECTS: creates the dataset needed to create the Chart
    private PieDataset createDataset() {

        DefaultPieDataset dataset = new DefaultPieDataset();

        dataset.setValue("MEAL", panel.getLog().findMonthlyTotalExpensesCategory(month, year, Expense.Category.MEAL));

        dataset.setValue("GROCERIES",
                panel.getLog().findMonthlyTotalExpensesCategory(month, year, Expense.Category.GROCERIES));

        dataset.setValue("TRAVEL",
                panel.getLog().findMonthlyTotalExpensesCategory(month, year, Expense.Category.TRAVEL));

        dataset.setValue("ENTERTAINMENT",
                panel.getLog().findMonthlyTotalExpensesCategory(month, year, Expense.Category.ENTERTAINMENT));

        dataset.setValue("BILLS", panel.getLog().findMonthlyTotalExpensesCategory(month, year, Expense.Category.BILLS));

        dataset.setValue("SHOPPING",
                panel.getLog().findMonthlyTotalExpensesCategory(month, year, Expense.Category.SHOPPING));

        dataset.setValue("TECH", panel.getLog().findMonthlyTotalExpensesCategory(month, year, Expense.Category.TECH));

        dataset.setValue("TRANSPORTATION",
                panel.getLog().findMonthlyTotalExpensesCategory(month, year, Expense.Category.TRANSPORTATION));

        dataset.setValue("OTHER", panel.getLog().findMonthlyTotalExpensesCategory(month, year, Expense.Category.OTHER));
        return dataset;
    }

    //EFFECTS: returns the colors used for the chart
    private Color[] getColors() {
        return new Color[]{new Color(193, 236, 193), new Color(193, 142, 248),
                new Color(145, 172, 243), new Color(246, 222, 246),
                new Color(239, 187, 207), new Color(231, 125, 167),
                new Color(98, 229, 188), new Color(45, 206, 117),
                new Color(255,182,182)};
    }


}

