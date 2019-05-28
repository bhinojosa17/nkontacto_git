package com.sisttemex.admin.security;

import com.sisttemex.admin.security.util.JsfUtil;
import com.sisttemex.admin.security.util.PaginationHelper;
import com.sisttemex.util.Constants;

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
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.primefaces.component.tabview.Tab;
import org.primefaces.event.TabChangeEvent;

@Named("secMenuController")
@SessionScoped
public class SecMenuController implements Serializable {
 private final Logger logger = Logger.getLogger(SecMenuController.class);
    private SecMenu current;
    private DataModel items = null;
    @EJB
    private com.sisttemex.admin.security.SecMenuFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<SecMenu> menuLeft;
    private int tabView;
    public SecMenuController() {
             }
    
   

    public SecMenu getSelected() {
        if (current == null) {
            current = new SecMenu();
            selectedItemIndex = -1;
        }
        return current;
    }

    private SecMenuFacade getFacade() {
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
        current = (SecMenu) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new SecMenu();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SecMenuCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (SecMenu) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    
    
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SecMenuUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (SecMenu) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SecMenuDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
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
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }
 
    public SecMenu getSecMenu(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    
    
    //Métodos definidos
    public ArrayList<SecMenu> getMenusTab()
    {
   
      List<SecMenu> menus;
         Query query = getFacade().getEntityManager().createNamedQuery("SecMenu.findByIdParent", SecMenu.class);
         query.setParameter("idParent", Constants.TAB_MENU_BD);
          menus = query.getResultList();
               ArrayList<SecMenu> tabsFound= new ArrayList<>(menus);
               getFacade().getEntityManager().getEntityManagerFactory().getCache().evictAll();
              return tabsFound;
    }

 
    /**
     * @return the menuLeft
     */
    public List<SecMenu> getMenuLeft() {
        return this.menuLeft;
    }

    /**
     * @param menuLeft the menuLeft to set
     */
    public void setMenuLeft(List<SecMenu> menuLeft) {
        this.menuLeft = menuLeft;
    }
  
    
      

       
       public void updateTabView() {
  
logger.info("El tab de menú es: "  );
//here activeTabIndex has the correct value.
}
    /**
     * @return the tabView
     */
    public int getTabView() {
        return tabView;
    }

    /**
     * @param tabView the tabView to set
     */
    public void setTabView(int tabView) {
        this.tabView = tabView;
    }
   

    @FacesConverter(forClass = SecMenu.class)
    public static class SecMenuControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SecMenuController controller = (SecMenuController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "secMenuController");
            return controller.getSecMenu(getKey(value));
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
            if (object instanceof SecMenu) {
                SecMenu o = (SecMenu) object;
                return getStringKey(o.getItemId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SecMenu.class.getName());
            }
        }

    }

}
