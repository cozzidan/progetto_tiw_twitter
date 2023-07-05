<%@ page import="com.elis.twitter.model.User" %>
<%@ page import="com.elis.twitter.model.Thread" %><%--
  Created by IntelliJ IDEA.
  User: Daniele
  Date: 25/06/2023
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/flowbite/1.6.6/flowbite.min.css" rel="stylesheet"/>
    <title>Message</title>
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
<div class="flex items-center justify-center h-full">
    <div class="w-1/2 h-1/2">
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
                        <li>
                            <!-- logout -->
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

        <h2 class="text-center text-4xl font-bold dark:text-white mb-3">Nuovo messaggio</h2>

        <% Thread thread = (Thread) session.getAttribute("thread"); %>

        <h5 class="text-xl text-center mb-3 dark:text-white">Thread: <%= thread.getTitle() %></h5>

        <!-- message form -->
        <form action="<%= request.getContextPath() %>/MessageServlet" method="POST" enctype="multipart/form-data">
            <div class="mb-6">
                <label for="text" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Testo</label>
                <input type="text" name="text" id="text"
                       class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light"
                       required>
            </div>

            <!-- input image -->
            <div class="mb-6">
                <label for="image" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Immagine</label>
                <input class="block w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 dark:text-gray-400 focus:outline-none dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400" name="image" id="image" type="file" accept="image/*">
            </div>
            <input type="hidden" name="page" value="<%= request.getParameter("page") %>">
            <button type="submit"
                    class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                Invia
            </button>
        </form>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/flowbite/1.6.6/flowbite.min.js"></script>
</body>
</html>
