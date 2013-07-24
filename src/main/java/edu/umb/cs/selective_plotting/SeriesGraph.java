
package edu.umb.cs.selective_plotting;

import edu.umb.cs.selective_plotting.data.DataFilter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.Rotation;

/**
 *
 * @author Vy Thao Nguyen
 */
public class SeriesGraph extends JFrame
{
    public static void showGraph(DataFilter f)
    {
        JFrame fr = new SeriesGraph(f);
        fr.pack();
        fr.setVisible(true);
    }

    public SeriesGraph(DataFilter f)
    {
        super("Sample chart");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // create a chart
        JFreeChart chart = createLineChart("", f.getXFieldName(), "", createXYDs(f));
        
        // put it into a panel
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new java.awt.Dimension(500, 270));
        
        setContentPane(panel);
    }

    private static XYDataset createXYDs(DataFilter filter)
    {
        int seriesCount = filter.yCount();
        List<TimeSeries> lines = new ArrayList<>(seriesCount);
        String yNames[] = filter.getYFieldNames();
        for (int n = 0; n < seriesCount; ++n)
            lines.add(new TimeSeries(yNames[n]));
        
        while (filter.hasNext())
        {
            FixedMillisecond sec = new FixedMillisecond((long)filter.nextX());
            
            for (TimeSeries s : lines)
                s.add(sec, filter.nextY());
        }

       TimeSeriesCollection ds = new TimeSeriesCollection();
       for (TimeSeries s : lines)
           ds.addSeries(s);
       
       return ds;
    }

    private static JFreeChart createLineChart(String title, String xLabel, String yLabel, XYDataset ds)
    {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(title, xLabel, yLabel, ds, true, true, true);
        
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
