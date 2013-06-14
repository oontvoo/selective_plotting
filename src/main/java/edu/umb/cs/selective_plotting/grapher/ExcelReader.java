
package edu.umb.cs.selective_plotting.grapher;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author Vy Thao Nguyen
 */
public class ExcelReader extends JFrame
{
    private static final String TITLE = "Simple Plotting Utility";
    private static final FileFilter XLS = new FileNameExtensionFilter(".xls file", "xls");

    private static class FileWrapper
    {
        File f;
        boolean loaded = false;
    }
    
    private final FileWrapper fileWrapper = new FileWrapper();
    
    public ExcelReader() throws IOException, BiffException
    {
        super(TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        // north subPanel
        final JTextField filepath = new JTextField("");
        filepath.setEditable(false);
        filepath.setPreferredSize(new Dimension(180, 25));

        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(XLS);
        JPanel north = new JPanel();
        north.setBorder(BorderFactory.createEtchedBorder());
        
        // file chooser
        JButton openBtn = new JButton("Browse File");
        openBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int retVal = fc.showOpenDialog(ExcelReader.this);
                if (retVal == JFileChooser.APPROVE_OPTION)
                {
                    fileWrapper.f = fc.getSelectedFile();
                    filepath.setText(fileWrapper.f.getAbsolutePath());
                    fileWrapper.loaded = false;
                }
            }   
        });
        north.add(openBtn);
        
        // load
        final JList list = new JList();
        final DefaultListModel model = new DefaultListModel();
        list.setModel(model);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);
        
        JButton loadBtn = new JButton("Load");
        final Random r = new Random(); // TODO: temp
        loadBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (fileWrapper.f != null && !fileWrapper.loaded)
                {
                    filepath.setText("Analysing: " + fileWrapper.f.getName());
                    fileWrapper.loaded = true;

                    // populate the list with fields
                    model.removeAllElements();
                    // TODO: replace with real:
                    model.addElement("Field : " + r.nextInt());
                    model.addElement("Field : " + r.nextInt());
                    model.addElement("Field : " + r.nextInt());
                    model.addElement("Field : " + r.nextInt());
                    ExcelReader.this.repaint();
                }
            }   
        });
        north.add(loadBtn);
        north.add(filepath);

        mainPanel.add(north, BorderLayout.NORTH);
        
        // central panel
        JPanel central = new JPanel();
        central.setLayout(new BorderLayout());
        
        // scrollable pane displaying all the fields (chemical ions)
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(250, 80));
        central.add(listScroller, BorderLayout.CENTER);
        
        JPanel right = new JPanel();
        right.setLayout(new GridLayout(2, 0));
        JButton graphBtn = new JButton("Graph it!");
        JButton clearBtn = new JButton("Clear Selection");
        clearBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                list.clearSelection();
            }   
        });

        right.add(graphBtn);
        right.add(clearBtn);
        central.add(right, BorderLayout.EAST);

        mainPanel.add(central, BorderLayout.CENTER);
        
//        Workbook wb = Workbook.getWorkbook(new File(filename));
//        StringBuilder text = new StringBuilder("Number of sheets: " + wb.getNumberOfSheets());                        
//        int n = 0;
//        for (Sheet st : wb.getSheets())
//                text.append("\nSheet #").append(n++).append(": ").append(st.getName());
//        this.setContentPane(new JLabel(text.toString()));
//        
//        Sheet sheet = wb.getSheet(0);
        
        
        this.setContentPane(mainPanel);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
    
    public static void main(String args[]) throws IOException, BiffException
    {
        ExcelReader excelReader = new ExcelReader();
    }
}
