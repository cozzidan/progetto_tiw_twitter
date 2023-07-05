package com.elis.twitter.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "surname", length = 30, nullable = false)
    private String surname;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "username", columnDefinition = "varchar(30) collate utf8_bin", nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 30, nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("date desc, id desc")
    private List<Thread> threads = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("date desc, id desc")
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    public User() {
    }

    public User(String surname, String name, LocalDate dateOfBirth, String email, String username, String password) {
        this.surname = surname;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(Long id, String surname, String name, LocalDate dateOfBirth, String email, String username, String password, List<Thread> threads, List<Message> messages, List<Comment> comments) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.username = username;
        this.password = password;
        this.threads = threads;
        this.messages = messages;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", threads=" + threads +
                ", messages=" + messages +
                ", comments=" + comments +
                '}';
    }
}
