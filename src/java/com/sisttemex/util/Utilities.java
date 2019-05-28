/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sisttemex.util;

import com.sisttemex.admin.security.LoginBean;
import java.util.Date;
import javax.faces.context.FacesContext;

/**
 *
 * @author Bruce Hinojosa
 */
public class Utilities {
//Método que devuelve el usuario actual
    public static LoginBean getCurrentUser() {
        return (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_logged");

    }
    //Crea un timestamp para insertarlo en los campos auditables
    public static Date createTimestamp()
    {
        Date now= new Date();
        return now;
    
    }
    /** 
     *  Convierte un dato de tipo fecha en una fecha cadena del tipo XX de XXXXXXX de XXXX
     * 
     * @param dateService
     * @return 
     */
    public static String dateExtended(Date dateService)
  {
	
    int day = dateService.getDate();
    int month = dateService.getMonth();
    int year = dateService.getYear() + 1900;
    String monthText = "";
    String responseDate = "";
    if ((day > 0) && (month >= 0))
    {
      switch (month)
      {
      case 0: 
        monthText = "enero";
        break;
      case 1: 
        monthText = "febrero";
        break;
      case 2: 
        monthText = "marzo";
        break;
      case 3: 
        monthText = "abril";
        break;
      case 4: 
        monthText = "mayo";
        break;
      case 5: 
        monthText = "junio";
        break;
      case 6: 
        monthText = "julio";
        break;
      case 7: 
        monthText = "agosto";
        break;
      case 8: 
        monthText = "septiembre";
        break;
      case 9: 
        monthText = "octubre";
        break;
      case 10: 
        monthText = "noviembre";
        break;
      case 11: 
        monthText = "diciembre";
      }
      responseDate = day + " de " + monthText + " de " + year;
    }
    else
    {
      responseDate = "Fecha no disponible";
    }
    return responseDate;
  }
}
