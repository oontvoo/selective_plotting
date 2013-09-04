
package edu.umb.cs.selective_plotting.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.List;

/**
 * This class is used to populate an existing wcl_data database with data from files
 * (See the attached scripts directory for the schema)
 * 
 * TODO:
 * @author Vy Thao Nguyen
 */
public class FromDB
{
    private static final String DEFAULT_DB = "wcl_data";
    private static final String user = "lionv";
    private static final String password = "lionv";
    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {
        Connection con = getConnect();
        Statement stm = con.createStatement();
        
        ResultSet rs = stm.executeQuery("SELECT * FROM  wcl_data.timesteps");
        
        while (rs.next())
        {
            System.out.println(rs.getTimestamp(1));
        }
    }
    
    static void loadConduct()
    {
        //TODO
    }

    static void loadConcentration() throws ClassNotFoundException, SQLException, FileNotFoundException
    {
        ///Users/admin/repo/selective_plotting/Sample Data/tab
        Connection con = getConnect();
        Statement stm = con.createStatement();
        
        Parser p = new DataParserImpl(new File("/Users/admin/repo/selective_plotting/SampleData/tab"));
        List<String> fields = p.getFields();
        while (p.hasNext())
        {
            String line[] = p.nextLine();
            //TODO
            //ResultSet rs = stm.execute("INSERT INTO wcl_data.concentrations(ion_name, time, percentage");
        }
    }
    static Connection getConnect() throws ClassNotFoundException, SQLException
    {
         Class.forName("com.mysql.jdbc.Driver");
         Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/" + DEFAULT_DB
                                                                + "?user=lionv&password=lionv");
         return connection;
    }
}
