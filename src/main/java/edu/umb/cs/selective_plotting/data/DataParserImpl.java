package edu.umb.cs.selective_plotting.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 *
 * @author Vy Thao Nguyen
 */
public class DataParserImpl implements Parser
{
    private static String HEADER_MARKER = "data header table";
    private static String DATA_MARKER = "date table"; // TODO: fix typo in data files
    
    private final File file;
    private final Map<String, List<Double>> table;
    private final Scanner input;
    private final List<String> headers;
    public DataParserImpl (File f) throws FileNotFoundException
    {
        file = f;
        input = new Scanner(f);
        table = new LinkedHashMap<>();
 
        headers = new ArrayList<String>();
        String line;
        while (input.hasNext())
        {
            line = input.nextLine();
            if (line.toLowerCase().contains(HEADER_MARKER))
            {
                line = input.nextLine();
                
                for (String col : line.split(","))
                {
                    String h = stripQuotes(col);
                    headers.add(h);
                    table.put(h, null);
                }
                break;
            }
        }
        
        while (input.hasNext())
        {
            line = input.nextLine();
            if (line.toLowerCase().contains(DATA_MARKER))
                break;
        }
    }

    @Override
    public List<String> getFields()
    {
        return headers;
    }
 
    private static String stripQuotes(String st)
    {
        StringBuilder bd = new StringBuilder();
        int count = 0;
        for (int n = 0, max = st.length(); n < max; ++n)
        {
            char ch = st.charAt(n);
            if (ch == '\'' && n < max && ch == '\'')
            {
                ++n;
                ++count;
            }
            else if (ch != '\"')
                bd.append(ch);
            else
                ++count;
            if (count == 2)
                break;
        }
        
        return bd.toString();
    }

    @Override
    public String[] nextLine()
    {
        String line = input.nextLine();
        while (input.hasNextLine() && (line = line.trim()).isEmpty())
            line = input.nextLine();
        return line.split(",");
    }

    @Override
    public boolean hasNext()
    {
        return input.hasNextLine();
    }
}
