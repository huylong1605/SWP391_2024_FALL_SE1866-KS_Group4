package org.example.kindergarten_management_system_g4.controller.studentClassManagementController;
import org.example.kindergarten_management_system_g4.dao.StudentClassManageDAO.StudentClassManageDAO;
import org.example.kindergarten_management_system_g4.model.ClassLevel;
import org.example.kindergarten_management_system_g4.model.Classes;
import org.example.kindergarten_management_system_g4.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Views/Manager/StudentHaveClass")
public class StudentHadClassController extends HttpServlet {
    private StudentClassManageDAO studentClassManageDAO;

    @Override
    public void init() throws ServletException {
        studentClassManageDAO = new StudentClassManageDAO(); // Khởi tạo DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String classLevelIdStr = request.getParameter("classLevelId");
        String pageStr = request.getParameter("page");
        String studentName = request.getParameter("studentName");

        List<ClassLevel> allClassLevels = studentClassManageDAO.getAllClassLevels();
        List<Classes> classesByLevel = null;
        List<Student> studentsByLevel = null;

        // Chuyển đổi tham số classLevelId và page từ chuỗi thành số
        int classLevelId = (classLevelIdStr != null && !classLevelIdStr.isEmpty()) ? Integer.parseInt(classLevelIdStr) : -1;
        int currentPage = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;
        int pageSize = 15; // Số lượng bản ghi mỗi trang
        int offset = (currentPage - 1) * pageSize; // Tính toán offset cho phân trang

        // Thêm lựa chọn "Tất cả" vào danh sách cấp lớp
        ClassLevel allClassLevel = new ClassLevel();
        allClassLevel.setClassLevelId(-1);
        allClassLevel.setClassLevelName("ALL");
        allClassLevel.setAgeRange(-1);
        allClassLevels.add(0, allClassLevel);

        try {
            if (studentName != null && !studentName.trim().isEmpty()) {
                // Tìm kiếm học sinh theo tên
                studentsByLevel = studentClassManageDAO.searchStudentsByNameWithPaging(studentName, pageSize, offset);
                request.setAttribute("studentName", studentName); // Giữ lại tên đã tìm kiếm

                int totalStudents = studentClassManageDAO.countStudentsByName(studentName);
                request.setAttribute("totalStudents", totalStudents);
            } else {
                // Phân trang học sinh theo cấp lớp hoặc tất cả học sinh đã có lớp
                if (classLevelId != -1) {
                    // Lấy danh sách các lớp có cấp độ tương ứng với classLevelId
                    classesByLevel = studentClassManageDAO.getClassesByClassLevel(classLevelId);
                    studentsByLevel = studentClassManageDAO.getAssignedStudentsByClassLevelWithPaging(classLevelId, pageSize, offset);
                    request.setAttribute("selectedClassLevel", classLevelId);
                } else {
                    studentsByLevel = studentClassManageDAO.getAssignedStudentsWithPaging(pageSize, offset);
                    request.setAttribute("selectedClassLevel", -1);
                }
            }

            // Tính toán tổng số học sinh và tổng số trang
            int totalStudents = (studentName != null && !studentName.trim().isEmpty())
                    ? studentClassManageDAO.countStudentsByName(studentName)
                    : (classLevelId != -1)
                    ? studentClassManageDAO.countAssignedStudentsByClassLevel(classLevelId)
                    : studentClassManageDAO.countAllAssignedStudents();

            int totalPages = (int) Math.ceil((double) totalStudents / pageSize);

            // Gửi các thuộc tính tới JSP
            request.setAttribute("classLevels", allClassLevels);
            request.setAttribute("classesByLevel", classesByLevel); // Danh sách các lớp cho cấp lớp đã chọn
            request.setAttribute("studentsByLevel", studentsByLevel); // Danh sách học sinh theo lớp
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalStudents", totalStudents);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi khi xử lý.");
        }

        // Forward tới JSP
        request.getRequestDispatcher("/Views/Manager/StudentHaveClass.jsp").forward(request, response);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] studentIds = request.getParameterValues("studentIds");
        String action = request.getParameter("action");
        String newClassIdStr = request.getParameter("newClassId");
        String selectedClassLevel = request.getParameter("classLevelId"); // Lấy cấp lớp hiện tại

        if (selectedClassLevel == null) {
            selectedClassLevel = "-1";
        }

        try {

            if (studentIds != null && studentIds.length > 0) {
                List<Integer> studentIdList = new ArrayList<>();
                for (String studentId : studentIds) {
                    studentIdList.add(Integer.parseInt(studentId)); // Chuyển đổi các String thành Integer
                }

                if ("transfer".equals(action) && newClassIdStr != null && !newClassIdStr.isEmpty()) {
                    int newClassId = Integer.parseInt(newClassIdStr);
                    String className = studentClassManageDAO.getClassNameById(newClassId); // Lấy tên lớp từ DB

                    // Kiểm tra số lượng học sinh trong lớp mới
                    int currentStudentCount = studentClassManageDAO.countStudentsInClass(newClassId);

                    if (currentStudentCount >= 30) {
                        String errorMessage = String.format(
                                "The class '%s' already has %d students. You cannot transfer %d more students as it exceeds the limit of 30.",
                                className,
                                currentStudentCount,
                                studentIdList.size()
                        );
                        request.getSession().setAttribute("error", errorMessage);
                    } else {
                        // Thực hiện chuyển học sinh
                        studentClassManageDAO.updateStudentClass(studentIdList, newClassId);
                        request.getSession().setAttribute("message", "transfer successful.");
                    }
                } else if ("delete".equals(action)) {
                    studentClassManageDAO.removeStudentsFromClass(studentIdList);
                    request.getSession().setAttribute("message", "Delete successful.");
                }
            }

            // Redirect về trang đã lọc với thông tin classLevelId
            response.sendRedirect(request.getContextPath() + "/Views/Manager/StudentHaveClass?classLevelId=" + selectedClassLevel);

        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Có lỗi xảy ra khi xử lý yêu cầu. Vui lòng thử lại.");
            response.sendRedirect(request.getContextPath() + "/Views/Manager/StudentHaveClass?classLevelId=" + selectedClassLevel);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Dữ liệu không hợp lệ.");
            response.sendRedirect(request.getContextPath() + "/Views/Manager/StudentHaveClass?classLevelId=" + selectedClassLevel);
        }
    }




}