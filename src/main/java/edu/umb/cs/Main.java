package edu.umb.cs;

import edu.umb.cs.selective_plotting.ui.FromDBWindow;
import edu.umb.cs.selective_plotting.ui.FromFilesWindow;

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
            if (args != null && args.length == 1 && "-db".equals(args[0]))
            {
                FromDBWindow win = new FromDBWindow();
            }
            else
            {
                FromFilesWindow win = new FromFilesWindow();
            }
        }
        catch (Exception e)
        {
            javax.swing.JOptionPane.showMessageDialog(null, "Error starting program. Please see stack trace");
            e.printStackTrace();
        }
    }
}
