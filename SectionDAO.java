package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SectionDAO {

    private static final String SQL_FIND =
        "SELECT * FROM section WHERE subjectid = ? AND num = ? AND termid = ? ORDER BY crn";

    private final DAOFactory daoFactory;

    SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public String find(int termid, String subjectid, String num) {

        String jsonResult = "[]";

        try {

            Connection connection = daoFactory.getConnection();

            if (connection != null && connection.isValid(2)) {

                try (PreparedStatement statement = connection.prepareStatement(SQL_FIND)) {

                    statement.setString(1, subjectid);
                    statement.setString(2, num);
                    statement.setInt(3, termid);

                    try (ResultSet resultSet = statement.executeQuery()) {

                        jsonResult = DAOUtility.getResultSetAsJson(resultSet);

                    }
                }
            }

        }
        catch (Exception e) {
            System.err.println("Section search failed: " + e.getMessage());
        }

        return jsonResult;
    }
}
