/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sisttemex.converter;

import com.sisttemex.admin.security.SecProfiles;
import com.sisttemex.admin.security.SecProfilesController;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Bruce Hinojosa
 */
 @FacesConverter(forClass = SecProfiles.class, value = "secProfilesConverter")
 public class ProfilesConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SecProfilesController controller = (SecProfilesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "secProfilesController");
            return controller.getSecProfiles(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof SecProfiles) {
                SecProfiles o = (SecProfiles) object;
                return getStringKey(o.getProfileId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SecProfiles.class.getName());
            }
        }

    }