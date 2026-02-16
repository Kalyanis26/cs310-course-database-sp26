package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;

public class DAOUtility {
    
    public static final int TERMID_FA24 = 1;
    
    public static String getResultSetAsJson(ResultSet rs) {
        
        JsonArray resultArray = new JsonArray();
        
        if (rs == null) {
            return Jsoner.serialize(resultArray);
        }
        
        try {
            
            ResultSetMetaData metaData = rs.getMetaData();
            int totalColumns = metaData.getColumnCount();
            
            while (rs.next()) {
                
                JsonObject rowObject = new JsonObject();
                
                for (int col = 1; col <= totalColumns; col++) {
                    
                    String columnLabel = metaData.getColumnLabel(col);
                    Object value = rs.getObject(col);
                    
                    rowObject.put(columnLabel, value);
                }
                
                resultArray.add(rowObject);
            }
            
        }
        catch (SQLException e) {
            System.err.println("Error converting ResultSet to JSON: " + e.getMessage());
        }
        
        return Jsoner.serialize(resultArray);
    }
}

