package org.example.kindergarten_management_system_g4.dao.AuthenDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgetPasswordDAO {

    private static final String GET_EMAIL = "SELECT email FROM user WHERE email = ?";
    private static final String INSERT_CODE = "Update user set Code = ? WHERE email = ?";
    private static final String GET_CODE = "SELECT code FROM user WHERE email = ?";
    private static final String UPDATE_PASS_USER = "UPDATE user SET password = ? WHERE email = ?";

    public String findMail(String email) throws ClassNotFoundException {

        String Mail = "";
        try (Connection connection = DBConnection.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(GET_EMAIL)) {


            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Mail = resultSet.getString(1);  // Corrected column index
            }


        } catch (SQLException e) {
            printSQLException(e);
        }
        return Mail;
    }

    public int insertCode(String code, String email) throws ClassNotFoundException {
        int result = 0;
        Connection connection = null; // Khai báo biến cục bộ cho Connection
        PreparedStatement preparedStatement = null; // Khai báo biến cục bộ cho PreparedStatement

        try {
            connection = DBConnection.getConnection(); // Mở kết nối
            preparedStatement = connection.prepareStatement(INSERT_CODE); // Tạo PreparedStatement

            // Thiết lập các giá trị cho câu lệnh SQL
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, email);

            System.out.println(preparedStatement); // Debug câu lệnh SQL
            result = preparedStatement.executeUpdate(); // Thực hiện lệnh INSERT

        } catch (SQLException e) {
            printSQLException(e); // Xử lý ngoại lệ liên quan đến SQL
        } finally {
            // Đóng tài nguyên trong khối finally
            try {
                if (preparedStatement != null) {
                    preparedStatement.close(); // Đóng PreparedStatement
                }
                if (connection != null) {
                    connection.close(); // Đóng Connection
                }
            } catch (SQLException ex) {
                printSQLException(ex); // Xử lý ngoại lệ khi đóng tài nguyên
            }
        }

        return result; // Trả về số bản ghi được cập nhật
    }


    public String findCode(String email) throws ClassNotFoundException {
        String code = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(GET_CODE);

            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                code = resultSet.getString(1);  // Corrected column index
            }

        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            // Đóng ResultSet
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    printSQLException(e);
                }
            }
            // Đóng PreparedStatement
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    printSQLException(e);
                }
            }
            // Đóng Connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    printSQLException(e);
                }
            }
        }
        return code;
    }


    public int newPass(String email, String newPass) throws ClassNotFoundException {
        int result = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_PASS_USER);

            preparedStatement.setString(1, newPass);
            preparedStatement.setString(2, email);

            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            // Đóng PreparedStatement
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    printSQLException(e);
                }
            }
            // Đóng Connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    printSQLException(e);
                }
            }
        }
        return result;
    }


    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
