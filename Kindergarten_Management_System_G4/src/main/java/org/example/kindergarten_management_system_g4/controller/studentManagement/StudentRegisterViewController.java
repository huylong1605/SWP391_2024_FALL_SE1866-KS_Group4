/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 12/10/2024       1.1              Đào Xuân Bình - HE163115        View Form Register Student
 */

package org.example.kindergarten_management_system_g4.controller.studentManagement;

import org.example.kindergarten_management_system_g4.model.ClassDAL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Lớp StudentRegisterViewController chịu trách nhiệm xử lý yêu cầu HTTP GET để hiển thị form đăng ký sinh viên.
 * Yêu cầu sẽ được chuyển tiếp đến trang "registerStudent.jsp".
 * <p>Lỗi: Chưa phát hiện lỗi.
 *
 * @author Đào Xuân Bình
 */
@WebServlet(name = "viewFormRegisterStudent", value = "/viewFormRegisterStudent")
public class StudentRegisterViewController extends HttpServlet {

    /**
     * Xử lý yêu cầu GET từ phía client để hiển thị form đăng ký sinh viên.
     * Chuyển tiếp yêu cầu đến trang "registerStudent.jsp" để người dùng có thể điền thông tin đăng ký.
     *
     * @param req đối tượng HttpServletRequest chứa yêu cầu từ phía client
     * @param resp đối tượng HttpServletResponse chứa phản hồi của servlet
     * @throws ServletException nếu có lỗi đặc thù của servlet xảy ra
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra được phát hiện khi servlet xử lý yêu cầu GET
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Chuyển tiếp đến trang "registerStudent.jsp" để hiển thị form đăng ký sinh viên
        req.getRequestDispatcher("registerStudent.jsp").forward(req, resp);
    }

}
