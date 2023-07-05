package com.elis.twitter.servlet;

import com.elis.twitter.model.Comment;
import com.elis.twitter.model.Message;
import com.elis.twitter.model.Thread;
import com.elis.twitter.model.User;
import com.elis.twitter.persistence.DAOFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.collections4.ListUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@WebServlet(name = "ThreadServlet", value = "/ThreadServlet")
public class ThreadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // no threads selected
        if (request.getParameter("thread_id") == null) {
            request.getSession().setAttribute("message", "Devi prima selezionare un thread.");
            response.sendRedirect(request.getContextPath() + "/Dashboard");
        } else {
            Long thread_id = Long.valueOf(request.getParameter("thread_id"));
            Thread thread = DAOFactory.getDAOFactory().getThreadDAO().getById(thread_id);

            // thread does not exist
            if (thread == null) {
                request.getSession().setAttribute("message", "Errore. Selezionare un thread.");
                response.sendRedirect(request.getContextPath() + "/Dashboard");
            } else {
                // page number
                int page;
                int maxPage = 1;
                String pageString = request.getParameter("page");

                if (pageString != null) {
                    page = Integer.parseInt(pageString);
                    List<List<Message>> parts = ListUtils.partition(thread.getMessages(), 10);
                    maxPage = parts.size();

                    if (maxPage == 0) {
                        maxPage = 1;
                    }
                } else {
                    page = 1;
                }

                // previous/next page
                String place = request.getParameter("place");
                if (place != null && !place.trim().isEmpty()) {
                    if (place.equals("previous")) {
                        page--;
                    } else if (place.equals("next")) {
                        page++;
                    }
                }

                // invalid page number
                if (page < 1 || page > maxPage) {
                    request.getSession().setAttribute("message", "Errore. Selezionare un thread.");
                    response.sendRedirect(request.getContextPath() + "/Dashboard");
                } else {
                    // take the messages
                    List<Message> threadMessages = DAOFactory.getDAOFactory().getMessageDAO().getPaginatedMessagesForThread(thread, page*10-10);

                    // put the thread in session
                    request.getSession().setAttribute("thread", thread);

                    request.setAttribute("threadMessages", threadMessages);
                    request.setAttribute("page", page);
                    request.setAttribute("maxPage", maxPage);

                    request.getRequestDispatcher("/WEB-INF/view/logged/thread.jsp").forward(request, response);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        Thread thread = (Thread) httpSession.getAttribute("thread");

        Long message_id = Long.valueOf(request.getParameter("message_id"));
        Message message = DAOFactory.getDAOFactory().getMessageDAO().getById(message_id);

        int page = Integer.parseInt(request.getParameter("page"));

        // comment text
        String text = request.getParameter("text");

        // invalid fields
        if (text == null || text.trim().isEmpty()) {
            httpSession.setAttribute("errorCreationComment", "Pubblicazione commento fallita: campo 'testo' non valido.");
        } else {
            User user = (User) httpSession.getAttribute("loggedInUser");

            Comment comment = new Comment(text, user, message);

            // save the new comment
            if (!DAOFactory.getDAOFactory().getCommentDAO().save(comment)) {
                httpSession.setAttribute("errorCreationComment", "Pubblicazione commento fallita.");
            } else {
                thread = DAOFactory.getDAOFactory().getThreadDAO().getById(thread.getId());
                httpSession.setAttribute("thread", thread);
                httpSession.setAttribute("loggedInUser", user);
            }
        }

        response.sendRedirect(request.getContextPath()
                + "/ThreadServlet?thread_id=" + URLEncoder.encode(String.valueOf(thread.getId()), "UTF-8")
                + "&page=" + URLEncoder.encode(String.valueOf(page), "UTF-8"));
    }
}