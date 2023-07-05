package com.elis.twitter.servlet;

import com.elis.twitter.model.User;
import com.elis.twitter.persistence.DAOFactory;
import com.elis.twitter.utils.EmailValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "SignUp", value = "/SignUp")
public class SignUp extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/signUp.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String surname = request.getParameter("surname");
        String name = request.getParameter("name");
        String dateOfBirthString = request.getParameter("dateOfBirth");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        LocalDate dateOfBirth = null;

        // date parsing
        if (dateOfBirthString != null && !dateOfBirthString.trim().isEmpty()) {
            try {
                dateOfBirth = LocalDate.parse(dateOfBirthString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // invalid fields
        if (surname == null ||
                name == null ||
                email == null ||
                username == null ||
                password == null ||
                repeatPassword == null ||
                dateOfBirth == null ||
                dateOfBirth.isAfter(LocalDate.now()) ||
                surname.trim().isEmpty() ||
                name.trim().isEmpty() ||
                email.trim().isEmpty() ||
                username.trim().isEmpty() ||
                password.trim().isEmpty() ||
                repeatPassword.trim().isEmpty() ||
                !repeatPassword.equals(password)) {
            request.setAttribute("message", "Registrazione fallita: uno o piu' campi non sono validi.");
            request.getRequestDispatcher("/WEB-INF/view/signUp.jsp").forward(request, response);
        } else if (DAOFactory.getDAOFactory().getUserDAO().getByUsername(username) != null) {
            // username not available
            request.setAttribute("message", "Registrazione fallita: username non disponibile.");
            request.getRequestDispatcher("/WEB-INF/view/signUp.jsp").forward(request, response);
        } else if (!EmailValidator.isValidEmail(email)) {
            // invalid email
            request.setAttribute("message", "Registrazione fallita: email non valida.");
            request.getRequestDispatcher("/WEB-INF/view/signUp.jsp").forward(request, response);
        } else {
            HttpSession httpSession = request.getSession();
            User user = new User(surname, name, dateOfBirth, email, username, password);

            // save the new user
            if (!DAOFactory.getDAOFactory().getUserDAO().save(user)) {
                httpSession.setAttribute("message", "Impossibile effettuare la registrazione, l'email risulta già utilizzata.");
            } else {
                httpSession.setAttribute("message", "Ora che ti sei registrato puoi effettuare il login.");
            }

            response.sendRedirect(request.getContextPath() + "/Home");
        }
    }
}