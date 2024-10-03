/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/2/2024       1.1              Nguyễn Huy Long - He160140        Update validate Forget Password
 */
package org.example.kindergarten_management_system_g4.controller.authencation;

import org.example.kindergarten_management_system_g4.dao.AuthenDAO.ForgetPasswordDAO;
import org.example.kindergarten_management_system_g4.javaMail.EmailService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;



@WebServlet(name = "forgetPassword", value = "/forgetPassword")
public class ForgetPassword extends HttpServlet {

    private ForgetPasswordDAO forgetPasswordDAO;
    private static final Logger LOGGER = Logger.getLogger(loginController.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        forgetPasswordDAO = new ForgetPasswordDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    /**
     * Phương thức doPost xử lý yêu cầu POST từ người dùng khi yêu cầu đặt lại mật khẩu.
     * Nó kiểm tra tính hợp lệ của địa chỉ email và gửi mã xác nhận đến email đó.
     *
     * @param req  Đối tượng HttpServletRequest chứa thông tin yêu cầu từ người dùng.
     * @param resp Đối tượng HttpServletResponse để gửi phản hồi cho người dùng.
     * @throws ServletException Nếu có lỗi trong quá trình xử lý Servlet.
     * @throws IOException      Nếu có lỗi đầu vào/đầu ra.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String email = req.getParameter("Email");
            EmailService emailService = new EmailService();

            String code = getRanDom();   //Lưu trữ giá trị random qua biến code
            try {
                String mail = forgetPasswordDAO.findMail(email); // Lưu email thông qua hàm findMail bằng biến mail
                if (mail.isEmpty() || mail == null) {             // Check null or empty

                    req.setAttribute("emailNull", "email is not exist");
                    req.getRequestDispatcher("forgotPassword.jsp").forward(req, resp);


                } else{

                    emailService.send(email, "Code", code);


                    LOGGER.info("Email before redirect: " + email); // In ra để kiểm tra
                    resp.sendRedirect("verificationCode.jsp?email=" + email);
                    forgetPasswordDAO.insertCode(code, email);   // Thêm code thông qua email ở database
                }

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    /**
     * Tạo chuỗi ngẫu nhiên gồm 6 chữ số.
     *
     * Phương thức này sử dụng đối tượng Random để tạo ra một số ngẫu nhiên có giá trị
     * từ 0 đến 999999. Sau đó, số này sẽ được định dạng thành chuỗi gồm 6 chữ số,
     * với các số 0 được thêm vào phía trước nếu cần.
     *
     * @return Chuỗi gồm 6 chữ số ngẫu nhiên.
     */
    public String getRanDom(){
        Random rd = new Random();       // Tạo đối tượng Random để sinh số ngẫu nhiên
        int num = rd.nextInt(999999); // Sinh số ngẫu nhiên trong khoảng từ 0 đến 999999
        return String.format("%06d",num); // Định dạng số thành chuỗi 6 chữ số
    }
}



