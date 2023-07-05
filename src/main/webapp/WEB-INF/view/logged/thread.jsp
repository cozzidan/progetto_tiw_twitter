<%@ page import="com.elis.twitter.model.User" %>
<%@ page import="org.apache.commons.collections4.ListUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="com.elis.twitter.model.Thread" %>
<%@ page import="com.elis.twitter.model.Message" %>
<%@ page import="com.elis.twitter.model.Comment" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/flowbite/1.6.6/flowbite.min.css" rel="stylesheet"/>
    <title>Thread</title>
    <style>
        body::-webkit-scrollbar {
            display: none;
        }

        body::-webkit-scrollbar-track {
            background-color: transparent;
        }

        body::-webkit-scrollbar-thumb {
            background-color: #888;
        }

        body::-webkit-scrollbar-thumb:hover {
            background-color: #555;
        }
    </style>
</head>
<body>
<div class="w-full p-5">
    <%
        Thread thread = (Thread) session.getAttribute("thread");
        List<Message> threadMessages = (request.getAttribute("threadMessages") != null) ? (List<Message>) request.getAttribute("threadMessages") : null;
        int maxPage = (int) request.getAttribute("maxPage");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    %>

    <!-- navbar -->
    <nav class="bg-white dark:bg-gray-900 fixed w-full h-16 flex items-center z-20 top-0 left-0 border-b border-gray-200 dark:border-gray-600 p-4">
        <div class="w-full flex flex-wrap items-center justify-between mx-auto">
            <!-- logo -->
            <a href="<%= request.getContextPath() %>/Home" class="flex items-center">
                <svg class="w-8 h-8 mr-2 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 18" fill="currentColor">
                    <path d="M18 4H16V9C16 10.0609 15.5786 11.0783 14.8284 11.8284C14.0783 12.5786 13.0609 13 12 13H9L6.846 14.615C7.17993 14.8628 7.58418 14.9977 8 15H11.667L15.4 17.8C15.5731 17.9298 15.7836 18 16 18C16.2652 18 16.5196 17.8946 16.7071 17.7071C16.8946 17.5196 17 17.2652 17 17V15H18C18.5304 15 19.0391 14.7893 19.4142 14.4142C19.7893 14.0391 20 13.5304 20 13V6C20 5.46957 19.7893 4.96086 19.4142 4.58579C19.0391 4.21071 18.5304 4 18 4Z" fill="currentColor"/>
                    <path d="M12 0H2C1.46957 0 0.960859 0.210714 0.585786 0.585786C0.210714 0.960859 0 1.46957 0 2V9C0 9.53043 0.210714 10.0391 0.585786 10.4142C0.960859 10.7893 1.46957 11 2 11H3V13C3 13.1857 3.05171 13.3678 3.14935 13.5257C3.24698 13.6837 3.38668 13.8114 3.55279 13.8944C3.71889 13.9775 3.90484 14.0126 4.08981 13.996C4.27477 13.9793 4.45143 13.9114 4.6 13.8L8.333 11H12C12.5304 11 13.0391 10.7893 13.4142 10.4142C13.7893 10.0391 14 9.53043 14 9V2C14 1.46957 13.7893 0.960859 13.4142 0.585786C13.0391 0.210714 12.5304 0 12 0Z" fill="currentColor"/>
                </svg>
                <span class="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">Twitter</span>
            </a>

            <button data-collapse-toggle="navbar-default" type="button" class="inline-flex items-center p-2 ml-3 text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600" aria-controls="navbar-default" aria-expanded="false">
                <span class="sr-only">Apri menu principale</span>
                <svg class="w-6 h-6" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 15a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1z" clip-rule="evenodd"></path></svg>
            </button>
            <div class="hidden items-center w-full md:block md:w-auto" id="navbar-default">
                <ul class="font-medium items-center flex flex-col p-4 md:p-0 mt-4 border border-gray-100 rounded-lg bg-gray-50 md:flex-row md:space-x-8 md:mt-0 md:border-0 md:bg-white dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700">
                    <li>
                        <a href="<%= request.getContextPath() %>/Home" class="block py-2 pl-3 pr-4 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent">Home</a>
                    </li>
                    <li>
                        <a href="<%= request.getContextPath() %>/Dashboard" class="block py-2 pl-3 pr-4 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent">Dashboard</a>
                    </li>
                    <!-- add message page -->
                    <li>
                        <a href="<%= request.getContextPath() %>/MessageServlet"
                           class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 inline-flex items-center">
                            <svg class="w-4 h-4 mr-3 text-gray-800 text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 18 18">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 1v16M1 9h16"/>
                            </svg>
                            Nuovo messaggio
                        </a>
                    </li>
                    <!-- logout -->
                    <li>
                        <a href="<%= request.getContextPath() %>/Logout"
                           class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 inline-flex items-center">
                            <svg class="w-4 h-4 mr-3 text-gray-800 text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 18 16">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M1 8h11m0 0L8 4m4 4-4 4m4-11h3a2 2 0 0 1 2 2v10a2 2 0 0 1-2 2h-3"/>
                            </svg>
                            Logout
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- under navbar -->
    <div class="w-full h-16"></div>

    <!-- thread title -->
    <div class="w-full">
        <h2 class="text-4xl font-bold dark:text-white mb-3">
            <%= thread.getTitle() %>
        </h2>

        <div class="flex">
            <h5 class="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                <%= thread.getUser().getUsername() %>
            </h5>
            <h5 class="mb-2 ml-1 text-2xl tracking-tight text-gray-900 dark:text-white">
                <%= "(" + thread.getUser().getName() + " " + thread.getUser().getSurname() + ")" %>
            </h5>
        </div>
    </div>

    <% if (maxPage != 1) { %>
    <p class="mb-3 mt-6 text-gray-500 dark:text-gray-400">pagina <%= request.getAttribute("page") %> di <%= maxPage %></p>
    <% } %>

    <div class="flex justify-between mt-6 mb-3">
        <!-- previous -->
        <% if (Integer.parseInt(request.getAttribute("page").toString()) > 1) { %>
        <div>
            <form action="<%= request.getContextPath() %>/ThreadServlet">
                <input type="hidden" name="place" value="previous">
                <input type="hidden" name="page" value="<%= request.getAttribute("page") %>">
                <input type="hidden" name="thread_id" value="<%= thread.getId() %>">
                <button type="submit"
                        class="flex items-center text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                    <svg class="w-4 h-4 mr-3 text-gray-800 text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 10">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 5H1m0 0 4 4M1 5l4-4"/>
                    </svg>
                    Indietro
                </button>
            </form>
        </div>
        <% } else { %>
        <div class="mb-3">
            <button type="submit"
                    class="flex items-center text-white bg-gray-200 hover:bg-gray-200 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                <svg class="w-4 h-4 mr-3 text-gray-800 text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 10">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 5H1m0 0 4 4M1 5l4-4"/>
                </svg>
                Indietro
            </button>
        </div>
        <% } %>

        <!-- next -->
        <% if (Integer.parseInt(request.getAttribute("page").toString()) < maxPage) { %>
        <div>
            <form action="<%= request.getContextPath() %>/ThreadServlet">
                <input type="hidden" name="place" value="next">
                <input type="hidden" name="page" value="<%= request.getAttribute("page") %>">
                <input type="hidden" name="thread_id" value="<%= thread.getId() %>">
                <button type="submit"
                        class="flex items-center text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                    Avanti
                    <svg class="w-4 h-4 ml-3 text-gray-800 text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 10">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M1 5h12m0 0L9 1m4 4L9 9"/>
                    </svg>
                </button>
            </form>
        </div>
        <% } else { %>
        <div class="mb-3">
            <button type="submit"
                    class="flex items-center text-white bg-gray-200 hover:bg-gray-300 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                Avanti
                <svg class="w-4 h-4 ml-3 text-gray-800 text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 10">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M1 5h12m0 0L9 1m4 4L9 9"/>
                </svg>
            </button>
        </div>
        <% } %>
    </div>

    <!-- error message -->
    <%
        String errorCreationMessage = (session.getAttribute("errorCreationMessage") != null) ? (String) session.getAttribute("errorCreationMessage") : null;
    %>

    <% if (errorCreationMessage != null) { %>
    <div class="flex p-4 mt-4 mb-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400" role="alert">
        <svg aria-hidden="true" class="flex-shrink-0 inline w-5 h-5 mr-3" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clip-rule="evenodd"></path></svg>
        <span class="sr-only">Info</span>
        <div>
            <span class="font-medium">
                <%= errorCreationMessage %>
            </span>
        </div>
    </div>
    <% } %>

    <% session.removeAttribute("errorCreationMessage"); %>

    <!-- messages -->
    <% if (threadMessages != null && !threadMessages.isEmpty()) { %>
    <!-- display messages for this thread -->
    <% for (Message m : threadMessages) { %>
    <% User user = m.getUser(); %>
    <div class="w-full mb-5 p-6 bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
        <div class="flex">
            <h5 class="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                <%= user.getUsername() %>
            </h5>
            <h5 class="mb-2 ml-1 text-2xl tracking-tight text-gray-900 dark:text-white">
                <%= "(" + user.getName() + " " + user.getSurname() + ")" %>
            </h5>
        </div>
        <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">
            <%= dateFormat.format(m.getDate()) %>
        </p>
        <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">
            <%= m.getText() %>
        </p>
        <% if (m.getImage() != null) { %>
        <img src="<%= request.getContextPath()%>/message_images/<%= m.getImage() %>" style="height: 50%"
             alt="Immagine allegata al messaggio">
        <% } %>

        <hr class="h-px my-8 bg-gray-200 border-0 dark:bg-gray-700">

        <%-- display comments for this message --%>
        <% List<Comment> messageComments = m.getComments(); %>
        <% if (messageComments != null && !messageComments.isEmpty()) { %>
        <div>
            <h5 class="text-xl mb-5 font-bold dark:text-white">Commenti:</h5>
            <% for (Comment c : messageComments) { %>
            <div class="w-full mb-3 p-6 bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
                <% User commentUser = c.getUser(); %>
                <div class="flex">
                    <p class="font-bold">
                        <%= commentUser.getUsername() %>
                    </p>
                    <p class="ml-1">
                        <%= "(" + commentUser.getName() + " " + commentUser.getSurname() + ")" %>:
                    </p>
                </div>
                <p>
                    <%= c.getText() %>
                </p>
            </div>
            <% } %>
        </div>
        <% } else { %>
        <h4>Non ci sono commenti da mostrare per questo messaggio.</h4>
        <% } %>

        <hr class="h-px my-8 bg-gray-200 border-0 dark:bg-gray-700">

        <%-- comment form --%>
        <h5 class="text-xl mb-5 font-bold dark:text-white">Nuovo commento:</h5>
        <form action="<%= request.getContextPath() %>/ThreadServlet" method="POST">
            <div class="mb-6 w-full">
                <label for="text" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Testo</label>
            </div>
            <div class="flex w-full">
                <div class="mb-3 w-full">
                    <input type="text" name="text" id="text" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" required>
                </div>
                <div>
                    <button type="submit" class="ml-3 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                        Commenta
                    </button>
                </div>

            </div>
            <input type="hidden" name="page" value="<%= request.getAttribute("page") %>">
            <input type="hidden" name="message_id" value="<%= m.getId() %>">
        </form>

        <!-- error message -->
        <%
            String errorCreationComment = (session.getAttribute("errorCreationComment") != null) ? (String) session.getAttribute("errorCreationComment") : null;
        %>

        <% if (errorCreationComment != null) { %>
        <div class="flex p-4 mb-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400" role="alert">
            <svg aria-hidden="true" class="flex-shrink-0 inline w-5 h-5 mr-3" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clip-rule="evenodd"></path></svg>
            <span class="sr-only">Info</span>
            <div>
            <span class="font-medium">
                <%= errorCreationComment %>
            </span>
            </div>
        </div>
        <% } %>

        <% session.removeAttribute("errorCreationComment"); %>
    </div>
    <% } %>
    <% } else { %>
    <p>Non ci sono messaggi da mostrare per questo thread.</p>
    <% } %>
</div>
</body>
</html>
