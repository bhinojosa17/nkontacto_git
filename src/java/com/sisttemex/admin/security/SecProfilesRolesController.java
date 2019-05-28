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
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.primefaces.model.DualListModel;

@Named("secProfilesRolesController")
@SessionScoped
@Transactional

public class SecProfilesRolesController implements Serializable {

    private final Logger logger = Logger.getLogger(SecProfilesRolesController.class);
    private static SecRolesController secRolesController;
    private static SecProfilesController secProfilesController;
    private int editCreate;
    private SecProfilesRoles current;
    private DataModel items = null;
    @EJB
    private com.sisttemex.admin.security.SecProfilesRolesFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<SecRoles> leftAvailable;
    private List<SecRoles> rightAvailable;
    private DualListModel<SecRoles> rolesDualListModel;
    private SecProfiles profileSelected;

    public SecProfilesRolesController() {
        rolesDualListModel = new DualListModel(new ArrayList<SecRoles>(), new ArrayList<SecRoles>());

    }

    //obtener referencia al Controller de Roles
    public static SecRolesController getRolesController() {
        secRolesController = (SecRolesController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "secRolesController");
        return secRolesController;
    }

    //obtener referencia al Controller de Perfiles
    public static SecProfilesController getProfilesController() {
        secProfilesController = (SecProfilesController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "secProfilesController");
        return secProfilesController;
    }

    public void handleRolesChange() {
       
        rightAvailable = new ArrayList();
        leftAvailable = new ArrayList();
         String profileIdSelected;
        try {
            profileIdSelected = getProfileSelected().getProfileId();
        } catch (Exception ex) {
            rolesDualListModel.getTarget().clear();
            rolesDualListModel.getSource().clear();
            return;

        }
        rightAvailable = obtainRolesAssigned(profileIdSelected);
         if (rightAvailable.isEmpty()) {
            leftAvailable = obtainAllRoles();
        } else {
            leftAvailable = obtainRolesMissing(profileIdSelected);
        }
        rolesDualListModel.setTarget(rightAvailable);
        rolesDualListModel.setSource(leftAvailable);

    }

    public SecProfilesRoles getSelected() {
        if (current == null) {
            current = new SecProfilesRoles();
            current.setSecProfilesRolesPK(new com.sisttemex.admin.security.SecProfilesRolesPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private SecProfilesRolesFacade getFacade() {
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
        current = (SecProfilesRoles) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return Constants.REDIRECT_VIEW;
    }

    public String prepareCreate() {
        current = new SecProfilesRoles();
        current.setSecProfilesRolesPK(new com.sisttemex.admin.security.SecProfilesRolesPK());
        selectedItemIndex = -1;
        return Constants.REDIRECT_CREATE;
    }

    public String create() {
     
         try {
           
            List<SecRoles> targ = rolesDualListModel.getTarget();
                perfomDestroyByProfile(getProfileSelected().getProfileId());
                for (int i = 0; i < targ.size(); i++) {
                SecProfilesRoles newProfileRole = new SecProfilesRoles((new SecProfilesRolesPK()));
                newProfileRole.setUpdatedOn(Utilities.createTimestamp());
                newProfileRole.setUpdatedBy(Utilities.getCurrentUser().getUserSession());
                newProfileRole.getSecProfilesRolesPK().setProfileId(getProfileSelected().getProfileId());
                newProfileRole.getSecProfilesRolesPK().setRoleId((String.valueOf(targ.get(i))));
                newProfileRole.setStatusReg(getSelected().getStatusReg());
                getFacade().create(newProfileRole);
                 }
                                   JsfUtil.addSuccessMessage(ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("SecProfilesRolesCreated"));
 
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("PersistenceErrorOccured"));
            return null;
        }
           return prepareCreate();
    }

    public String prepareEdit() {
        current = (SecProfilesRoles) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return Constants.REDIRECT_EDIT;
    }

    public String update() {
        try {
            current.getSecProfilesRolesPK().setProfileId(current.getSecProfiles().getProfileId());
            current.getSecProfilesRolesPK().setRoleId(current.getSecRoles().getRoleId());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("SecProfilesRolesUpdated"));
            return Constants.REDIRECT_VIEW;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (SecProfilesRoles) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("SecProfilesRolesDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(Constants.BUNDLE_DEFAULT).getString("PersistenceErrorOccured"));
        }
    }
    
    private void perfomDestroyByProfile (String profile)
    {
        try
        {
          
              Query query = getFacade().getEntityManager().createNamedQuery("SecProfilesRoles.deleteFromProfile", SecProfilesRoles.class);
         query.setParameter("profileId", profile);
         logger.error("Se eliminarán todos los registros del perfil  "+ profile );
     int result= query.executeUpdate();
     logger.info("Eliminados: " + result + " registros");
        }catch (Exception e) {
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
        return Constants.REDIRECT_LIST;
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return Constants.REDIRECT_LIST;
    }

   
    public List<SecProfilesRoles> getItemsAvailable() {
        return ejbFacade.findAll();
    }

   
    public SecProfilesRoles getSecProfilesRoles(com.sisttemex.admin.security.SecProfilesRolesPK id) {
        return ejbFacade.find(id);
    }

    public List<SecProfiles> getFillProfiles() {
        return getProfilesController().getAllProfiles();

    }

    public List<SecRoles> obtainRolesAssigned(String profile) {
        List resp = getRolesController().getRolesByProfile(profile);
        //   Convertimos los resultados que vienen como Vector a ArrayList
        ArrayList<SecRoles> roles = new ArrayList(resp);

        return roles;

    }

    public List<SecRoles> obtainRolesMissing(String profile) {
        List resp = getRolesController().getRolesMissingProfile(profile);
        ArrayList<SecRoles> roles = new ArrayList(resp);

        return roles;
    }

    public List<SecRoles> obtainAllRoles() {
        List resp = getRolesController().getAllRoles();
        ArrayList<SecRoles> roles = new ArrayList(resp);

        return roles;
    }

    /**
     * @return the leftAvailable
     */
    public List<SecRoles> getLeftAvailable() {
        return leftAvailable;
    }

    /**
     * @return the rightAvailable
     */
    public List<SecRoles> getRightAvailable() {
        return rightAvailable;
    }

    /**
     * @return the profileSelected
     */
    public SecProfiles getProfileSelected() {
        return profileSelected;
    }

    /**
     * @param profileSelected the profileSelected to set
     */
    public void setProfileSelected(SecProfiles profileSelected) {
        this.profileSelected = profileSelected;
    }

    /**
     * @return the rolesDualListModel
     */
    public DualListModel<SecRoles> getRolesDualListModel() {
        return rolesDualListModel;
    }

    /**
     * @param rolesDualListModel the rolesDualListModel to set
     */
    public void setRolesDualListModel(DualListModel<SecRoles> rolesDualListModel) {
        this.rolesDualListModel = rolesDualListModel;
    }

     /**
     * @return the editCreate
     */
    public int getEditCreate() {
        return editCreate;
    }

    /**
     * @param editCreate the editCreate to set
     */
    public void setEditCreate(int editCreate) {
        this.editCreate = editCreate;
    }

    @FacesConverter(forClass = SecProfilesRoles.class, value = "secprofConverter")
    public static class SecProfilesRolesControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SecProfilesRolesController controller = (SecProfilesRolesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "secProfilesRolesController");
            return controller.getSecProfilesRoles(getKey(value));
        }

        com.sisttemex.admin.security.SecProfilesRolesPK getKey(String value) {
            com.sisttemex.admin.security.SecProfilesRolesPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.sisttemex.admin.security.SecProfilesRolesPK();
            key.setProfileId(values[0]);
            key.setRoleId(values[1]);
            return key;
        }

        String getStringKey(com.sisttemex.admin.security.SecProfilesRolesPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getProfileId());
            sb.append(SEPARATOR);
            sb.append(value.getRoleId());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof SecProfilesRoles) {
                SecProfilesRoles o = (SecProfilesRoles) object;
                return getStringKey(o.getSecProfilesRolesPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SecProfilesRoles.class.getName());
            }
        }

    }

}
