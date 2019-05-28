package com.sisttemex.admin.security;

import com.sisttemex.admin.security.util.JsfUtil;
import com.sisttemex.admin.security.util.PaginationHelper;
import com.sisttemex.util.Constants;
import com.sisttemex.util.Utilities;
import java.io.Serializable;
import java.util.ArrayList;
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
import org.apache.log4j.Logger;

@Named("secProfilesController")
@SessionScoped
public class SecProfilesController implements Serializable {
  private final Logger logger = Logger.getLogger(SecProfilesController.class);
    private SecProfiles current;
    private DataModel items = null;
    @EJB
    private com.sisttemex.admin.security.SecProfilesFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public SecProfilesController() {
    }

  
    public SecProfiles getSelected() {
        if (current == null) {
            current = new SecProfiles();
            selectedItemIndex = -1;
        }
        return current;
    }

    private SecProfilesFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(4) {

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
        current = (SecProfiles) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return Constants.REDIRECT_VIEW;
    }

    public String prepareCreate() {
        current = new SecProfiles();
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
           JsfUtil.addSuccessMessage(ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("SecProfilesCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (SecProfiles) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return Constants.REDIRECT_EDIT;
    }

    public String update() {
        try {

            current.setUpdatedOn(Utilities.createTimestamp());
            current.setUpdatedBy(Utilities.getCurrentUser().getUserSession());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("SecProfilesUpdated"));
            return Constants.REDIRECT_VIEW;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (SecProfiles) getItems().getRowData();
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
        if (getSelectedItemIndex() >= 0) {
            return Constants.REDIRECT_VIEW;
        } else {
            // all items were removed - go back to list
            recreateModel();
            return Constants.REDIRECT_LIST;
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("SecProfilesDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (getSelectedItemIndex() >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (getSelectedItemIndex() >= 0) {
            current = getFacade().findRange(new int[]{getSelectedItemIndex(), getSelectedItemIndex() + 1}).get(0);
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
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false, Constants.SELECT_PROFILE);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true, Constants.SELECT_PROFILE);
    }

    public SecProfiles getSecProfiles(java.lang.String id) {
        return ejbFacade.find(id);
    }

    /**
     * @return the selectedItemIndex
     */
    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

   
     public  ArrayList<SecProfiles> getAllProfiles() {
        List results = getFacade().findAll();
        ArrayList<SecProfiles> resp= new ArrayList<SecProfiles>(results);
        if (resp.isEmpty())
        {
            try {
                throw new Exception();
            } catch (Exception ex) {
                 logger.error("No se han podido obtener perfiles de la BD");
                
            }
         }
            else
            logger.debug("Consultados: " + results.size() + " perfiles." );
       
        return resp;
    }
     
    @FacesConverter(forClass = SecProfiles.class)
    public static class SecProfilesControllerConverter implements Converter {

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

}
