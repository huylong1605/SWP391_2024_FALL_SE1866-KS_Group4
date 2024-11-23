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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Views/Manager/StudentDontHaveClass")
public class StudentClassManagementController extends HttpServlet {
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

        int classLevelId = (classLevelIdStr != null && !classLevelIdStr.isEmpty()) ? Integer.parseInt(classLevelIdStr) : -1;
        int currentPage = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;
        int pageSize = 15;
        int offset = (currentPage - 1) * pageSize;

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
                // Phân trang học sinh theo cấp lớp hoặc tất cả học sinh chưa có lớp
                if (classLevelId != -1) {
                    classesByLevel = studentClassManageDAO.getClassesByClassLevel(classLevelId);
                    studentsByLevel = studentClassManageDAO.getUnassignedStudentsByClassLevelWithPaging(classLevelId, pageSize, offset);
                    request.setAttribute("selectedClassLevel", classLevelId);
                } else {
                    studentsByLevel = studentClassManageDAO.getAllUnassignedStudentsWithPaging(pageSize, offset);
                    request.setAttribute("selectedClassLevel", -1);
                }
            }

            int totalStudents = (studentName != null && !studentName.trim().isEmpty())
                    ? studentClassManageDAO.countStudentsByName(studentName)
                    : (classLevelId != -1)
                    ? studentClassManageDAO.countUnassignedStudentsByClassLevel(classLevelId)
                    : studentClassManageDAO.countAllUnassignedStudents();

            int totalPages = (int) Math.ceil((double) totalStudents / pageSize);

            // Gửi các thuộc tính tới JSP
            request.setAttribute("classLevels", allClassLevels);
            request.setAttribute("classesByLevel", classesByLevel);
            request.setAttribute("studentsByLevel", studentsByLevel);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalStudents", totalStudents);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi khi xử lý.");
        }

        // Forward tới JSP
        request.getRequestDispatcher("/Views/Manager/StudentDontHaveClass.jsp").forward(request, response);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(); // Lấy session
        String[] selectedStudentIds = request.getParameterValues("studentIds"); // Lấy danh sách học sinh được chọn
        String selectedClassIdStr = request.getParameter("classId"); // Lấy ID lớp học được
        String selectedClassLevel = request.getParameter("classLevelId");

        if (selectedStudentIds != null && selectedClassIdStr != null) {
            try {
                int classId = Integer.parseInt(selectedClassIdStr);
                String className = studentClassManageDAO.getClassNameById(classId); // Lấy tên lớp từ DB
                List<Integer> studentIds = new ArrayList<>();

                for (String studentId : selectedStudentIds) {
                    studentIds.add(Integer.parseInt(studentId));
                }

                // Kiểm tra số lượng học sinh hiện tại trong lớp
                int currentStudentCount = studentClassManageDAO.countStudentsInClass(classId);

                if (currentStudentCount + studentIds.size() > 30) {
                    // Hiển thị thông báo lỗi với số lượng học sinh hiện tại
                    String errorMessage = String.format(
                            "The class '%s' already has %d students. You cannot add %d more students as it exceeds the limit of 30.",
                            className,
                            currentStudentCount,
                            studentIds.size()
                    );
                    session.setAttribute("error", errorMessage);
                } else {
                    // Gọi phương thức assignStudentsToClass để thêm học sinh vào lớp
                    studentClassManageDAO.assignStudentsToClass(studentIds, classId);

                    // Gửi thông báo thành công
                    String successMessage = String.format(
                            "Added %d student to the class '%s' successfully! Current class size: %d students.",
                            studentIds.size(),
                            className,
                            currentStudentCount + studentIds.size()
                    );
                    session.setAttribute("message", successMessage);
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
                request.setAttribute("error", "Invalid student or class ID format.");
            }
        } else {
            request.setAttribute("error", "Please choose at least one student and one class.");
        }

        // Load lại danh sách classLevels, classesByLevel, studentsByLevel
        response.sendRedirect(request.getContextPath() + "/Views/Manager/StudentDontHaveClass?classLevelId=" + selectedClassLevel);
    }


}

