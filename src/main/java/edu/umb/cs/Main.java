
package edu.umb.cs;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.general.SeriesDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.Rotation;

/**
 *
 * @author Vy Thao Nguyen
 */
public class Main extends JFrame
{
    public Main()
    {
        super("Sample chart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // create a chart
        JFreeChart chart = createLineChart(createXYDs());
        
        // put it into a panel
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new java.awt.Dimension(500, 270));
        
        setContentPane(panel);
    }

    public static void main (String args[])
    {
        JFrame fr = new Main();
        fr.pack();
        fr.setVisible(true);
        // -------- test excel
        
        
        
    }

    private static XYDataset createXYDs()
    {
        TimeSeries s1 = new TimeSeries("Percentage of oil over time");
        
        s1.add(new Month(1, 2001), 98.2);
        s1.add(new Month(2, 2001), 93.0);
        s1.add(new Month(3, 2001), 93.0);
        s1.add(new Month(4, 2001), 93.0);
        s1.add(new Month(5, 2001), 90.0);
        
        TimeSeries s2 = new TimeSeries("Percentage petroleum over time");
        
        s2.add(new Month(1, 2001), 23.2);
        s2.add(new Month(2, 2001), 15.0);
        s2.add(new Month(3, 2001), 10.0);
        s2.add(new Month(4, 2001), 3.0);
        s2.add(new Month(5, 2001), 20.0);
        
        TimeSeriesCollection ds = new TimeSeriesCollection();
        ds.addSeries(s1);
        ds.addSeries(s2);
        
        return ds;
    }
    private static JFreeChart createLineChart(XYDataset ds)
    {
        JFreeChart chart = ChartFactory.createTimeSeriesChart("Percentages", "Months", "Percentage", ds, true, true, true);
        
        // todo
        
        return chart;
    }
    
    private static PieDataset createPieDs()
    {
        DefaultPieDataset ret = new DefaultPieDataset();
        ret.setValue("Linux", 29);
        ret.setValue("Mac", 21);
        ret.setValue("Windows", 50);
        return ret;
    }
    
    private static JFreeChart createPie(PieDataset ds, String title)
    {
        JFreeChart chart = ChartFactory.createPieChart3D(title,
                                                       ds,
                                                       true,
                                                       true,
                                                       false);
        
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
    }
}
