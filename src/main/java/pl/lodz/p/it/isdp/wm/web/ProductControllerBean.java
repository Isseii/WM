package pl.lodz.p.it.isdp.wm.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import pl.lodz.p.it.isdp.wm.dto.ProductDTO;
import pl.lodz.p.it.isdp.wm.ejb.endpoint.ProductEndpoint;
import pl.lodz.p.it.isdp.wm.exception.AppBaseException;

@Named(value = "productControllerBean")
@SessionScoped
public class ProductControllerBean implements Serializable {

    @EJB
    private ProductEndpoint productEndpoint;

    private ProductDTO selectedProductDTO;

    private int lastActionMethod = 0;

    public ProductControllerBean() {
    }

    public ProductDTO getSelectedProductDTO() {
        return selectedProductDTO;
    }

    public void setSelectedProductDTO(ProductDTO selectedProductDTO) {
        this.selectedProductDTO = selectedProductDTO;
    }

    void createNewProduct(ProductDTO productDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = productDTO.hashCode() + 1;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = productEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                productEndpoint.createNewProduct(productDTO); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (productEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    void selectProductForChange(ProductDTO productDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = productDTO.hashCode() + 2;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = productEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                selectedProductDTO = productEndpoint.rememberSelectedProductInState(productDTO.getProductSymbol()); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (productEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    void deleteProduct(ProductDTO productDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = productDTO.hashCode() + 3;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = productEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                productEndpoint.deleteProduct(productDTO); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (productEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    void editProduct(ProductDTO productDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = productDTO.hashCode() + 4;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = productEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                productEndpoint.editProduct(productDTO); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (productEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

}
