package org.example.kindergarten_management_system_g4.dao.TermDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.Term;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TermDAO {
    private Connection connection;

    public TermDAO() {
        try {
            this.connection = DBConnection.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates or inserts a new term into the database.
     * @param term the Term object containing information about the term to be added
     * @return true if the insertion is successful, otherwise false
     */
    public boolean createTerm(Term term) {
        String sql = "INSERT INTO Term (Term_name, Start_Date, End_Date, Year) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, term.getTermName().trim());
            statement.setDate(2, new java.sql.Date(term.getStartDate().getTime()));
            statement.setDate(3, new java.sql.Date(term.getEndDate().getTime()));
            statement.setInt(4, term.getYear());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Updates the information of an existing term in the database.
     * @param term the Term object containing updated information of the term
     * @return true if the update is successful, otherwise false
     */
    public boolean updateTerm(Term term) {
        String sql = "UPDATE Term SET Term_name = ?, Start_Date = ?, End_Date = ?, Year = ? WHERE Term_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, term.getTermName().trim());
            statement.setDate(2, new java.sql.Date(term.getStartDate().getTime()));
            statement.setDate(3, new java.sql.Date(term.getEndDate().getTime()));
            statement.setInt(4, term.getYear());
            statement.setInt(5, term.getTermId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Xóa một kỳ học khỏi cơ sở dữ liệu dựa trên ID.
     *
     * @param termId ID của kỳ học cần xóa
     * @return true nếu xóa thành công, ngược lại false
     */
    public boolean deleteTerm(int termId) {
        String sql = "DELETE FROM Term WHERE Term_ID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, termId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    /**
     * Retrieves the term information based on its ID.
     * @param termId the ID of the term to retrieve
     * @return the Term object if found, otherwise null
     */
    public Term getTermById(int termId) {
        String sql = "SELECT * FROM Term WHERE Term_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, termId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Term(
                        resultSet.getInt("Term_ID"),
                        resultSet.getString("Term_name"),
                        resultSet.getDate("Start_Date"),
                        resultSet.getDate("End_Date"),
                        resultSet.getInt("Year")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves a list of all terms from the database.
     * @return a list of Term objects
     */
    public List<Term> getAllTerms() {
        List<Term> terms = new ArrayList<>();
        String sql = "SELECT * FROM Term order by start_Date ";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Term term = new Term(
                        resultSet.getInt("Term_ID"),
                        resultSet.getString("Term_name"),
                        resultSet.getDate("Start_Date"),
                        resultSet.getDate("End_Date"),
                        resultSet.getInt("Year")
                );
                terms.add(term);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return terms;
    }

    /**
     * Filters the list of terms based on the given criteria.
     * @param termName the name of the term
     * @param startDate the start date of the term
     * @param endDate the end date of the term
     * @param year the year of the term
     * @return a list of Term objects that match the criteria
     */
    public List<Term> filterTerms(String termName, String startDate, String endDate, String year) {
        List<Term> termList = new ArrayList<>();

        // Tạo câu truy vấn SQL dựa trên các tiêu chí
        String sql = "SELECT * FROM Term WHERE 1=1";
        if (termName != null && !termName.isEmpty()) {
            sql += " AND Term_name LIKE ?";
        }
        if (startDate != null && !startDate.isEmpty()) {
            sql += " AND Start_Date >= ?";
        }
        if (endDate != null && !endDate.isEmpty()) {
            sql += " AND End_Date <= ?";
        }
        if (year != null && !year.isEmpty()) {
            sql += " AND Year = ?";
        }

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            int paramIndex = 1;

            if (termName != null && !termName.isEmpty()) {
                statement.setString(paramIndex++, "%" + termName + "%");
            }
            if (startDate != null && !startDate.isEmpty()) {
                statement.setDate(paramIndex++, java.sql.Date.valueOf(startDate));
            }
            if (endDate != null && !endDate.isEmpty()) {
                statement.setDate(paramIndex++, java.sql.Date.valueOf(endDate));
            }
            if (year != null && !year.isEmpty()) {
                statement.setInt(paramIndex++, Integer.parseInt(year));
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Term term = new Term(
                        resultSet.getInt("Term_ID"),
                        resultSet.getString("Term_name"),
                        resultSet.getDate("Start_Date"),
                        resultSet.getDate("End_Date"),
                        resultSet.getInt("Year")
                );
                termList.add(term);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return termList;
    }
}
