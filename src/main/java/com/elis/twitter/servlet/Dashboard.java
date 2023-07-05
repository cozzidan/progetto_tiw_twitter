package com.elis.twitter.servlet;

import com.elis.twitter.model.Thread;
import com.elis.twitter.model.User;
import com.elis.twitter.persistence.DAOFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "Dashboard", value = "/Dashboard")
public class Dashboard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("loggedInUser");

        // take the other threads
        List<Thread> otherThreads = DAOFactory.getDAOFactory().getThreadDAO().getAllOther(user);
        request.setAttribute("otherThreads", otherThreads);

        request.getRequestDispatcher("/WEB-INF/view/logged/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // delete thread
        if (request.getParameter("_method") != null && request.getParameter("_method").equals("DELETE")) {
            doDelete(request, response);
        } else {
            HttpSession httpSession = request.getSession();

            String title = request.getParameter("title");
            LocalDate date = LocalDate.now();

            // invalid fields
            if (title == null || title.trim().isEmpty()) {
                httpSession.setAttribute("errorCreationThread", "Creazione fallita: titolo non valido.");
            } else {
                User user = (User) httpSession.getAttribute("loggedInUser");
                Long user_id = user.getId();

                Thread thread = new Thread(title, date, user);

                // save the new thread
                if (!DAOFactory.getDAOFactory().getThreadDAO().save(thread)) {
                    httpSession.setAttribute("errorCreationThread", "Creazione fallita.");
                } else {
                    user = DAOFactory.getDAOFactory().getUserDAO().getById(user_id);
                    httpSession.setAttribute("loggedInUser", user);
                }
            }
        }

        response.sendRedirect(request.getContextPath() + "/Dashboard");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();

        Long thread_id = Long.valueOf(request.getParameter("thread_id"));
        Thread thread = DAOFactory.getDAOFactory().getThreadDAO().getById(thread_id);

        User user = (User) httpSession.getAttribute("loggedInUser");

        // invalid thread_id
        if (thread == null) {
            httpSession.setAttribute("errorRemoveThread", "Rimozione fallita.");
        } else {
            // the thread to be deleted does not belong to the logged in user
            if (!Objects.equals(thread.getUser().getId(), user.getId())) {
                httpSession.setAttribute("errorRemoveThread", "Rimozione fallita.");
            } else {
                // delete thread
                if (!DAOFactory.getDAOFactory().getThreadDAO().delete(thread)) {
                    httpSession.setAttribute("errorRemoveThread", "Rimozione fallita.");
                } else {
                    user = DAOFactory.getDAOFactory().getUserDAO().getById(user.getId());
                    httpSession.setAttribute("loggedInUser", user);
                }
            }
        }
    }
}