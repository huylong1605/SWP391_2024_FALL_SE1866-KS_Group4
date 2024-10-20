package org.example.kindergarten_management_system_g4.dao.SubjectDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private Connection connection;

    public SubjectDAO()  {
        try {
            this.connection = DBConnection.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tạo hoặc chèn một môn học mới vào cơ sở dữ liệu.
     *
     * @param subject đối tượng Subject chứa thông tin môn học cần thêm
     * @return true nếu chèn thành công, ngược lại false
     */
    public boolean createSubject(Subject subject)  {
        String sql = "INSERT INTO Subject (subject_Code, subject_name, Description, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, subject.getSubjectCode());
            statement.setString(2, subject.getSubjectName());
            statement.setString(3, subject.getDescription());
            statement.setString(4, subject.getStatus());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    /**
     * Cập nhật thông tin một môn học đã tồn tại trong cơ sở dữ liệu.
     *
     * @param subject đối tượng Subject chứa thông tin môn học cần cập nhật
     * @return true nếu cập nhật thành công, ngược lại false
     */
    public boolean updateSubject(Subject subject)  {
        String sql = "UPDATE Subject SET subject_Code = ?, subject_name = ?, Description = ?, status=? WHERE Subject_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, subject.getSubjectCode());
            statement.setString(2, subject.getSubjectName());
            statement.setString(3, subject.getDescription());
            statement.setString(4, subject.getStatus());
            statement.setInt(5, subject.getSubjectId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Xóa một môn học khỏi cơ sở dữ liệu dựa trên ID.
     *
     * @param subjectId ID của môn học cần xóa
     * @return true nếu xóa thành công, ngược lại false
     */
    public boolean deleteSubject(int subjectId)  {
        String sql = "DELETE FROM Subject WHERE Subject_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, subjectId);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Lấy thông tin môn học dựa trên ID.
     *
     * @param subjectId ID của môn học cần lấy
     * @return đối tượng Subject nếu tìm thấy, ngược lại null
     */
    public Subject getSubjectById(int subjectId) {
        String sql = "SELECT * FROM Subject WHERE Subject_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, subjectId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Subject s = new Subject(
                        resultSet.getInt("Subject_ID"),
                        resultSet.getString("subject_Code"),
                        resultSet.getString("subject_name"),
                        resultSet.getString("Description"),
                        resultSet.getString("Status")
                );
                s.setStatus(resultSet.getString("status"));
                return  s;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Lấy thông tin môn học dựa trên ID và mã môn học.
     *
     * @param subjectId ID của môn học cần kiểm tra
     * @param code mã môn học cần tìm
     * @return đối tượng Subject nếu tìm thấy, ngược lại null
     */
    public Subject getSubjectByIdCode(int subjectId, String code) {
        String sql = "SELECT * FROM Subject WHERE Subject_ID != ? and subject_Code = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, subjectId);
            statement.setString(2, code);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Subject(
                        resultSet.getInt("Subject_ID"),
                        resultSet.getString("subject_Code"),
                        resultSet.getString("subject_name"),
                        resultSet.getString("Description"),
                        resultSet.getString("Status")
                );

            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Lấy danh sách tất cả các môn học từ cơ sở dữ liệu.
     *
     * @return danh sách các đối tượng Subject
     */
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM Subject";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Subject s = new Subject(
                        resultSet.getInt("Subject_ID"),
                        resultSet.getString("subject_Code"),
                        resultSet.getString("subject_name"),
                        resultSet.getString("Description"),
                        resultSet.getString("Status")
                );
                s.setStatus(resultSet.getString("status"));
                subjects.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return subjects;
    }

    /**
     * Lưu một môn học mới vào cơ sở dữ liệu.
     *
     * @param subject đối tượng Subject chứa thông tin môn học cần lưu
     */
    public void save(Subject subject) {
        String query = "INSERT INTO subjects (subject_name, description) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Thiết lập các giá trị cho câu lệnh SQL
            preparedStatement.setString(1, subject.getSubjectName());
            preparedStatement.setString(2, subject.getDescription());

            // Thực hiện câu lệnh SQL
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Bắt lỗi khi có sự cố kết nối cơ sở dữ liệu
        }
    }

//    Filter
public List<Subject> filterSubjects(String subjectCode, String subjectName, String status) {
    List<Subject> subjectList = new ArrayList<>();

    // Tạo câu truy vấn SQL dựa trên các tiêu chí
    String sql = "SELECT * FROM subject WHERE 1=1";
    if (subjectCode != null && !subjectCode.isEmpty()) {
        sql += " AND subject_Code LIKE ?";
    }
    if (subjectName != null && !subjectName.isEmpty()) {
        sql += " AND subject_name LIKE ?";
    }
    if (status != null && !status.isEmpty()) {
        sql += " AND Status = ?";
    }


    try (PreparedStatement statement = this.connection.prepareStatement(sql)) {

        int paramIndex = 1;

        // Thiết lập tham số cho câu truy vấn
        if (subjectCode != null && !subjectCode.isEmpty()) {
            statement.setString(paramIndex++, "%" + subjectCode + "%");
        }
        if (subjectName != null && !subjectName.isEmpty()) {
            statement.setString(paramIndex++, "%" + subjectName + "%");
        }
        if (status != null && !status.isEmpty()) {
            statement.setString(paramIndex++, status);
        }

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Subject subject = new Subject();
            subject.setSubjectId(resultSet.getInt("Subject_ID"));
            subject.setSubjectCode(resultSet.getString("subject_Code"));
            subject.setSubjectName(resultSet.getString("subject_name"));
            subject.setDescription(resultSet.getString("Description"));
            subject.setStatus(resultSet.getString("Status"));
            subjectList.add(subject);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return subjectList;
}


}



