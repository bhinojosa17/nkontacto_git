package com.sisttemex.admin.security;

import com.sisttemex.admin.security.util.JsfUtil;
import com.sisttemex.admin.security.util.PaginationHelper;
import com.sisttemex.util.Constants;
import com.sisttemex.util.Utilities;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.Query;
import org.apache.log4j.Logger;

@Named("secUsersController")
@SessionScoped
public class SecUsersController implements Serializable {
    private static SecProfilesController secProfilesController;
  private final Logger logger = Logger.getLogger(SecUsersProfilesController.class);
    private SecUsers current;
    private DataModel items = null;
    @EJB
    private com.sisttemex.admin.security.SecUsersFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
     private static SecUsersProfilesController secUsersProfilesController;
    
    private List<SecProfiles> profilesSelected;
    public SecUsersController() {
    }
 
     
     //obtener referencia al Controller de Usuarios-Perfiles
    public static SecUsersProfilesController getUsersProfilesController() {
        secUsersProfilesController = (SecUsersProfilesController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "secUsersProfilesController");
        return secUsersProfilesController;
    }
    
      public static SecProfilesController getProfilesController() {
        secProfilesController = (SecProfilesController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "secProfilesController");
        return secProfilesController;
    }
      
       
    public SecUsers getSelected() {
        if (current == null) {
            current = new SecUsers();
            selectedItemIndex = -1;
        }
        return current;
    }

    private SecUsersFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return Constants.REDIRECT_LIST;
    }

    public String prepareView() {
        current = (SecUsers) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return Constants.REDIRECT_VIEW;
    }

    public String prepareCreate() {
        current = new SecUsers();
        selectedItemIndex = -1;
        return Constants.REDIRECT_CREATE ;
    }

    
    
    public String create() {
        try {
            Date timeNow= Utilities.createTimestamp();
            current.setCreatedOn(timeNow);
            current.setLastSessionDate(timeNow);
             current.setUpdatedOn(timeNow);
            current.setUpdatedBy(Utilities.getCurrentUser().getUserSession());
             current.setCreatedBy(Utilities.getCurrentUser().getUserSession());
          
            //Insertamos el registro
             getFacade().create(current);
             logger.info("Registro insertado en usuarios");
             logger.info("Los profiles seleccionados son: " + profilesSelected.size() + " y el perfil a eliminar es " + current.getUserId());
             
             //Insertamos los registros de los perfiles seleccionados en la tabla usuarios-perfiles
              getUsersProfilesController().perfomDestroyByUser((current.getUserId()));
             logger.info("Se eliminan registros");
              
             for (int i=0; i<profilesSelected.size();i++)
             {
                     logger.info("Insertando en user-profiles");
             SecUsersProfiles userProf= new SecUsersProfiles();
             SecUsersProfilesPK userProfKey= new SecUsersProfilesPK();
             userProfKey.setProfileId(profilesSelected.get(i).getProfileId());
             userProfKey.setUserId(current.getUserId());
              userProf.setSecUsersProfilesPK(userProfKey);
             userProf.setUpdatedOn(timeNow);
             userProf.setUpdatedBy(Utilities.getCurrentUser().getUserSession());
             getUsersProfilesController().create(userProf);
              }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("SecUsersCreated"));
            return prepareCreate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (SecUsers) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return Constants.REDIRECT_EDIT;
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("SecUsersUpdated"));
            return Constants.REDIRECT_VIEW;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (SecUsers) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return Constants.REDIRECT_LIST;
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return Constants.REDIRECT_VIEW ;
        } else {
            // all items were removed - go back to list
            recreateModel();
            return Constants.REDIRECT_LIST;
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("SecUsersDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public SecUsers findLogin(String user, String pwd) {
         SecUsers found = null;
         Query query = getFacade().getEntityManager().createNamedQuery("SecUsers.findByUserPwd", SecUsers.class);
         query.setParameter("username", user);
         query.setParameter("password", pwd);
         List results = query.getResultList();
         if (!results.isEmpty()) {
            found = (SecUsers) results.get(0);
        }
         return found;
    }
    
    
      public List<SecProfiles> getAllProfiles() {
        return getProfilesController().getAllProfiles();

    }
 
    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return Constants.REDIRECT_LIST;
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return Constants.REDIRECT_LIST;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false, "");
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true, "");
    }

    public SecUsers getSecUsers(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     * @return the profilesSelected
     */
    public List<SecProfiles> getProfilesSelected() {
        return profilesSelected;
    }

    /**
     * @param profilesSelected the profilesSelected to set
     */
    public void setProfilesSelected(List<SecProfiles> profilesSelected) {
        this.profilesSelected = profilesSelected;
    }

    /**
     * @return the profileSelected
     */
    
     


    @FacesConverter(forClass = SecUsers.class)
    public static class SecUsersControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SecUsersController controller = (SecUsersController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "secUsersController");
            return controller.getSecUsers(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof SecUsers) {
                SecUsers o = (SecUsers) object;
                return getStringKey(o.getUserId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SecUsers.class.getName());
            }
        }

    }

}
