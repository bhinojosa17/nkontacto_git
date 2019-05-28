/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sisttemex.converter;
   
import com.sisttemex.admin.security.SecRoles;
 import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

 
@FacesConverter(forClass = SecRoles.class, value="secRolesPickListConverter")
public class SecRolesPickListConverter implements Converter {
  
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
         System.out.println ("Convirtiendo getAsObjct");
         return getObjectFromUIPickListComponent(component,value);
    }
 
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        String string;
          if(object == null){
            string="";
        }else{
            try{
                string = ((SecRoles)object).getRoleId();
            }catch(ClassCastException cce){
                
                throw new ConverterException();
            }
        }
        return string;
    }
 
    @SuppressWarnings("unchecked")
    private SecRoles getObjectFromUIPickListComponent(UIComponent component, String value) {
         final DualListModel<SecRoles> dualList;
        try{
            dualList = (DualListModel<SecRoles>) ((PickList)component).getValue();
            SecRoles roles = getObjectFromList(dualList.getSource(),value);
            if(roles==null){
                roles = getObjectFromList(dualList.getTarget(), value);
            }
             
            return roles;
        }catch(  Exception cce){
            throw new ConverterException();
        }
    }
 
    private SecRoles getObjectFromList(final List<?> list, final String id) {
         for(final Object object:list){
            final SecRoles rol = (SecRoles) object;
            if(rol.getRoleId().equals(id)){
                return rol;
            }
        }
        return null;
    }
}