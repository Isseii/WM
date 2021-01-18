package pl.lodz.p.it.isdp.wm.web;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.isdp.wm.dto.ContractorDTO;
import pl.lodz.p.it.isdp.wm.dto.LocationDTO;
import pl.lodz.p.it.isdp.wm.dto.ProductDTO;
import pl.lodz.p.it.isdp.wm.dto.StockDTO;
import pl.lodz.p.it.isdp.wm.exception.AppBaseException;
import pl.lodz.p.it.isdp.wm.web.utils.ContextUtils;

@Named(value = "stockUpPageBean2")
@RequestScoped
public class StockUpPageBean2 implements Serializable {

    @Inject
    private StockControllerBean stockControllerBean;

    private StockDTO stockDTO;

    private ProductDTO productDTO;
    private ContractorDTO contractorDTO;
    private LocationDTO locationDTO;

    public StockUpPageBean2() {
    }

    public StockDTO getStockDTO() {
        return stockDTO;
    }

    public void setStockDTO(StockDTO stockDTO) {
        this.stockDTO = stockDTO;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public ContractorDTO getContractorDTO() {
        return contractorDTO;
    }

    public void setContractorDTO(ContractorDTO contractorDTO) {
        this.contractorDTO = contractorDTO;
    }

    public LocationDTO getLocationDTO() {
        return locationDTO;
    }

    public void setLocationDTO(LocationDTO locationDTO) {
        this.locationDTO = locationDTO;
    }

    @PostConstruct
    public void init() {
        stockDTO = stockControllerBean.getSelectedStockDTO();
    }

    public String toStockUpAction() {
        if (stockDTO.getProduct().getWeight() <= stockDTO.getLocation().getMaxWeight()) {
            try {
                stockControllerBean.toStockUp(stockDTO);
            } catch (AppBaseException ex) {
                Logger.getLogger(StockUpPageBean2.class.getName()).log(Level.SEVERE, null, ex);
                ContextUtils.emitI18NMessage(null, ex.getMessage());
                return null;
            }
        } else {
            ContextUtils.emitI18NMessage("StockUP:maxWeight", "weight.to.stock.up.is.more.than.shelf.max.weight");
            return null;
        }
        return "main";
    }

}
