package org.example.kindergarten_management_system_g4.controller.authencation;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.example.kindergarten_management_system_g4.dao.AuthenDAO.RegisterDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@WebServlet(name = "login_Gg", value = "/login_Gg")
public class Login_Gg extends HttpServlet {

    private RegisterDAO registerDao;

    public void init() throws ServletException {
        super.init();
        registerDao = new RegisterDAO();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        LoginGgToken loginGoogle = new LoginGgToken();
        String accessToken = loginGoogle.getToken(code);
        GoogleIdToken.Payload payload = null;
        try {
                payload = verifyGoogleToken(accessToken);
            String email = payload.getEmail();
            System.out.println(email);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }



        if (code != null || !code.isEmpty()) {
            req.setAttribute("codes", accessToken);
            RequestDispatcher dis = req.getRequestDispatcher("Index.jsp");
            dis.forward(req, resp);
        }
}
    public GoogleIdToken.Payload verifyGoogleToken(String tokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList("1028444732076-4llkdccstoav2g4bkdf3s75cj86kvl82.apps.googleusercontent.com"))// Đảm bảo Client ID chính xác
                .build();

        GoogleIdToken idToken = verifier.verify(tokenString);
        if (idToken != null) {
            return idToken.getPayload();
        } else {
            throw new GeneralSecurityException("Invalid ID token.");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
