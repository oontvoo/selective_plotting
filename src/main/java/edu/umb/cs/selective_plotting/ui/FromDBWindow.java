
package edu.umb.cs.selective_plotting.ui;

import edu.umb.cs.selective_plotting.SeriesGraph;
import edu.umb.cs.selective_plotting.data.DataFilter;
import edu.umb.cs.selective_plotting.data.DataFilterImpl;
import edu.umb.cs.selective_plotting.data.DataParserImpl;
import edu.umb.cs.selective_plotting.data.Parser;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.read.biff.BiffException;

/**
 *
 * @author admin
 */
public class FromDBWindow extends JFrame
{
    
    private static final String COND = "/Users/admin/repo/selective_plotting/SampleData/tab/ws030c0cnd__00___13690000w0.tab";
    private static final String ISE = "/Users/admin/repo/selective_plotting/SampleData/tab/ws030c0ise__00___13690000w0.tab";
    private static final String CP = "/Users/admin/repo/selective_plotting/SampleData/tab/ws030c0cp_0_00___13690000w0.tab";

    private static final String TITLE = "Simple Plotting Utility";
    private static final FileFilter XLS = new FileNameExtensionFilter(".tab file", "tab");

    private static class Wrapper<T>
    {
        T object;
        boolean loaded = false;
    }
    
    private final MainWindow.Wrapper<File> fileWrapper = new MainWindow.Wrapper<File>();
    private final MainWindow.Wrapper<Parser> parserWrapper = new MainWindow.Wrapper <Parser>();
    
    private static class LoadFileListener implements ActionListener
    {
        MainWindow.Wrapper<File> fileWrapper;
        String p;
        JButton load;
        public LoadFileListener(String path, MainWindow.Wrapper wrap, JButton l)
        {
            fileWrapper = wrap;
            p = path;
            load = l;
            
            
        }
        @Override
        public void actionPerformed(ActionEvent e)
        {
            fileWrapper.object = new File(p);
            
            // load it
            load.doClick();
        }
    }
    public FromDBWindow() throws IOException, BiffException
    {
        super(TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        // north sub panel : 3 visable buttons
        final JButton loadBtn = new JButton("Load");
        
        JButton cndBtn = new JButton("Conductivities");
        cndBtn.addActionListener(new LoadFileListener(COND, fileWrapper, loadBtn));
        
        JButton ise = new JButton("Concentrations");
        ise.addActionListener(new LoadFileListener(ISE, fileWrapper, loadBtn));
        
        JButton cp = new JButton("CP");
        cp.addActionListener(new LoadFileListener(CP, fileWrapper, loadBtn));
        
//        // north subPanel
//        final JTextField filepath = new JTextField("");
//        filepath.setEditable(false);
//        filepath.setPreferredSize(new Dimension(180, 25));
//
//        final JFileChooser fc = new JFileChooser();
//        fc.setFileFilter(XLS);
        
        JPanel north = new JPanel();
        north.setBorder(BorderFactory.createEtchedBorder());
        
        north.add(cndBtn);
        north.add(ise);
        north.add(cp);
        
//        // file chooser
//        JButton openBtn = new JButton("Browse File");
//        openBtn.addActionListener(new ActionListener()
//        {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                int retVal = fc.showOpenDialog(FromDBWindow.this);
//                if (retVal == JFileChooser.APPROVE_OPTION)
//                {
//                    fileWrapper.object = fc.getSelectedFile();
//                    filepath.setText(fileWrapper.object.getAbsolutePath());
//                    fileWrapper.loaded = false;
//                }
//            }   
//        });
//        north.add(openBtn);
        
        // load
        // TODO: add action listener to remove field selected as xAxis
        //       from yAxisFields
        final JList yAxisFields = new JList();
        final DefaultListModel yAxisModel = new DefaultListModel();
        yAxisFields.setPrototypeCellValue("Select fields to plot");
        yAxisFields.setModel(yAxisModel);
        yAxisFields.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        yAxisFields.setLayoutOrientation(JList.VERTICAL_WRAP);
        
        final JComboBox xAxisField = new JComboBox();
        xAxisField.setPrototypeDisplayValue("Select x-Axis");
        xAxisField.setEditable(false);
        
        // graph and clear selection buttons
        final JButton graphBtn = new JButton("Graph it!");
        graphBtn.setEnabled(false);
        final JButton clearBtn = new JButton("Clear Selection");
        clearBtn.setEnabled(false);
        
        
        loadBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //if (fileWrapper.object != null && !fileWrapper.loaded)
                {
                    try
                    {
                        parserWrapper.object = new DataParserImpl(fileWrapper.object);
                    }
                    catch (FileNotFoundException ex)
                    {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(FromDBWindow.this, "Error reading data file! Please check the log", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    //filepath.setText("Analysing: " + fileWrapper.object.getName());
                    fileWrapper.loaded = true;

                    // populate the list with fields
                    yAxisModel.removeAllElements();
                    xAxisField.removeAllItems();
                    
                    final Collection<String> cols = parserWrapper.object.getFields(); 
                    for (String col : cols)
                        xAxisField.addItem(col);
                    
                    for (String col : cols)
                        yAxisModel.addElement(col);

                    graphBtn.setEnabled(true);
                    clearBtn.setEnabled(true);
                    FromDBWindow.this.repaint();
                }
            }   
        });
       // north.add(filepath);
        //north.add(loadBtn);

        mainPanel.add(north, BorderLayout.NORTH);
        
        // central panel
        JPanel central = new JPanel();
        central.setLayout(new BorderLayout());
        
        // scrollable pane displaying all the fields (chemical ions)
        JScrollPane yAxisPane = new JScrollPane(yAxisFields);
        yAxisPane.setPreferredSize(new Dimension(250, 80));
        
        // pane for x-axis (ie., time?)
        JPanel xAxisPane = new JPanel();
        xAxisPane.setPreferredSize(new Dimension(250, 20));
        xAxisPane.add(xAxisField);
        
        // x-axis and y-axis pane
        JPanel axes = new JPanel();
        axes.setLayout(new GridLayout(0, 2));
        axes.add(xAxisPane);
        axes.add(yAxisPane);
        
        central.add(axes, BorderLayout.CENTER);
        
        JPanel right = new JPanel();
        right.setLayout(new GridLayout(2, 0));

        graphBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                List<String> names = yAxisFields.getSelectedValuesList();
                String yNames[] = new String[names.size()];
                yNames = names.toArray(yNames);

                DataFilter filter = new DataFilterImpl(parserWrapper.object,
                                                       xAxisField.getSelectedIndex(),
                                                       yAxisFields.getSelectedIndices(),
                                                       xAxisField.getSelectedItem().toString(),
                                                       yNames);
                
                SeriesGraph.showGraph(filter);

                // TODO: remove this (this fix the bug of  not know how to selectively remove serires from a created dataset (which has multiple serires)
                loadBtn.doClick(); 
            }   
        });

        clearBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                yAxisFields.clearSelection();
            }   
        });

        right.add(graphBtn);
        right.add(clearBtn);
        central.add(right, BorderLayout.EAST);

        mainPanel.add(central, BorderLayout.CENTER);

        this.setContentPane(mainPanel);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
}
