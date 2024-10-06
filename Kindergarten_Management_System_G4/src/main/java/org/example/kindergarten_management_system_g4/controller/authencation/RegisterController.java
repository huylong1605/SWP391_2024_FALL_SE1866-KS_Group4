/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/2/2024       1.1              Nguyễn Huy Long - He160140        Update validate Register
 */

package org.example.kindergarten_management_system_g4.controller.authencation;

import org.example.kindergarten_management_system_g4.dao.AuthenDAO.RegisterDAO;
import org.example.kindergarten_management_system_g4.javaMail.EmailService;
import org.example.kindergarten_management_system_g4.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Sử lý logic cho việc kiểm tra đầu vào trước khi dữ liệu người dùng được lưu vào database
 * @author Nguyễn Huy Long
 */
@WebServlet(name = "Register", value = "/register")
public class RegisterController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RegisterController.class.getName());
    private static final String PHONE_NUMBER_PATTERN = "^0[35789]\\d{8}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_NUMBER_PATTERN);
    private static final String EMAIL_PATTERN = "^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_CHECK = Pattern.compile(EMAIL_PATTERN);
    private static final int MAX_LENGTH = 30;
    private static final int MAX_LENGTH_PASSWORD = 10;
    private static final int MAX_LENGTH_ADDRESS = 50;

    private RegisterDAO registerDao;

    @Override
    public void init() throws ServletException {
        super.init();
        registerDao = new RegisterDAO();
        LOGGER.info("RegisterParentController initialized.");
    }

    /**
     * Xử lý yêu cầu đăng ký người dùng
     * @param req Đối tượng HttpServletRequest chứa thông tin yêu cầu
     * @param resp Đối tượng HttpServletResponse để gửi phản hồi
     * @throws ServletException Nếu có lỗi xảy ra trong servlet
     * @throws IOException Nếu có lỗi xảy ra trong quá trình I/O
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullname = req.getParameter("fullname").trim().replaceAll("\\s+", " ");
        String password = req.getParameter("password").trim();
        String confirmPassword = req.getParameter("confirmPassword").trim();
        String email = req.getParameter("email").trim();
        String phone = req.getParameter("phone").trim();
        String gender = req.getParameter("gender");
        String address = req.getParameter("address").trim().replaceAll("\\s+", " ");

        LOGGER.info("Received registration request for email: " + email);

        if (!validateMaxLength(req, resp, fullname, address, password)) return;

        if (!validateInput(req, resp, password, confirmPassword, email, phone, gender)) return;

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        User user = createUser(fullname, email, hashedPassword, gender, phone, address);

        if (isUserAlreadyRegistered(req, resp, email, phone)) return;

        try {
            boolean isInserted = registerDao.insertUser(user);
            if (isInserted) {
                LOGGER.info("User registered successfully: " + email);
                CompletableFuture.runAsync(() -> sendRegistrationEmail(email, fullname));
                req.setAttribute("registerSuccessful", "Register successful, log in now!");
                req.getRequestDispatcher("Login.jsp").forward(req, resp);
            } else {
                LOGGER.warning("Failed to register user: " + email);
                setUserAttributes(req, fullname, email, phone, password, confirmPassword, gender, address);
                req.getRequestDispatcher("register.jsp").forward(req, resp);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Database error while registering user: " + email, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Kiểm tra độ dài tối đa của các trường đầu vào
     * @param req Đối tượng HttpServletRequest
     * @param resp Đối tượng HttpServletResponse
     * @param fullname Tên đầy đủ của người dùng
     * @param address Địa chỉ của người dùng
     * @param password Mật khẩu của người dùng
     * @return true nếu tất cả các trường hợp hợp lệ, false nếu có lỗi
     * @throws ServletException Nếu có lỗi xảy ra trong servlet
     * @throws IOException Nếu có lỗi xảy ra trong quá trình I/O
     */
    private boolean validateMaxLength(HttpServletRequest req, HttpServletResponse resp, String fullname, String address, String password) throws ServletException, IOException {
        if (fullname.length() > MAX_LENGTH) {
            req.setAttribute("fullname_too_long", "Full name must be less than " + MAX_LENGTH + " characters!");
            LOGGER.log(Level.SEVERE, "Full name must be less than " + MAX_LENGTH + " characters!" );
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return false;
        }

        if (address.length() > MAX_LENGTH_ADDRESS) {
            req.setAttribute("address_too_long", "Address must be less than " + MAX_LENGTH_ADDRESS + " characters!");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return false;
        }

        if (password.length() > MAX_LENGTH_PASSWORD) {
            req.setAttribute("password_too_long", "Password must be less than " + MAX_LENGTH_PASSWORD + " characters!");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return false;
        }

        return true;
    }

    /**
     * Kiểm tra tính hợp lệ của các đầu vào như mật khẩu, email và số điện thoại
     * @param req Đối tượng HttpServletRequest
     * @param resp Đối tượng HttpServletResponse
     * @param password Mật khẩu của người dùng
     * @param confirmPassword Mật khẩu xác nhận
     * @param email Email của người dùng
     * @param phone Số điện thoại của người dùng
     * @param gender Giới tính của người dùng
     * @return true nếu tất cả các đầu vào hợp lệ, false nếu có lỗi
     * @throws ServletException Nếu có lỗi xảy ra trong servlet
     * @throws IOException Nếu có lỗi xảy ra trong quá trình I/O
     */
    private boolean validateInput(HttpServletRequest req, HttpServletResponse resp, String password, String confirmPassword, String email, String phone, String gender) throws ServletException, IOException {
        if (!password.equals(confirmPassword)) {
            req.setAttribute("Password_not_match", "Passwords do not match!");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return false;
        }

        if (!isValidPhoneNumber(phone)) {
            req.setAttribute("phone_not_match", "Phone number does not match!");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return false;
        }

        if (!isValidEmail(email)) {
            req.setAttribute("email_not_match", "Email does not match!");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return false;
        }

        if (gender == null) {
            req.setAttribute("gender_null", "You forgot to choose a gender");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return false;
        }

        return true;
    }

    /**
     * Kiểm tra xem người dùng đã đăng ký hay chưa
     * @param req Đối tượng HttpServletRequest
     * @param resp Đối tượng HttpServletResponse
     * @param email Email của người dùng
     * @param phone Số điện thoại của người dùng
     * @return true nếu người dùng đã đăng ký, false nếu chưa
     * @throws ServletException Nếu có lỗi xảy ra trong servlet
     * @throws IOException Nếu có lỗi xảy ra trong quá trình I/O
     */
    private boolean isUserAlreadyRegistered(HttpServletRequest req, HttpServletResponse resp, String email, String phone) throws ServletException, IOException {
        try {
            if (registerDao.checkPhone(phone)) {
                req.setAttribute("phone_exits", "Phone already exists!");
                req.getRequestDispatcher("register.jsp").forward(req, resp);
                return true;
            }

            if (registerDao.checkEmail(email)) {
                req.setAttribute("email_exits", "Email already exists!");
                req.getRequestDispatcher("register.jsp").forward(req, resp);
                return true;
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error checking if user is already registered", e);
            throw new RuntimeException(e);
        }

        return false;
    }

    /**
     * Tạo đối tượng User từ thông tin đầu vào
     * @param fullname Tên đầy đủ của người dùng
     * @param email Email của người dùng
     * @param hashedPassword Mật khẩu đã mã hóa của người dùng
     * @param gender Giới tính của người dùng
     * @param phone Số điện thoại của người dùng
     * @param address Địa chỉ của người dùng
     * @return Đối tượng User đã được tạo
     */
    private User createUser(String fullname, String email, String hashedPassword, String gender, String phone, String address) {
        User user = new User();
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setGender(Integer.parseInt(gender));
        user.setPhoneNumber(phone);
        user.setAddress(address);
        LOGGER.info("Created user object for registration: " + email);
        return user;
    }

    /**
     * Gửi email xác nhận đăng ký
     * @param email Email của người dùng
     * @param fullname Tên đầy đủ của người dùng
     */
    private void sendRegistrationEmail(String email, String fullname) {
        EmailService emailService = new EmailService();
        emailService.send(email, "Congratulations " + fullname, "You have successfully registered with the Kindergarten Management System using the email " + email);
        LOGGER.info("Sent registration email to: " + email);
    }

    /**
     * Kiểm tra tính hợp lệ của số điện thoại
     * @param phone Số điện thoại cần kiểm tra
     * @return true nếu số điện thoại hợp lệ, false nếu không
     */
    private boolean isValidPhoneNumber(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * Kiểm tra tính hợp lệ của địa chỉ email
     * @param email Địa chỉ email cần kiểm tra
     * @return true nếu địa chỉ email hợp lệ, false nếu không
     */
    private boolean isValidEmail(String email) {
        return EMAIL_CHECK.matcher(email).matches();
    }

    /**
     * Đặt các thuộc tính cho người dùng trong yêu cầu
     * @param req Đối tượng HttpServletRequest
     * @param fullname Tên đầy đủ của người dùng
     * @param email Email của người dùng
     * @param phone Số điện thoại của người dùng
     * @param password Mật khẩu của người dùng
     * @param confirmPassword Mật khẩu xác nhận
     * @param gender Giới tính của người dùng
     * @param address Địa chỉ của người dùng
     */
    private void setUserAttributes(HttpServletRequest req, String fullname, String email, String phone, String password, String confirmPassword, String gender, String address) {
        req.setAttribute("fullname", fullname);
        req.setAttribute("email", email);
        req.setAttribute("phone", phone);
        req.setAttribute("password", password);
        req.setAttribute("confirmPassword", confirmPassword);
        req.setAttribute("gender", gender);
        req.setAttribute("address", address);
        LOGGER.info("Setting user attributes for registration form.");
    }
}
