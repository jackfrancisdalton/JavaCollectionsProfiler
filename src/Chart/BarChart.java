package chart;

import helper.TestResult;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;

public class BarChart extends JFrame {

    private static final long serialVersionUID = 1L;

    public BarChart(String applicationTitle, String chartTitle, String xAxisLabel, String yaxisLabel, List<TestResult> testResults) {
        super(applicationTitle);

        JFreeChart chart = ChartFactory.createBarChart(
                chartTitle,
                xAxisLabel,
                yaxisLabel,
                createDataSet(testResults),
                PlotOrientation.VERTICAL,
                true, // include legend?
                true, // include tooltips?
                false // include URLs?
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }

    private CategoryDataset createDataSet(List<TestResult> testResults) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        for (TestResult testResult: testResults)
            dataSet.setValue((Number)testResult.getMin(), testResult.getSampleSize(), testResult.getLabel());

        return dataSet;
    }
}
