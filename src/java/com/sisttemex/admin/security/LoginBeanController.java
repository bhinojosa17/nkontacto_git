

  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sisttemex.admin.security;

import com.sisttemex.admin.security.util.JsfUtil;
import com.sisttemex.util.Constants;
import com.sisttemex.util.Utilities;
import java.io.Serializable;
import java.util.*;
import java.util.Date;
import java.util.ResourceBundle;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Bruce Hinojosa
 */
@ManagedBean
@SessionScoped
public class LoginBeanController implements Serializable {

    /**
     * @return the secMenus
     */
    
//Utilizamos este bean  para el Login pero también para instanciar variables globales
    private LoginBean logged;
    private SecUsers user;
    private static SecUsersController secUserController;
    
    /**
     * Creates a new instance of LoginController
     */
    public LoginBeanController() {

        logged = new LoginBean();
     
       
    }

    public static SecUsersController getUsers() {
        secUserController = (SecUsersController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "secUsersController");
        return secUserController;
    }
    
   

    public String login() {

        String response = null;
        user = getUsers().findLogin(logged.getUsername(), logged.getPassword());
        if (user != null) {
            logged.setToday(Utilities.dateExtended(new Date()));
            logged.setUsername(user.getUsername());
            logged.setPassword(user.getPassword());
            logged.setUserSession(user);
       //Colocamos el objeto en sesiÃ³n ya que el redirect-true lo anula
              FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(Constants.USER_LOGGED, logged);
            response =Constants.REDIRECT_MAIN;
        
        } else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle(Constants.MY_BUNDLE).getString("NotAllowed"));
        }

        return response;
    }

    public String doLogout() {
         FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(false);
        httpSession.invalidate();
        return Constants.REDIRECT_LOGIN;

    }
    
 

    /**
     * @return the logged
     */
    public LoginBean getLogged() {
        return logged;
    }

    /**
     * @param logged the logged to set
     */
    public void setLogged(LoginBean logged) {
        this.logged = logged;
    }

    /**
     * @return the tabView
     */
  
 
 
    

}
