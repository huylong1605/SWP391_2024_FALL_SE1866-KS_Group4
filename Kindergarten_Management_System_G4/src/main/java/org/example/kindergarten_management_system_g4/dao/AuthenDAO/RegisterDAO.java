package org.example.kindergarten_management_system_g4.dao.AuthenDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterDAO {
    private static final String CHECK_PHONE = "SELECT * FROM user WHERE phoneNumber=?";
    private static final String CHECK_EMAIL = "SELECT * FROM user WHERE email=?";
    private static final String INSERT_USER = "INSERT INTO user(Fullname, email, password, gender, phoneNumber, address) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String INSERT_USER_LOGIN_GG = "INSERT INTO user(Fullname, email) VALUES (?, ?)";


    public boolean checkPhone(String phone) throws ClassNotFoundException {
        boolean exists = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(CHECK_PHONE);
            preparedStatement.setString(1, phone);
            rs = preparedStatement.executeQuery();

            exists = rs.next(); // Kiểm tra xem có bản ghi nào không
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng ResultSet
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Đóng PreparedStatement
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Đóng Connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return exists; // Trả về kết quả kiểm tra
    }


    public boolean checkEmail(String email) throws ClassNotFoundException {
        boolean exists = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(CHECK_EMAIL);
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();

            exists = rs.next(); // Kiểm tra xem có bản ghi nào không
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            // Đóng ResultSet
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Đóng PreparedStatement
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Đóng Connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return exists; // Trả về kết quả kiểm tra
    }


    public boolean insertUser(User u) throws ClassNotFoundException {
        boolean isInserted = false; // Khởi tạo mặc định là false
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_USER);

            preparedStatement.setString(1, u.getFullname());
            preparedStatement.setString(2, u.getEmail());
            preparedStatement.setString(3, u.getPassword());
            preparedStatement.setInt(4, u.getGender());
            preparedStatement.setString(5, u.getPhoneNumber());
            preparedStatement.setString(6, u.getAddress());

            System.out.println(preparedStatement);

            int result = preparedStatement.executeUpdate(); // Thực thi câu lệnh

            if (result > 0) {
                // Nếu số dòng bị ảnh hưởng > 0, thêm thành công
                isInserted = true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            // Đóng PreparedStatement
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Đóng Connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isInserted; // Trả về kết quả kiểm tra
    }


    public boolean insertUserLoginGG(String mail, String name) throws ClassNotFoundException {
        boolean isInserted = false; // Khởi tạo mặc định là false
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_USER_LOGIN_GG);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, mail); // Sửa lỗi từ name thành mail

            System.out.println(preparedStatement);

            int result = preparedStatement.executeUpdate(); // Thực thi câu lệnh

            if (result > 0) {
                // Nếu số dòng bị ảnh hưởng > 0, thêm thành công
                isInserted = true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            // Đóng PreparedStatement
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Đóng Connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isInserted; // Trả về kết quả kiểm tra
    }


  /*  public User getUserByMail(String email) throws ClassNotFoundException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(check_email)) {

            preparedStatement.setString(1, email);


            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.s
                user.setFullName(rs.getString("full_name"));
                // Các thuộc tính khác nếu cần
                return user;
            }
            return null;
        } catch (SQLException e) {
            printSQLException(e);
        }

    }*/


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
