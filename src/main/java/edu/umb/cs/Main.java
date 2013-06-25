package edu.umb.cs;

import edu.umb.cs.selective_plotting.ui.MainWindow;

/**
 *
 * @author Vy Thao Nguyen
 */
public class Main 
{
    public static void main(String args[])
    {
        try
        {
            MainWindow excelReader = new MainWindow();
        }
        catch (Exception e)
        {
            javax.swing.JOptionPane.showMessageDialog(null, "Error starting program. Please see stack trace");
            e.printStackTrace();
        }
    }
}
