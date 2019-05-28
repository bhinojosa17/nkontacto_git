package com.sisttemex.admin.security;

import com.sisttemex.util.Constants;
import com.sisttemex.util.JsfUtil;
import com.sisttemex.util.PaginationHelper;
import java.io.Serializable;
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
import javax.transaction.Transactional;
import org.apache.log4j.Logger;

@Named("secUsersProfilesController")
@SessionScoped
@Transactional 
public class SecUsersProfilesController implements Serializable {

    private final Logger logger = Logger.getLogger(SecUsersProfilesController.class);
    private SecUsersProfiles current;
    private DataModel items = null;
    @EJB
    private com.sisttemex.admin.security.SecUsersProfilesFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public SecUsersProfilesController() {
    }

    public SecUsersProfiles getSelected() {
        if (current == null) {
            current = new SecUsersProfiles();
            current.setSecUsersProfilesPK(new com.sisttemex.admin.security.SecUsersProfilesPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private SecUsersProfilesFacade getFacade() {
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
        return "List";
    }

    public String prepareView() {
        current = (SecUsersProfiles) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new SecUsersProfiles();
        current.setSecUsersProfilesPK(new com.sisttemex.admin.security.SecUsersProfilesPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create(SecUsersProfiles user) {

        try {
           getFacade().create(user);
           } catch (Exception e) {
            com.sisttemex.admin.security.util.JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("PersistenceErrorOccured"));
            return null;
        }
        return prepareCreate();
    }

    public String prepareEdit() {
        current = (SecUsersProfiles) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle1").getString("SecUsersProfilesUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle1").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (SecUsersProfiles) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle1").getString("SecUsersProfilesDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle1").getString("PersistenceErrorOccured"));
        }
    }

    public int  perfomDestroyByUser(int user) {
         int result = 0;
        try {
            logger.error("Se eliminarán todos los registros del usuario  ");
            Query query = getFacade().getEntityManager().createNamedQuery("SecUsersProfiles.deleteFromUser", SecUsersProfiles.class);
            query.setParameter("userId", user);
            logger.error("Se eliminarán todos los registros del usuario  " + user);
            result = query.executeUpdate();
            logger.error("Eliminados: " + result + " registros");
        } catch (Exception e) {
            logger.error(e);
            com.sisttemex.admin.security.util.JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("PersistenceErrorOccured"));

        }
        return result;
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
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SecUsersProfiles getSecUsersProfiles(com.sisttemex.admin.security.SecUsersProfilesPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = SecUsersProfiles.class)
    public static class SecUsersProfilesControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SecUsersProfilesController controller = (SecUsersProfilesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "secUsersProfilesController");
            return controller.getSecUsersProfiles(getKey(value));
        }

        com.sisttemex.admin.security.SecUsersProfilesPK getKey(String value) {
            com.sisttemex.admin.security.SecUsersProfilesPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.sisttemex.admin.security.SecUsersProfilesPK();
            key.setUserId(Integer.parseInt(values[0]));
            key.setProfileId(values[1]);
            return key;
        }

        String getStringKey(com.sisttemex.admin.security.SecUsersProfilesPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getUserId());
            sb.append(SEPARATOR);
            sb.append(value.getProfileId());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof SecUsersProfiles) {
                SecUsersProfiles o = (SecUsersProfiles) object;
                return getStringKey(o.getSecUsersProfilesPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SecUsersProfiles.class.getName());
            }
        }

    }

}
