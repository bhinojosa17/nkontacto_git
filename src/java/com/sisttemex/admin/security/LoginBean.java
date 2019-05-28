/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sisttemex.admin.security;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
 
/**
 *
 * @author Bruce Hinojosa
 */
  @ManagedBean
 @SessionScoped
public class LoginBean implements Serializable{
      private String username;
      private String password;
      private String today;
    private SecUsers userSession;
     private Boolean sessionActive;
     private Boolean credentialsValid;
     
     
    
    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }

    /**
     * @return the userSession
     */
    public SecUsers getUserSession() {
        return userSession;
    }

    /**
     * @param userSession the userSession to set
     */
    public void setUserSession(SecUsers userSession) {
        this.userSession = userSession;
    }

    /**
     * @return the sessionActive
     */
    public Boolean getSessionActive() {
        return sessionActive;
    }

    /**
     * @param sessionActive the sessionActive to set
     */
    public void setSessionActive(Boolean sessionActive) {
        this.sessionActive = sessionActive;
    }

    /**
     * @return the credentialsValid
     */
    public Boolean getCredentialsValid() {
        return credentialsValid;
    }

    /**
     * @param credentialsValid the credentialsValid to set
     */
    public void setCredentialsValid(Boolean credentialsValid) {
        this.credentialsValid = credentialsValid;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the today
     */
    public String getToday() {
        return today;
    }

    /**
     * @param today the today to set
     */
    public void setToday(String today) {
        this.today = today;
    }
 
 
    
}
