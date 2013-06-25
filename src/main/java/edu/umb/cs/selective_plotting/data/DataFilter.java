package edu.umb.cs.selective_plotting.data;

/**
 *
 * @author Vy Thao Nguyen
 */
public interface DataFilter 
{
    String getXFieldName();
    String[] getYFieldNames();
    
    int yCount();

    boolean hasNext();

    double nextX();
    
    double nextY();
}
