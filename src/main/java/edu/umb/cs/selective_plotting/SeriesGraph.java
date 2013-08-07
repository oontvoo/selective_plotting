
package edu.umb.cs.selective_plotting;

import edu.umb.cs.selective_plotting.data.DataFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
        
        // TODO: this is a waste of mem!
        // time step --> index
        Map<Long, Integer> idx = new HashMap<>();
        int n = 0;
        while (filter.hasNext())
        {
            long secL = (long)filter.nextX();
            FixedMillisecond sec = new FixedMillisecond(secL);
            idx.put(secL, n++);
            for (TimeSeries s : lines)
            {
                s.add(sec, filter.nextY());
            }
        }
        
        long startEnd[] = getStartEnd();
        boolean adjustRange = false;
        int startIdx = 0;
        int endIdx = idx.size() - 1;
        if (startEnd != null)
        {
            Integer startObj = idx.get(startEnd[0]);
            Integer endObj = idx.get(startEnd[1]);
            
            if (startObj != null)
                startIdx = startObj.intValue();
            if (endObj != null)
                endIdx = endObj.intValue();
           adjustRange = true;  
            // swap start and end, if reversed
        }
        
        
        TimeSeriesCollection ds = new TimeSeriesCollection();
        for (TimeSeries s : lines)
        {
            if (adjustRange)
            {
                if (startIdx != 0)
                    s.delete(0, startIdx - 1);
                if (endIdx < idx.size() - 1)
                    s.delete(endIdx + 1, idx.size() - 1);
            }
            ds.addSeries(s);
        }
       
        return ds;
    }

    private static long[] getStartEnd ()
    {
        // TODO: build a window with drop-down 
        //       prediction input field
        String str = JOptionPane.showInputDialog("Enter starting and ending point:");

        try
        {
            String startEnd[] = str.split("\\s*,\\s*");
            return new long[] {(long)Double.parseDouble(startEnd[0]),
                               (long)Double.parseDouble(startEnd[1])};
        }
        catch(Exception e)
        {
            return null;
        }
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
