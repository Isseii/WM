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
import pl.lodz.p.it.isdp.wm.dto.ContractorDTO;
import pl.lodz.p.it.isdp.wm.ejb.endpoint.ContractorEndpoint;
import pl.lodz.p.it.isdp.wm.exception.AppBaseException;
import pl.lodz.p.it.isdp.wm.web.utils.ContextUtils;

@Named(value = "listContractorsPageBean")
@ViewScoped
public class ListContractorsPageBean implements Serializable {

    @EJB
    private ContractorEndpoint contractorEndpoint;

    @Inject
    private ContractorControllerBean contractorControllerBean;

    private List<ContractorDTO> listContractors;

    private DataModel<ContractorDTO> dataModelContractors;

    public ListContractorsPageBean() {
    }

    public DataModel<ContractorDTO> getDataModelContractors() {
        return dataModelContractors;
    }

    @PostConstruct
    public void initListContractors() {
        try {
            listContractors = contractorEndpoint.listContractors();
        } catch (AppBaseException ex) {
            Logger.getLogger(ListContractorsPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
        }
        dataModelContractors = new ListDataModel<>(listContractors);
    }

    public String editContractorAction(ContractorDTO contractorDTO) {
        try {
            contractorControllerBean.selectContractorForChange(contractorDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(ListContractorsPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        initListContractors();
        return "editContractor";
    }

    public String deleteSelectedContractorAction(ContractorDTO contractorDTO) {
        try {
            contractorControllerBean.selectContractorForChange(contractorDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(ListContractorsPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        initListContractors();
        return "deleteContractor";
    }

    public String resetSelectedContractorAction(ContractorDTO contractorDTO) {
        try {
            contractorControllerBean.selectContractorForChange(contractorDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(ListContractorsPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        initListContractors();
        return "resetContractor";
    }
}
