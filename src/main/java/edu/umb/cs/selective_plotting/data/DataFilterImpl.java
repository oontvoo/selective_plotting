package edu.umb.cs.selective_plotting.data;

/**
 *
 * @author Vy Thao Nguyen
 */
public class DataFilterImpl implements DataFilter
{
    private int xIndex;
    private int yIndices[];
    private int fieldCount; // how many fields has been printed in this row
    private Parser parser;
    
    private String line[];
    private String xName;
    private String yNames[];

    public DataFilterImpl(Parser parser, int xIndex, int yIndices[], String xName, String yName[])
    {
        this.parser = parser;
        this.xIndex = xIndex;
        this.yIndices = yIndices;
        this.xName = xName;
        this.yNames = yName;
        fieldCount = 0;
        
    }
    
    @Override
    public int yCount()
    {
        return yIndices.length;
    }

    @Override
    public boolean hasNext()
    {
        return parser.hasNext();
    }

    @Override
    public double nextX()
    {
        line = parser.nextLine();
        fieldCount = 0;
        return Double.parseDouble(line[xIndex]);
    }

    @Override
    public double nextY()
    {
        return Double.parseDouble(line[yIndices[fieldCount++]]);
    }

    @Override
    public String getXFieldName()
    {
        return xName;
    }

    @Override
    public String[] getYFieldNames()
    {
        return yNames;
    }

}
