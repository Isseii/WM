package pl.lodz.p.it.isdp.wm.web;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import pl.lodz.p.it.isdp.wm.dto.ProductDTO;
import pl.lodz.p.it.isdp.wm.ejb.endpoint.ProductEndpoint;
import pl.lodz.p.it.isdp.wm.exception.AppBaseException;
import pl.lodz.p.it.isdp.wm.web.utils.ContextUtils;

@Named(value = "listProductsPageBean")
@ViewScoped
public class ListProductsPageBean implements Serializable {

    @EJB
    private ProductEndpoint productEndpoint;

    @Inject
    private ProductControllerBean productControllerBean;

    private List<ProductDTO> listProducts;

    private DataModel<ProductDTO> dataModelProducts;

    public ListProductsPageBean() {
    }

    public DataModel<ProductDTO> getDataModelProducts() {
        return dataModelProducts;
    }

    @PostConstruct
    public void initListProducts() {
        try {
            listProducts = productEndpoint.listProducts();
        } catch (AppBaseException ex) {
            Logger.getLogger(ListProductsPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
        }
        dataModelProducts = new ListDataModel<>(listProducts);
    }

    public String editProductAction(ProductDTO productDTO) {
        try {
            productControllerBean.selectProductForChange(productDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(ListProductsPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        initListProducts();
        return "editProduct";
    }

    public String deleteSelectedProductAction(ProductDTO productDTO) {
        try {
            productControllerBean.selectProductForChange(productDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(ListProductsPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        initListProducts();
        return "deleteProduct";
    }

}
