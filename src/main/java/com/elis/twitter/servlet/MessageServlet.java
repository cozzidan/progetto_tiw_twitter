package com.elis.twitter.servlet;

import com.elis.twitter.model.Message;
import com.elis.twitter.model.Thread;
import com.elis.twitter.model.User;
import com.elis.twitter.persistence.DAOFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;

@WebServlet(name = "MessageServlet", value = "/MessageServlet")
@MultipartConfig
public class MessageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/logged/message.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        Thread thread = (Thread) httpSession.getAttribute("thread");

        String text = request.getParameter("text");
        LocalDate date = LocalDate.now();

        // file to upload
        Part part = request.getPart("image");
        String image = part.getSubmittedFileName();

        // invalid fields
        if (text == null || text.trim().isEmpty() || (!image.isEmpty() && !part.getContentType().startsWith("image/"))) {
            httpSession.setAttribute("errorCreationMessage", "Invio messaggio fallito: uno o più campi non sono validi.");
            response.sendRedirect(request.getContextPath() + "/ThreadServlet?thread_id=" + URLEncoder.encode(String.valueOf(thread.getId())));
        } else {
            // upload the file
            if (!image.isEmpty()) {
                String savePath = request.getServletContext().getRealPath("") + File.separator + "message_images";
                File imageSaveDirectory = new File(savePath);
                if (!imageSaveDirectory.exists()) {
                    imageSaveDirectory.mkdir();
                }
                part.write(savePath + File.separator + image);
            } else {
                image = null;
            }

            User user = (User) httpSession.getAttribute("loggedInUser");

            Message message = new Message(text, date, image, user, thread);

            // save the new message
            if (!DAOFactory.getDAOFactory().getMessageDAO().save(message)) {
                httpSession.setAttribute("errorCreationMessage", "Invio messaggio fallito.");
                response.sendRedirect(request.getContextPath() + "/ThreadServlet?thread_id=" + URLEncoder.encode(String.valueOf(thread.getId())));
            } else {
                thread = DAOFactory.getDAOFactory().getThreadDAO().getById(thread.getId());
                user = DAOFactory.getDAOFactory().getUserDAO().getById(user.getId());

                // put the thread in session
                httpSession.setAttribute("thread", thread);

                // put the user in session
                httpSession.setAttribute("loggedInUser", user);

                response.sendRedirect(request.getContextPath() + "/ThreadServlet?thread_id=" + URLEncoder.encode(String.valueOf(thread.getId()), "UTF-8") + "&page=1");
            }
        }
    }
}