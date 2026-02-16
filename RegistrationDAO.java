package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegistrationDAO {

    private final DAOFactory daoFactory;

    private static final String SQL_INSERT =
        "INSERT INTO registration (studentid, termid, crn) VALUES (?, ?, ?)";

    private static final String SQL_DELETE_ONE =
        "DELETE FROM registration WHERE studentid = ? AND termid = ? AND crn = ?";

    private static final String SQL_DELETE_ALL =
        "DELETE FROM registration WHERE studentid = ? AND termid = ?";

    private static final String SQL_LIST =
        "SELECT studentid, termid, crn FROM registration " +
        "WHERE studentid = ? AND termid = ? ORDER BY crn";

    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public boolean create(int studentid, int termid, int crn) {

        try {

            Connection connection = daoFactory.getConnection();

            if (connection != null && connection.isValid(2)) {

                try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {

                    statement.setInt(1, studentid);
                    statement.setInt(2, termid);
                    statement.setInt(3, crn);

                    return statement.executeUpdate() > 0;
                }
            }

        }
        catch (Exception e) {
            System.err.println("Registration create failed: " + e.getMessage());
        }

        return false;
    }

    public boolean delete(int studentid, int termid, int crn) {

        try {

            Connection connection = daoFactory.getConnection();

            if (connection != null && connection.isValid(2)) {

                try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ONE)) {

                    statement.setInt(1, studentid);
                    statement.setInt(2, termid);
                    statement.setInt(3, crn);

                    return statement.executeUpdate() > 0;
                }
            }

        }
        catch (Exception e) {
            System.err.println("Registration delete (single) failed: " + e.getMessage());
        }

        return false;
    }

    public boolean delete(int studentid, int termid) {

        try {

            Connection connection = daoFactory.getConnection();

            if (connection != null && connection.isValid(2)) {

                try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ALL)) {

                    statement.setInt(1, studentid);
                    statement.setInt(2, termid);

                    return statement.executeUpdate() > 0;
                }
            }

        }
        catch (Exception e) {
            System.err.println("Registration delete (all) failed: " + e.getMessage());
        }

        return false;
    }

    public String list(int studentid, int termid) {

        String jsonOutput = "[]";

        try {

            Connection connection = daoFactory.getConnection();

            if (connection != null && connection.isValid(2)) {

                try (PreparedStatement statement = connection.prepareStatement(SQL_LIST)) {

                    statement.setInt(1, studentid);
                    statement.setInt(2, termid);

                    try (ResultSet resultSet = statement.executeQuery()) {

                        jsonOutput = DAOUtility.getResultSetAsJson(resultSet);
                    }
                }
            }

        }
        catch (Exception e) {
            System.err.println("Registration list failed: " + e.getMessage());
        }

        return jsonOutput;
    }
}
