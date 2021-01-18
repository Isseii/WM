package pl.lodz.p.it.isdp.wm.exception;

import javax.persistence.NoResultException;
import pl.lodz.p.it.isdp.wm.model.Product;

public class ProductException extends AppBaseException {

    static final public String KEY_PRODUCT_SYMBOL_EXIST = "error.product.symbol.exist.problem";
    static final public String KEY_PRODUCT_WRONG_STATE = "error.product.wrong.state.problem";
    static final public String KEY_PRODUCT_HAS_STOCK = "error.product.has.stock.problem";
    static final public String KEY_PRODUCT_NOT_EXIST = "error.product.not.exist.problem";

    private Product product;

    public Product getProduct() {
        return product;
    }

    private ProductException(String message, Product product) {
        super(message);
        this.product = product;
    }

    private ProductException(String message, Throwable cause, Product product) {
        super(message, cause);
        this.product = product;
    }

    private ProductException(String message, Throwable cause) {
        super(message, cause);
    }

    static public ProductException createExceptionProductExists(Throwable cause, Product product) {
        return new ProductException(KEY_PRODUCT_SYMBOL_EXIST, cause, product);
    }

    static public ProductException createExceptionWrongState(Product product) {
        return new ProductException(KEY_PRODUCT_WRONG_STATE, product);
    }

    static public ProductException createExceptionDeleteProduct(Product product) {
        return new ProductException(KEY_PRODUCT_HAS_STOCK, product);
    }

    static public ProductException createExceptionProductNotExists(NoResultException e) {
        return new ProductException(KEY_PRODUCT_NOT_EXIST, e);
    }
}
