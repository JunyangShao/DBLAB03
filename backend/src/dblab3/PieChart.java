package dblab3;

import javax.swing.JFrame;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;

public class PieChart extends JFrame {

    private static final long serialVersionUID = 1L;
    private DefaultPieDataset defaultdataset;
    private String chartTitle;
    public PieChart(String applicationTitle, String ichartTitle) {
        super(applicationTitle);
        // This will create the dataset
        defaultdataset = createDataset();
        chartTitle=ichartTitle;
    }
    /**
     * after finishing the data filling, create the PieChart
     */
    public void create_pie_chart(){
        // based on the dataset we create the chart

        JFreeChart chart = createChart(defaultdataset, chartTitle);
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        setContentPane(chartPanel);
    }

    /**
     * fill some data into the database
     */
    public void fill_data(String name,int num){
        defaultdataset.setValue(name, num);
    }

    /**
     * Creates a sample dataset
     */
    private  DefaultPieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();
        return result;

    }

    /**
     * Creates a chart
     */
    private JFreeChart createChart(PieDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createPieChart3D(
                title,                  // chart title
                dataset,                // data
                true,                   // include legend
                true,
                false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})",
                NumberFormat.getNumberInstance(),
                new DecimalFormat("0.00%")));
        plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}={1}({2})"));

        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;

    }
}

