package pl.lodz.p.it.isdp.wm.web;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.isdp.wm.dto.ContractorDTO;
import pl.lodz.p.it.isdp.wm.exception.AppBaseException;
import pl.lodz.p.it.isdp.wm.web.utils.ContextUtils;

@Named(value = "editContractorPageBean")
@RequestScoped
public class EditContractorPageBean {

    private ContractorDTO contractorDTO;

    @Inject
    private ContractorControllerBean contractorControllerBean;

    public EditContractorPageBean() {
    }

    public ContractorDTO getContractorDTO() {
        return contractorDTO;
    }

    public void setContractorDTO(ContractorDTO contractorDTO) {
        this.contractorDTO = contractorDTO;
    }

    @PostConstruct
    public void init() {
        contractorDTO = contractorControllerBean.getSelectedContractorDTO();
    }

    public String saveEditContractorAction() {
        try {
            contractorControllerBean.editContractor(contractorDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(EditContractorPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        return "listContractors";
    }

}
