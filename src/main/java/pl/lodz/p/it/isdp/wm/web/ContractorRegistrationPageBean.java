package pl.lodz.p.it.isdp.wm.web;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.isdp.wm.dto.ContractorDTO;
import pl.lodz.p.it.isdp.wm.exception.AppBaseException;
import pl.lodz.p.it.isdp.wm.web.utils.ContextUtils;

@Named(value = "contractorRegistrationPageBean")
@RequestScoped
public class ContractorRegistrationPageBean implements Serializable {

    @Inject
    private ContractorControllerBean contractorControllerBean;

    private ContractorDTO contractorDTO;

    public ContractorRegistrationPageBean() {
    }

    public ContractorDTO getContractorDTO() {
        return contractorDTO;
    }

    public void setContractorDTO(ContractorDTO contractorDTO) {
        this.contractorDTO = contractorDTO;
    }

    @PostConstruct
    public void init() {
        contractorDTO = new ContractorDTO();
    }

    public String registerContractorAction() {
        try {
            contractorControllerBean.registerContractor(contractorDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(ContractorRegistrationPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        return "main";
    }

}
