package edu.jsu.mcis.cs310.coursedb;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.Jsoner;
import edu.jsu.mcis.cs310.coursedb.dao.*;

public class Main {
    
    private static final String USERNAME = "nobody@jsu.edu";
    
    public static void main(String[] args) {
        
        // Create DAO Factory
        
        DAOFactory daoFactory = new DAOFactory("coursedb");
        
        // Test Connection
        
        if ( !daoFactory.isClosed() ) {
            
            System.out.println("Connected Successfully!");
            
            StudentDAO studentDao = daoFactory.getStudentDAO();
            int studentid = studentDao.find(USERNAME);
            System.out.println("Your Student ID is: " + studentid);
            
        }
        
    }
    
}