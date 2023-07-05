package com.elis.twitter.servlet;

import com.elis.twitter.model.User;
import com.elis.twitter.persistence.DAOFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession httpSession = request.getSession();

        // empty username or password
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("message", "Inserire credenziali");
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
        } else {
            User user = DAOFactory.getDAOFactory().getUserDAO().login(username, password);

            // user does not exist
            if (user == null) {
                request.setAttribute("message", "Credenziali errate");
                request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
            } else {
                // put the user in session
                httpSession.setAttribute("loggedInUser", user);
                response.sendRedirect(request.getContextPath() + "/Dashboard");
            }
        }
    }
}