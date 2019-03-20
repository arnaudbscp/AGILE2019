package fr.ulille.iut.pizzaland.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilisateurDto {
    private final static Logger logger = LoggerFactory.getLogger(UtilisateurDto.class);
    protected  long id;
    protected  String login;
    protected  String password;
    protected  String role;


    public static Logger getLogger() {
        return logger;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String toString() {
        return "Login: " + this.login + " Password: " + this.password + " Role: " + this.role;
    }
}
