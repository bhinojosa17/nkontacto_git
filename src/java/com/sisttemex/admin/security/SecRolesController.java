package com.sisttemex.admin.security;

import com.sisttemex.admin.security.util.JsfUtil;
import com.sisttemex.admin.security.util.PaginationHelper;
import com.sisttemex.util.Constants;
import com.sisttemex.util.Utilities;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
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

@Named("secRolesController")
@SessionScoped
public class SecRolesController implements Serializable {

    private SecRoles current;
    private DataModel items = null;
    @EJB
    private com.sisttemex.admin.security.SecRolesFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private static LoginBeanController loginBeanController;
  
    

    public SecRolesController() {
         
    }
    
  

    public SecRoles getSelected() {
        if (current == null) {
            current = new SecRoles();
            selectedItemIndex = -1;
        }
        return current;
    }

    private SecRolesFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(20) {

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
        current = (SecRoles) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return Constants.REDIRECT_VIEW;
    }

    public String prepareCreate() {
     
        current = new SecRoles();
        selectedItemIndex = -1;
        return Constants.REDIRECT_CREATE;
    }

    public String create() {
        try {
            current.setUpdatedOn(Utilities.createTimestamp());
            current.setUpdatedBy(Utilities.getCurrentUser().getUserSession());
            getFacade().create(current);
            recreatePagination();
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("SecRolesCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (SecRoles) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return Constants.REDIRECT_EDIT;
    }

    public String update() {
        try {

            current.setUpdatedOn(Utilities.createTimestamp());
            current.setUpdatedBy(Utilities.getCurrentUser().getUserSession());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("SecRolesUpdated"));
            return Constants.REDIRECT_VIEW;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("PersistenceErrorOccured"));
            return null;
        }
    }
//Este método ya no se utiliza debido a que se quitó la paginación
    public String destroy() {
        current = (SecRoles) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        current = null;
        recreatePagination();
        recreateModel();
        return Constants.REDIRECT_CREATE;
    }

    public String destroyAndView() {
        performDestroy();
        current = null;
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("SecRolesDeleted"));
            return Constants.REDIRECT_VIEW;
        } else {
            // all items were removed - go back to list
            recreateModel();
            return Constants.REDIRECT_CREATE;
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("SecRolesDeleted"));

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
        return Constants.REDIRECT_;
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return Constants.REDIRECT_;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false, "");
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true, Constants.SELECT_ROL);
    }

    public SecRoles getSecRoles(java.lang.String id) {
        return ejbFacade.find(id);
    }

    public List<SecRoles> getRolesByProfile(String profileIdReq) {
        Query query = getFacade().getEntityManager().createNamedQuery("SecRoles.findByProfileId", SecRoles.class);
        query.setParameter("profileId", profileIdReq);
         List<SecRoles> results = query.getResultList();
//  getFacade().getEntityManager().getEntityManagerFactory().getCache().evictAll();
return results;
    }
    
    
      public List<SecRoles> getRolesMissingProfile(String profileId) {
        Query query = getFacade().getEntityManager().createNamedQuery("SecRoles.findSuggestRoles", SecRoles.class);
        query.setParameter("profileId", profileId);
         List<SecRoles> results = query.getResultList();
 // getFacade().getEntityManager().getEntityManagerFactory().getCache().evictAll();
return results;
    }
    
        public List<SecRoles> getAllRoles() {
          List<SecRoles> results = getFacade().findAll();
          System.out.println("Obtenidos..." + results.size());
return results;
    }
     
      

    @FacesConverter(forClass = SecRoles.class, value = "secRolesConverter")
    public static class SecRolesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SecRolesController controller = (SecRolesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "secRolesController");
            return controller.getSecRoles(getKey(value));
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
            if (object instanceof SecRoles) {
                SecRoles o = (SecRoles) object;
                return getStringKey(o.getRoleId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SecRoles.class.getName());
            }
        }

    }

}
