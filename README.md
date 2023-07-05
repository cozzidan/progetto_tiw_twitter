# progetto_tiw_twitter

Project Title: Twitter-like Messaging Application

Description:
This repository contains a university project that implements a web application for managing a messaging stream similar to Twitter. The application allows users to register and login through a public webpage with the appropriate form.

During registration, the system validates the syntax of the email address and ensures that the "password" and "confirm password" fields match. The registration process also checks the uniqueness of the chosen username.

Each message is stored in the database with attributes such as text, date, and the user who posted it. Messages can optionally include an image, which is stored as a file in the server's file system where the application is deployed.

Users can create discussion threads and associate their messages with them. A thread consists of a title, creator name, creation date, and can contain one or more messages. Other users can comment on the messages within a thread. A comment includes the comment text and the username of the creator.

Upon accessing the DASHBOARD, users are presented with a list of threads they have created and a list of threads created by other users. Both lists are sorted by the creation date in descending order. Clicking on a thread in the DASHBOARD lists opens the THREAD_PAGE, which displays the messages belonging to that thread. If a thread contains more than ten messages, commands are available to view the previous and next ten messages.

The THREAD_PAGE also features a form for adding comments. Submitting a comment using the "SEND" button refreshes the THREAD_PAGE, displaying all the updated data for the same thread. The THREAD_PAGE includes a link to return to the HOME_PAGE. The application provides a logout feature for users.

Please note that this project is a university assignment and should not be considered a fully functional real-world application.
