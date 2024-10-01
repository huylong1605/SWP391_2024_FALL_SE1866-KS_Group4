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
import java.util.regex.Pattern;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "Register", value = "/register")
public class RegisterController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(RegisterController.class.getName());
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
        logger.info("RegisterParentController initialized.");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullname = req.getParameter("fullname").trim();
        String password = req.getParameter("password").trim();
        String confirmPassword = req.getParameter("confirmPassword").trim();
        String email = req.getParameter("email").trim();
        String phone = req.getParameter("phone").trim();
        String gender = req.getParameter("gender");
        String address = req.getParameter("address").trim();

        logger.info("Received registration request for email: " + email);

        if (!validateMaxLength(req, resp, fullname, address, password)) return;

        if (!validateInput(req, resp, password, confirmPassword, email, phone, gender)) return;

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        User user = createUser(fullname, email, hashedPassword, gender, phone, address);

        if (isUserAlreadyRegistered(req, resp, email, phone)) return;

        try {
            boolean isInserted = registerDao.insertUser(user);
            if (isInserted) {
                logger.info("User registered successfully: " + email);
                CompletableFuture.runAsync(() -> sendRegistrationEmail(email, fullname));
                req.setAttribute("registerSuccessful", "Register successful, log in now!");
                req.getRequestDispatcher("Login.jsp").forward(req, resp);
            } else {
                logger.warning("Failed to register user: " + email);
                setUserAttributes(req, fullname, email, phone, password, confirmPassword, gender, address);
                req.getRequestDispatcher("register.jsp").forward(req, resp);
            }
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Database error while registering user: " + email, e);
            throw new RuntimeException(e);
        }
    }

    private boolean validateMaxLength(HttpServletRequest req, HttpServletResponse resp, String fullname, String address, String password) throws ServletException, IOException {
        if (fullname.length() > MAX_LENGTH) {
            req.setAttribute("fullname_too_long", "Full name must be less than " + MAX_LENGTH + " characters!");
            logger.log(Level.SEVERE, "Full name must be less than " + MAX_LENGTH + " characters!" );
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
            logger.log(Level.SEVERE, "Error checking if user is already registered", e);
            throw new RuntimeException(e);
        }

        return false;
    }

    private User createUser(String fullname, String email, String hashedPassword, String gender, String phone, String address) {
        User user = new User();
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setGender(Integer.parseInt(gender));
        user.setPhoneNumber(phone);
        user.setAddress(address);
        logger.info("Created user object for registration: " + email);
        return user;
    }

    private void sendRegistrationEmail(String email, String fullname) {
        EmailService emailService = new EmailService();
        emailService.send(email, "Congratulations " + fullname, "You have successfully registered with the Kindergarten Management System using the email " + email);
        logger.info("Sent registration email to: " + email);
    }

    private boolean isValidPhoneNumber(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }

    private boolean isValidEmail(String email) {
        return EMAIL_CHECK.matcher(email).matches();
    }

    private void setUserAttributes(HttpServletRequest req, String fullname, String email, String phone, String password, String confirmPassword, String gender, String address) {
        req.setAttribute("fullname", fullname);
        req.setAttribute("email", email);
        req.setAttribute("phone", phone);
        req.setAttribute("password", password);
        req.setAttribute("confirmPassword", confirmPassword);
        req.setAttribute("gender", gender);
        req.setAttribute("address", address);
        logger.info("Setting user attributes for registration form.");
    }
}
