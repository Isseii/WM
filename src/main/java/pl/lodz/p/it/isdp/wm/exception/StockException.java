package pl.lodz.p.it.isdp.wm.exception;

import java.util.List;
import pl.lodz.p.it.isdp.wm.model.Stock;

public class StockException extends AppBaseException {

    static final public String KEY_LOCATION_IN_STOCK_EXIST = "error.location.in.stock.exist.problem";
    static final public String KEY_STOCK_WRONG_STATE = "error.stock.wrong.state.problem";
    static final public String KEY_STOCK_WRONG_QUANTITY = "error.stock.wrong.quantity.problem";
    static final public String KEY_STOCK_WRONG_WEIGHT = "error.stock.wrong.weight.problem";
    static final public String KEY_NO_STOCK_FOR_PRODUCT = "error.no.stock.for.product.problem";
    static final public String KEY_NO_OBJECT_TO_STOCK_UP = "error.no.object.to.stock.up.problem";

    private Stock stock;
    private List<Stock> stocks;

    public Stock getStock() {
        return stock;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    private StockException(String message, Stock stock) {
        super(message);
        this.stock = stock;
    }

    private StockException(String message, Throwable cause, Stock stock) {
        super(message, cause);
        this.stock = stock;
    }

    private StockException(String message, List<Stock> stocks) {
        super(message);
        this.stocks = stocks;
    }

    private StockException(String message, Throwable cause) {
        super(message, cause);
    }

    static public StockException createExceptionLocationInStockExists(Throwable cause, Stock stock) {
        return new StockException(KEY_LOCATION_IN_STOCK_EXIST, cause, stock);
    }

    static public StockException createExceptionWrongState(Stock stock) {
        return new StockException(KEY_STOCK_WRONG_STATE, stock);
    }

    static public StockException createExceptionWrongQuantity(Stock stock) {
        return new StockException(KEY_STOCK_WRONG_QUANTITY, stock);
    }

    static public StockException createExceptionWrongWeight(Stock stock) {
        return new StockException(KEY_STOCK_WRONG_WEIGHT, stock);
    }

    static public StockException createExceptionNoStockForProduct(List<Stock> stocks) {
        return new StockException(KEY_NO_STOCK_FOR_PRODUCT, stocks);
    }

    static public StockException createExceptionStockUpFail(IllegalStateException e) {
        return new StockException(KEY_NO_OBJECT_TO_STOCK_UP, e);
    }

}
