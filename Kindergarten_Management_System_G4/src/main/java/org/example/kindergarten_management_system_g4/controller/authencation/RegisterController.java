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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet(name = "Register", value = "/register")
public class RegisterController extends HttpServlet {

    private static final String PHONE_NUMBER_PATTERN = "^0[35789]\\d{8}$";
    private static final Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);

    private static final String EMAIL_PATTERN = "^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    private RegisterDAO registerDao;

    public void init() throws ServletException {
        super.init();
        registerDao = new RegisterDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmailService emailService = new EmailService();
           String fullname = req.getParameter("fullname");

        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        String gender = req.getParameter("gender");



        if (!password.equals(confirmPassword)) {

            req.setAttribute("Password_not_match", "Passwords do not match!");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        if (!VietNamPhone(phone)){
            req.setAttribute("phone_not_match", "phone do not match!");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        if(!isValidEmail(email)){
            req.setAttribute("email_not_match", "phone do not match!");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        if (gender == null) {

            req.setAttribute("gender_null", "you forgot choose gender");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        try {
            boolean checkPhone = registerDao.checkLPhone(phone);
            if(checkPhone ) {
                req.setAttribute("phone_exits", "Phone already exists!");
                req.getRequestDispatcher("register.jsp").forward(req, resp);
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            boolean checkEmail = registerDao.checkEmail(email);
            if(checkEmail ) {
                req.setAttribute("email_exits", "Email already exists!");
                req.getRequestDispatcher("register.jsp").forward(req, resp);
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setGender(Integer.parseInt(gender));
        user.setPhoneNumber(phone);

        try {
            registerDao.insertUser(user);
            emailService.send(email, "Congratulations " + fullname, " You have successfully logged into the Kindergarten Management System by email " + email);
            req.getRequestDispatcher("Login.jsp").forward(req, resp);
        } catch (ClassNotFoundException e) {
            req.setAttribute("fullname", fullname);
            req.setAttribute("email", email);
            req.setAttribute("phone", phone);
            req.setAttribute("password", password);
            req.setAttribute("confirmPassword", confirmPassword);
            req.setAttribute("gender", gender);

            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }


    private  boolean VietNamPhone(String phone) {
        // Regex cho số điện thoại Việt Nam
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        return emailPattern.matcher(email).matches();
    }
}
