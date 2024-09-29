package org.example.kindergarten_management_system_g4.dao.AccountDAO;


import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public List<User> getAllAccounts() throws SQLException {
        List<User> accounts = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT User_id, Role_id, Fullname, Email, Status from kindergartenmanagementsystem.user;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            User account = new User();
            account.setUserID(rs.getInt("User_id"));
            account.setRoleId(rs.getInt("Role_id"));
            account.setFullname(rs.getString("Fullname"));
            account.setEmail(rs.getString("Email"));
            account.setStatus(rs.getInt("Status"));
            accounts.add(account);
        }

        return accounts;
    }


    public static void main(String[] args) {
        AccountDAO accountDAO = new AccountDAO();
        try {
            List<User> accounts = accountDAO.getAllAccounts();
            // Kiểm tra nếu danh sách rỗng
            if (accounts.isEmpty()) {
                System.out.println("Danh sách tài khoản trống.");
            } else {
                System.out.println("Có " + accounts.size() + " tài khoản trong danh sách.");

                // Lặp và in ra từng tài khoản
                for (User account : accounts) {
                    System.out.println("User ID: " + account.getUserID());
                    System.out.println("Role ID: " + account.getRoleId());
                    System.out.println("Full Name: " + account.getFullname());
                    System.out.println("Email: " + account.getEmail());
                    System.out.println("Status: " + account.getStatus());
                    System.out.println("----------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
