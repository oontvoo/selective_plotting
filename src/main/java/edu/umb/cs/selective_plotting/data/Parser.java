package edu.umb.cs.selective_plotting.data;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author Vy Thao Nguyen
 */
public interface Parser 
{
    List<String> getFields();
    
    String[] nextLine();
    
    boolean hasNext();
    
    
    // TODO
    // set YAxes/XAxis, in case can't be parsed???
}
