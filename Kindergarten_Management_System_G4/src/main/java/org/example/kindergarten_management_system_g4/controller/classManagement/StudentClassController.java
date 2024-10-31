

/*
 * Copyright(C) 2005,  <SWP_G4>.
 * <KMS> :
 *  <Kindergarten Management System>
 *
 * Record of change:
 * DATE                       Version             AUTHOR                       DESCRIPTION
 * <10/15/2024>                 <1.1>           <Vu Viet Chuc>            <Update searchStudents method>
 */



package org.example.kindergarten_management_system_g4.controller.classManagement;
import org.example.kindergarten_management_system_g4.dao.classDAO.studentInClassDAO.IStudentInClassDAO;
import org.example.kindergarten_management_system_g4.dao.classDAO.studentInClassDAO.StudentInClassDAO;
import org.example.kindergarten_management_system_g4.model.Student;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


/**
 * StudentClassController là lớp điều khiển xử lý các yêu cầu liên quan đến quản lý
 * học sinh trong các lớp học, bao gồm các chức năng như: hiển thị danh sách học sinh,
 * tìm kiếm học sinh, thêm học sinh vào lớp, lọc học sinh theo tình trạng, và hiển thị chi tiết.
 */

@WebServlet(value = {"/Views/Manager/listStudentInClass", "/Views/Manager/AddStudentToClass", "/Views/Manager/StudentDetails"})
public class StudentClassController extends HttpServlet {
    private IStudentInClassDAO studentDAO;


    /**
     * Phương thức khởi tạo init() được sử dụng để khởi tạo đối tượng studentDAO
     * khi servlet được tạo.
     */

    public void init() throws ServletException {
        studentDAO = new StudentInClassDAO(); // Khởi tạo đối tượng StudentDAO
    }

    /**
     * Phương thức doGet() xử lý các yêu cầu GET, bao gồm hiển thị danh sách học sinh,
     * tìm kiếm, lọc học sinh, hiển thị chi tiết học sinh, và thêm học sinh vào lớp.
     *
     * @param req  Đối tượng HttpServletRequest chứa yêu cầu từ phía client.
     * @param resp Đối tượng HttpServletResponse để trả về phản hồi cho client.
     * @throws ServletException Nếu có lỗi trong quá trình xử lý Servlet.
     * @throws IOException      Nếu có lỗi I/O xảy ra.
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String classIdParam = req.getParameter("classId");
        String studentIdParam = req.getParameter("studentId");
        String action = req.getParameter("action");
        String filterType = req.getParameter("classFilter");
        String searchTerm = req.getParameter("searchTerm");// "withClass", "noClass", hoặc "" (All)

        int page = 1; // Trang hiện tại
        int size = 8; // Số lượng bản ghi mỗi trang

        // Lấy tham số phân trang
        String pageParam = req.getParameter("page");
        String sizeParam = req.getParameter("size");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                // Giữ nguyên giá trị mặc định nếu không hợp lệ
            }
        }
        if (sizeParam != null) {
            try {
                size = Integer.parseInt(sizeParam);
            } catch (NumberFormatException e) {
                // Giữ nguyên giá trị mặc định nếu không hợp lệ
            }
        }

        // Kiểm tra nếu có lọc (searchTerm không rỗng hoặc action là "filter" với filterType không rỗng)
        boolean isFiltered = (searchTerm != null && !searchTerm.trim().isEmpty()) ||
                ("filter".equals(action) && filterType != null && !filterType.isEmpty());

        // Đặt thuộc tính isFiltered vào request
        req.setAttribute("isFiltered", isFiltered);
        req.setAttribute("filterType", filterType); // Lưu trữ filterType
        req.setAttribute("searchTerm", searchTerm); // Lưu trữ searchTerm

        try {
            if ("details".equals(action) && studentIdParam != null) {
                showStudentDetails(req, resp, studentIdParam);
            } else if (classIdParam != null && !classIdParam.isEmpty()) {
                if ("add".equals(action)) {
                    showAddStudentToClass(req, resp, classIdParam, page, size);
                } else if ("filter".equals(action) && filterType != null) {
                    filterStudents(req, resp, filterType, classIdParam, page, size);
                } else if ("search".equals(action)) {
                    searchTerm = (searchTerm != null) ? searchTerm.trim() : null;
                    if (searchTerm == null || searchTerm.isEmpty()) {
                        // Hiển thị tất cả sinh viên nếu searchTerm rỗng
                        showAddStudentToClass(req, resp, classIdParam, page, size);
                    } else {
                        // Tìm kiếm sinh viên theo tên
                        searchStudents(req, resp, searchTerm, classIdParam);
                    }
                } else {
                    listStudentsByClassId(req, resp, classIdParam);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Class ID or Student ID is required");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Class ID or Student ID");
        } catch (SQLException e) {
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to process request");
        }
    }


    /**
     * Phương thức searchStudents() dùng để tìm kiếm học sinh theo tên dựa trên từ khóa
     * tìm kiếm được truyền vào.
     *
     * @param req       Đối tượng HttpServletRequest chứa yêu cầu từ phía client.
     * @param resp      Đối tượng HttpServletResponse để trả về phản hồi cho client.
     * @param searchTerm Từ khóa tìm kiếm tên học sinh.
     * @param classIdParam Mã lớp để xác định lớp học.
     * @throws ServletException Nếu có lỗi trong quá trình xử lý Servlet.
     * @throws IOException      Nếu có lỗi I/O xảy ra.
     * @throws SQLException     Nếu có lỗi truy vấn cơ sở dữ liệu.
     */

    private void searchStudents(HttpServletRequest req, HttpServletResponse resp, String searchTerm, String classIdParam)
            throws ServletException, IOException, SQLException {
        int classId = Integer.parseInt(classIdParam);
        List<Student> students = studentDAO.searchStudentsByName(searchTerm);
        req.setAttribute("AllStudent", students);
        req.setAttribute("classId", classId);
        req.getRequestDispatcher("/Views/Manager/AddStudentToClass.jsp").forward(req, resp);
    }


    /**
     * Phương thức filterStudents() dùng để lọc danh sách học sinh theo bộ lọc (có lớp, không lớp)
     * và phân trang.
     *
     * @param req       Đối tượng HttpServletRequest chứa yêu cầu từ phía client.
     * @param resp      Đối tượng HttpServletResponse để trả về phản hồi cho client.
     * @param filterType Loại bộ lọc (có lớp, không lớp, tất cả).
     * @param classIdParam Mã lớp học.
     * @param page      Trang hiện tại để phân trang.
     * @param size      Số lượng bản ghi mỗi trang.
     * @throws ServletException Nếu có lỗi trong quá trình xử lý Servlet.
     * @throws IOException      Nếu có lỗi I/O xảy ra.
     * @throws SQLException     Nếu có lỗi truy vấn cơ sở dữ liệu.
     */
    private void filterStudents(HttpServletRequest req, HttpServletResponse resp, String filterType, String classIdParam, int page, int size)
            throws ServletException, IOException, SQLException {
        int classId = Integer.parseInt(classIdParam);
        List<Student> students;

        if ("withClass".equals(filterType)) {
            students = studentDAO.getStudentsWithClass(page, size);
        } else if ("noClass".equals(filterType)) {
            students = studentDAO.getStudentsWithoutClass(page, size);
        } else if ("".equals(filterType)) {
            students = studentDAO.getAllStudents(page, size);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid filter type");
            return;
        }

        int totalStudents = studentDAO.getTotalStudentsCount(filterType); // Lấy tổng số sinh viên theo bộ lọc
        int totalPages = (int) Math.ceil((double) totalStudents / size); // Tính số trang

        req.setAttribute("AllStudent", students);
        req.setAttribute("classId", classId); // Lưu trữ classId để sử dụng sau khi lọc
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("pageSize", size);
        req.getRequestDispatcher("/Views/Manager/AddStudentToClass.jsp").forward(req, resp);
    }


    /**
     * Phương thức showStudentDetails() dùng để hiển thị thông tin chi tiết của học sinh.
     *
     * @param req        Đối tượng HttpServletRequest chứa yêu cầu từ phía client.
     * @param resp       Đối tượng HttpServletResponse để trả về phản hồi cho client.
     * @param classIdParam Mã lop học.
     * @throws ServletException Nếu có lỗi trong quá trình xử lý Servlet.
     * @throws IOException      Nếu có lỗi I/O xảy ra.
     * @throws SQLException     Nếu có lỗi truy vấn cơ sở dữ liệu.
     */
    private void showAddStudentToClass(HttpServletRequest req, HttpServletResponse resp, String classIdParam, int pageNumber, int pageSize)
            throws ServletException, IOException, SQLException {
        int classId = Integer.parseInt(classIdParam);

        // Lấy danh sách học sinh với phân trang
        List<Student> allStudents = studentDAO.getAllStudents(pageNumber, pageSize); // Gọi phương thức đã cập nhật
        // Tổng số học sinh để tính tổng số trang
        int totalStudentsCount = studentDAO.getTotalStudentsCount(""); // Lấy tổng số học sinh không lọc
        int totalPages = (int) Math.ceil((double) totalStudentsCount / pageSize);
        String className = studentDAO.getClassNameById(classId);
        // Thêm các thuộc tính vào request
        req.setAttribute("AllStudent", allStudents);
        req.setAttribute("classId", classId);
        req.setAttribute("currentPage", pageNumber);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("pageSize", pageSize);
        req.setAttribute("className", className);

        // Chuyển hướng đến trang JSP
        req.getRequestDispatcher("/Views/Manager/AddStudentToClass.jsp").forward(req, resp);
    }

    /**
     * Phương thức listStudentsByClassId() dùng để hiển thị danh sách học sinh theo mã lớp.
     *
     * @param req        Đối tượng HttpServletRequest chứa yêu cầu từ phía client.
     * @param resp       Đối tượng HttpServletResponse để trả về phản hồi cho client.
     * @param studentIdParam Mã lop học.
     * @throws ServletException Nếu có lỗi trong quá trình xử lý Servlet.
     * @throws IOException      Nếu có lỗi I/O xảy ra.
     * @throws SQLException     Nếu có lỗi truy vấn cơ sở dữ liệu.
     */
    private void showStudentDetails(HttpServletRequest req, HttpServletResponse resp, String studentIdParam)
            throws ServletException, IOException, SQLException {
        int studentId = Integer.parseInt(studentIdParam);
        Student detailStudent = studentDAO.getStudentById(studentId);
        if (detailStudent != null) {
            req.setAttribute("detailStudent", detailStudent);
            req.getRequestDispatcher("/Views/Manager/StudentDetails.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Student not found");
        }
    }


    /**
     * Phương thức listStudentsByClassId() dùng để hiển thị danh sách học sinh theo mã lớp.
     *
     * @param req        Đối tượng HttpServletRequest chứa yêu cầu từ phía client.
     * @param resp       Đối tượng HttpServletResponse để trả về phản hồi cho client.
     * @param classIdParam Mã lớp học.
     * @throws ServletException Nếu có lỗi trong quá trình xử lý Servlet.
     * @throws IOException      Nếu có lỗi I/O xảy ra.
     * @throws SQLException     Nếu có lỗi truy vấn cơ sở dữ liệu.
     */
    private void listStudentsByClassId(HttpServletRequest req, HttpServletResponse resp, String classIdParam)
            throws ServletException, IOException, SQLException {
        int classId = Integer.parseInt(classIdParam);
        List<Student> students = studentDAO.getStudentsByClassId(classId);
        req.setAttribute("students", students);
        req.setAttribute("classId", classId);
        req.getRequestDispatcher("/Views/Manager/listStudentInClass.jsp").forward(req, resp);
    }

    /**
     * Phương thức doPost() xử lý các yêu cầu POST, bao gồm thêm hoặc xóa học sinh khỏi lớp học.
     *
     * @param req  Đối tượng HttpServletRequest chứa yêu cầu từ phía client.
     * @param resp Đối tượng HttpServletResponse để trả về phản hồi cho client.
     * @throws ServletException Nếu có lỗi trong quá trình xử lý Servlet.
     * @throws IOException      Nếu có lỗi I/O xảy ra.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String classIdParam = req.getParameter("classId");
        String studentIdParam = req.getParameter("studentId");
        String action = req.getParameter("action");

        if (classIdParam != null && studentIdParam != null) {
            int classId;
            int studentId;
            try {
                classId = Integer.parseInt(classIdParam);
                studentId = Integer.parseInt(studentIdParam);
                boolean isProcessed;
                // Kiểm tra nếu hành động là xóa học sinh khỏi lớp
                if ("remove".equals(action)) {
                    isProcessed = studentDAO.removeStudentFromClass(studentId);
                    if (isProcessed) {
                        resp.setContentType("application/json");
                        resp.getWriter().write("{\"success\": true, \"message\": \"This student delete successfully.\"}");
                    } else {
                        resp.setContentType("application/json");
                        resp.getWriter().write("{\"success\": false, \"message\": \"Can't delete student.\"}");
                    }
                } else {
                    // Thêm học sinh vào lớp
                    boolean isAdded = studentDAO.addStudentToClass(studentId, classId);
                    if (isAdded) {
                        resp.setContentType("application/json");
                        resp.getWriter().write("{\"success\": true, \"message\": \"This student delete successfully..\"}");
                    } else {
                        resp.setContentType("application/json");
                        resp.getWriter().write("{\"success\": false, \"message\": \"Can't delete student.\"}");
                    }
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Class ID or Student ID");
            } catch (SQLException e) {
                req.getRequestDispatcher("/error.jsp").forward(req, resp);
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to add or update student in class");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Class ID and Student ID are required");
        }
    }


}