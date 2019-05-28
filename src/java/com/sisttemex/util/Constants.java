/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sisttemex.util;

/**
 *
 * @author Bruce Hinojosa
 */
public interface Constants {

  
    public static int EDIT_PROFILE_ROLES = 1;
    public static int CREATE_PROFILE_ROLES = 2;

    public final String USER_LOGGED = "user_logged";
    public final String EMPTY_VALUE_SELECTED = "";

    public final String BUNDLE_DEFAULT = "/com/sisttemex/util/Bundle";
    public final String MY_BUNDLE = "/com/sisttemex/util/MyMessages";

    //Cadenas de RedirecciÃ³n
    //Redirects principales
    public final String REDIRECT_MAIN = "/admin/security/secUsers/Create";
    public final String REDIRECT_LOGIN = "/Login?faces-redirect=true";

    //Redirects genÃ©ricos
    public final String REDIRECT_= "?faces-redirect=true";
  public final String REDIRECT_LIST = "List";
    public final String REDIRECT_CREATE = "Create?faces-redirect=true";
    public final String REDIRECT_EDIT = "Edit?faces-redirect=true";
    public final String REDIRECT_VIEW = "View?faces-redirect=true";
            //redirects a Perfiles

    //Constantes de textos para inicio de Combos
    public final String SELECT_PROFILE = "Seleccione Perfil";
    public final String SELECT_DOMAIN = "Seleccione Dominio";
    public final String SELECT_COMPANY = "Seleccione Compañía";
    public final String SELECT_COUNTRY = "Seleccione País";
    public final String SELECT_STATE = "Seleccione Estado";
    public final String SELECT_CITY = "Seleccione Ciudad";
    public final String SELECT_ROL = "Seleccione Rol";
 
    //Constantes para el índice del TabView
   
    public final String TAB_MANAGEMENT = "6";
    public final int TAB_MENU_BD=1;
    
}
