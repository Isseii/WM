package pl.lodz.p.it.isdp.wm.ejb.endpoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.isdp.wm.dto.ProductDTO;
import pl.lodz.p.it.isdp.wm.ejb.facade.OfficeAccountFacade;
import pl.lodz.p.it.isdp.wm.ejb.facade.ProductFacade;
import pl.lodz.p.it.isdp.wm.ejb.facade.StockFacade;
import pl.lodz.p.it.isdp.wm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.isdp.wm.exception.AppBaseException;
import pl.lodz.p.it.isdp.wm.exception.ProductException;
import pl.lodz.p.it.isdp.wm.model.OfficeAccount;
import pl.lodz.p.it.isdp.wm.model.Product;
import pl.lodz.p.it.isdp.wm.model.Stock;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
@RolesAllowed("Office")
public class ProductEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @EJB
    private ProductFacade productFacade;

    @EJB
    private StockFacade stockFacade;

    @EJB
    private OfficeAccountFacade officeAccountFacade;

    @Resource
    private SessionContext sessionContext;

    private Product productState;

    private OfficeAccount loadCurrentOffice() throws AppBaseException {
        //przypisanie konta użytkownika, który tworzył lub edytował produkt
        String userLogin = sessionContext.getCallerPrincipal().getName();
        OfficeAccount account = officeAccountFacade.findByLogin(userLogin);
        if (account != null) {
            return account;
        } else {
            throw AppBaseException.createExceptionNotAuthorizedAction();
        }
    }

    public void createNewProduct(ProductDTO productDTO) throws AppBaseException {
        Product product = new Product();
        product.setProductSymbol(productDTO.getProductSymbol());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice(productDTO.getPriceFromForm()));
        product.setWeight(productDTO.getWeight());
        product.setEasilyDamage(productDTO.isEasilyDamage());
        product.setCreatedBy(loadCurrentOffice());
        productFacade.create(product);
    }

    @RolesAllowed({"Office", "Warehouse"})
    public List<ProductDTO> listProducts() throws AppBaseException {
        List<Product> listAllProducts = productFacade.findProducts();
        List<ProductDTO> listProducts = new ArrayList<>();
        for (Product product : listAllProducts) {
            ProductDTO productDTO = new ProductDTO(product.getProductSymbol(), product.getDescription(), product.getPrice(), product.getWeight(), product.isEasilyDamage());
            listProducts.add(productDTO);
        }
        Collections.sort(listProducts);
        return listProducts;
    }

    public void editProduct(ProductDTO productDTO) throws AppBaseException {
        if (productState.getProductSymbol().equals(productDTO.getProductSymbol())) {
            if (productState != null) {
                productState.setDescription(productDTO.getDescription());
                productState.setDescription(productDTO.getDescription());
                productState.setPrice(productDTO.getPrice(productDTO.getPriceFromForm()));
                productState.setWeight(productDTO.getWeight());
                productState.setEasilyDamage(productDTO.isEasilyDamage());
                productFacade.edit(productState);
            }
        } else {
            throw ProductException.createExceptionWrongState(productState);
        }
    }

    public ProductDTO rememberSelectedProductInState(String productSymbol) throws AppBaseException {
        productState = productFacade.findProductBySymbol(productSymbol);
        ProductDTO productDTO = new ProductDTO(productState.getProductSymbol(), productState.getDescription(), productState.getPrice(), productState.getWeight(), productState.isEasilyDamage());
        productDTO.setPriceFromForm(productState.getPrice());
        return productDTO;
    }

    public void deleteProduct(ProductDTO productDTO) throws AppBaseException {
        if (productState.getProductSymbol().equals(productDTO.getProductSymbol())) {
            List<Stock> stocks = stockFacade.findStockByProduct(productDTO.getProductSymbol());
            if (stocks.isEmpty()) {
                Product product = productFacade.findProductBySymbol(productDTO.getProductSymbol());
                if (product.getProductSymbol().equals(productState.getProductSymbol())) {
                    productFacade.remove(productState);
                } else {
                    throw AppBaseException.createExceptionEditedObjectData();
                }
            } else {
                throw ProductException.createExceptionDeleteProduct(productState);
            }
        } else {
            throw ProductException.createExceptionWrongState(productState);
        }
    }
}
