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
import pl.lodz.p.it.isdp.wm.dto.ContractorDTO;
import pl.lodz.p.it.isdp.wm.dto.LocationDTO;
import pl.lodz.p.it.isdp.wm.dto.ProductDTO;
import pl.lodz.p.it.isdp.wm.dto.StockDTO;
import pl.lodz.p.it.isdp.wm.ejb.facade.ContractorFacade;
import pl.lodz.p.it.isdp.wm.ejb.facade.IssueFacade;
import pl.lodz.p.it.isdp.wm.ejb.facade.LocationFacade;
import pl.lodz.p.it.isdp.wm.ejb.facade.ProductFacade;
import pl.lodz.p.it.isdp.wm.ejb.facade.StockFacade;
import pl.lodz.p.it.isdp.wm.ejb.facade.WarehouseAccountFacade;
import pl.lodz.p.it.isdp.wm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.isdp.wm.exception.AppBaseException;
import pl.lodz.p.it.isdp.wm.exception.LocationException;
import pl.lodz.p.it.isdp.wm.exception.StockException;
import pl.lodz.p.it.isdp.wm.model.Contractor;
import pl.lodz.p.it.isdp.wm.model.Issue;
import pl.lodz.p.it.isdp.wm.model.Location;
import pl.lodz.p.it.isdp.wm.model.Product;
import pl.lodz.p.it.isdp.wm.model.Stock;
import pl.lodz.p.it.isdp.wm.model.WarehouseAccount;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
@RolesAllowed("Warehouse")
public class StockEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @EJB
    private StockFacade stockFacade;

    @EJB
    private WarehouseAccountFacade warehouseAccountFacade;

    @EJB
    private ProductFacade productFacade;

    @EJB
    private ContractorFacade contractorFacade;

    @EJB
    private LocationFacade locationFacade;

    @EJB
    private IssueFacade issueFacade;

    @Resource
    private SessionContext sessionContext;

    private Stock stockState;

    private WarehouseAccount loadCurrentWarehouse() throws AppBaseException {
        //przypisanie konta użytkownika, który wprawadzał towar na stan, wydawał ze stanu lub przenosił z półki na półkę
        String userLogin = sessionContext.getCallerPrincipal().getName();
        WarehouseAccount account = warehouseAccountFacade.findByLogin(userLogin);
        if (account != null) {
            return account;
        } else {
            throw AppBaseException.createExceptionNotAuthorizedAction();
        }
    }

    public void toStockUp(StockDTO stockDTO) throws AppBaseException {
        Location location = locationFacade.findLocationBySymbol(stockDTO.getLocation().getLocationSymbol());
        Contractor contractor = contractorFacade.findByContractorNumber(stockDTO.getContractor().getContractorNumber());
        Product product = productFacade.findProductBySymbol(stockDTO.getProduct().getProductSymbol());
        if (product.getWeight() * stockDTO.getQuantity() <= location.getMaxWeight()) {
            Stock stock = new Stock();
            stock.setContractor(contractor);
            stock.setProduct(product);
            stock.setQuantity(stockDTO.getQuantity());
            location.setEmptyLocation(false);
            location.setModificatedBy(loadCurrentWarehouse());
            stock.setLocation(location);
            stock.setCreatedBy(loadCurrentWarehouse());
            stockFacade.create(stock);
            locationFacade.edit(location);
        } else {
            throw LocationException.createExceptionLocationOverweighted(location);
        }
    }

    @RolesAllowed({"Office", "Warehouse"})
    public List<ProductDTO> listProducts() throws AppBaseException {
        List<Product> listAllProducts = productFacade.findProducts();
        List<ProductDTO> listProducts = new ArrayList<>();
        for (Product product : listAllProducts) {
            ProductDTO productDTO = new ProductDTO(product.getProductSymbol(), product.getDescription(), product.getPrice());
            listProducts.add(productDTO);
        }
        Collections.sort(listProducts);
        return listProducts;
    }

    public List<LocationDTO> listEmptyLocations() throws AppBaseException {
        List<Location> listEmptyLocations = locationFacade.findEmptyLocation();
        List<LocationDTO> listLocations = new ArrayList<>();
        for (Location location : listEmptyLocations) {
            LocationDTO locationDTO = new LocationDTO(location.getLocationSymbol(), location.getLocationType(), location.isEmptyLocation(), location.getMaxWeight());
            if (location.isEmptyLocation()) {
                listLocations.add(locationDTO);
            }
        }
        Collections.sort(listLocations);
        return listLocations;
    }

    public List<ContractorDTO> listContractors() throws AppBaseException {
        List<Contractor> listRegisteredContractors = contractorFacade.findActiveContractors();
        List<ContractorDTO> listContractors = new ArrayList<>();
        for (Contractor contractor : listRegisteredContractors) {
            ContractorDTO contractorDTO = new ContractorDTO(contractor.getContractorNumber(), contractor.getContractorName(), contractor.getNip(), contractor.getRegon(), contractor.getStreet(), contractor.getHouse(), contractor.getApartment(), contractor.getZip(), contractor.getCity());
            listContractors.add(contractorDTO);
        }
        Collections.sort(listContractors);
        return listContractors;
    }

    public StockDTO rememberSelectedStock(int quantity, String contractorNumber, String productSymbol, String locationSymbol) throws AppBaseException {
        Contractor contractor = contractorFacade.findByContractorNumber(contractorNumber);
        ContractorDTO contractorDTO = new ContractorDTO(contractor.getContractorNumber(), contractor.getContractorName(), contractor.getNip(), contractor.getRegon(), contractor.getStreet(), contractor.getHouse(), contractor.getApartment(), contractor.getZip(), contractor.getCity());
        Product product = productFacade.findProductBySymbol(productSymbol);
        ProductDTO productDTO = new ProductDTO(product.getProductSymbol(), product.getDescription(), product.getPrice(), product.getWeight());
        Location location = locationFacade.findLocationBySymbol(locationSymbol);
        LocationDTO locationDTO = new LocationDTO(location.getLocationSymbol(), location.getLocationType(), location.isEmptyLocation(), location.getMaxWeight());
        return new StockDTO(productDTO, quantity, locationDTO, contractorDTO);
    }

    public StockDTO rememberSelectedStockInState(StockDTO stockDTO) throws AppBaseException {
        stockState = stockFacade.findByLocation(stockDTO.getLocation().getLocationSymbol(), true);
        ContractorDTO contractorDTO = new ContractorDTO(stockDTO.getContractor().getContractorNumber(), stockDTO.getContractor().getContractorName(), stockDTO.getContractor().getNip(), stockDTO.getContractor().getRegon(), stockDTO.getContractor().getStreet(), stockDTO.getContractor().getHouse(), stockDTO.getContractor().getApartment(), stockDTO.getContractor().getZip(), stockDTO.getContractor().getCity());
        ProductDTO productDTO = new ProductDTO(stockDTO.getProduct().getProductSymbol(), stockDTO.getProduct().getDescription(), stockDTO.getProduct().getPrice(), stockDTO.getProduct().getWeight());
        LocationDTO locationDTO = new LocationDTO(stockDTO.getLocation().getLocationSymbol(), stockDTO.getLocation().getLocationType(), stockDTO.getLocation().isEmptyLocation(), stockDTO.getLocation().getMaxWeight());
        return new StockDTO(productDTO, stockState.getQuantity(), locationDTO, contractorDTO);
    }

    @RolesAllowed({"Office", "Warehouse"})
    public List<StockDTO> listStockForProduct(String productSymbol) throws AppBaseException {
        List<Stock> listStockForProduct = stockFacade.findStockByProduct(productSymbol);
        if (listStockForProduct.isEmpty() == false) {
            List<StockDTO> listStock = new ArrayList<>();
            for (Stock stock : listStockForProduct) {
                ContractorDTO contractorDTO = new ContractorDTO(stock.getContractor().getContractorNumber(), stock.getContractor().getContractorName(), stock.getContractor().getNip(), stock.getContractor().getRegon(), stock.getContractor().getStreet(), stock.getContractor().getHouse(), stock.getContractor().getApartment(), stock.getContractor().getZip(), stock.getContractor().getCity());
                ProductDTO productDTO = new ProductDTO(stock.getProduct().getProductSymbol(), stock.getProduct().getDescription(), stock.getProduct().getPrice(), stock.getProduct().getWeight());
                LocationDTO locationDTO = new LocationDTO(stock.getLocation().getLocationSymbol(), stock.getLocation().getLocationType(), stock.getLocation().isEmptyLocation(), stock.getLocation().getMaxWeight());
                StockDTO stockDTO = new StockDTO(productDTO, stock.getQuantity(), locationDTO, contractorDTO);
                listStock.add(stockDTO);
            }
            Collections.sort(listStock);
            return listStock;
        } else {
            throw StockException.createExceptionNoStockForProduct(listStockForProduct);
        }
    }

    public void stockIssue(StockDTO stockDTO) throws AppBaseException {
        if (stockState.getLocation().getLocationSymbol().equals(stockDTO.getLocation().getLocationSymbol())) {

            if (stockState != null) {
                stockState.setQuantity(stockDTO.getQuantity() - stockDTO.getQuantityToIssue());

                Issue issue = new Issue();
                issue.setContractorNumber(stockDTO.getContractor().getContractorNumber());
                issue.setCreatedBy(loadCurrentWarehouse());
                issue.setDescription(stockDTO.getProduct().getDescription());
                issue.setProductSymbol(stockDTO.getProduct().getProductSymbol());
                issue.setPrice(stockDTO.getProduct().getPrice());
                issue.setQuantity(stockDTO.getQuantityToIssue());
                issue.setLocationSymbol(stockDTO.getLocation().getLocationSymbol());
                issueFacade.create(issue);

                if (stockState.getQuantity() == 0) {
                    Location location = stockState.getLocation();
                    location.setEmptyLocation(true);
                    location.setModificatedBy(loadCurrentWarehouse());
                    locationFacade.edit(location);
                    stockFacade.remove(stockState);
                } else {
                    stockState.setModificatedBy(loadCurrentWarehouse());
                    stockFacade.edit(stockState);
                }
            }
        } else {
            throw StockException.createExceptionWrongState(stockState);
        }
    }

    public void moveStock(StockDTO stockDTO) throws AppBaseException {
        if (stockState.getLocation().getLocationSymbol().equals(stockDTO.getLocation().getLocationSymbol())) {

            if (stockState != null) {
                stockState.setModificatedBy(loadCurrentWarehouse());

                Location locationFrom = stockState.getLocation();
                Location locationTo = locationFacade.findLocationBySymbol(stockDTO.getLocationTo());

                if (stockDTO.getProduct().getWeight() * stockDTO.getQuantityToMove() <= locationTo.getMaxWeight()) {
                    if (stockDTO.getQuantity() == stockDTO.getQuantityToMove()) {
                        // ustawienie lokalizacji jako pustej
                        locationFrom.setEmptyLocation(true);
                        locationFrom.setModificatedBy(loadCurrentWarehouse());
                        locationFacade.edit(locationFrom);
                        //przydzielenie nowej lokalizacji i ustawienie jako niepustej
                        locationTo.setEmptyLocation(false);
                        locationTo.setModificatedBy(loadCurrentWarehouse());
                        locationFacade.edit(locationTo);
                        stockState.setLocation(locationTo);
                        stockFacade.edit(stockState);
                    } else if (stockDTO.getQuantity() > stockDTO.getQuantityToMove()) {
                        // zmiana ilości na obecnej lokalizacji
                        stockState.setQuantity(stockDTO.getQuantity() - stockDTO.getQuantityToMove());
                        stockFacade.edit(stockState);
                        // zmiana lokalizacji na niepustą
                        locationTo.setEmptyLocation(false);
                        locationTo.setModificatedBy(loadCurrentWarehouse());
                        locationFacade.edit(locationTo);
                        // przypisanie wartości dla nowego obiektu stanu
                        Stock stock = new Stock();
                        stock.setProduct(stockState.getProduct());
                        stock.setContractor(stockState.getContractor());
                        stock.setLocation(locationTo);
                        stock.setCreatedBy(loadCurrentWarehouse());
                        stock.setQuantity(stockDTO.getQuantityToMove());
                        stockFacade.create(stock);
                    } else {
                        throw StockException.createExceptionWrongQuantity(stockState);
                    }
                } else {
                    throw StockException.createExceptionWrongWeight(stockState);
                }
            }
        } else {
            throw StockException.createExceptionWrongState(stockState);
        }
    }
}
