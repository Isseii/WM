package pl.lodz.p.it.isdp.wm.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import pl.lodz.p.it.isdp.wm.dto.ContractorDTO;
import pl.lodz.p.it.isdp.wm.ejb.endpoint.ContractorEndpoint;
import pl.lodz.p.it.isdp.wm.exception.AppBaseException;

@Named(value = "contractorControllerBean")
@SessionScoped
public class ContractorControllerBean implements Serializable {

    @EJB
    private ContractorEndpoint contractorEndpoint;

    private int lastActionMethod = 0;

    private ContractorDTO selectedContractorDTO;

    public ContractorControllerBean() {
    }

    public ContractorDTO getSelectedContractorDTO() {
        return selectedContractorDTO;
    }

    public void setSelectedContractorDTO(ContractorDTO selectedContractorDTO) {
        this.selectedContractorDTO = selectedContractorDTO;
    }

    public void registerContractor(final ContractorDTO contractorDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = contractorDTO.hashCode() + 1;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = contractorEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                contractorEndpoint.registerContractor(contractorDTO); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (contractorEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void deleteContractor(final ContractorDTO contractorDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = contractorDTO.hashCode() + 2;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = contractorEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                contractorEndpoint.deleteContractor(contractorDTO); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (contractorEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void editContractor(final ContractorDTO contractorDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = contractorDTO.hashCode() + 3;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = contractorEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                contractorEndpoint.editContractor(contractorDTO); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (contractorEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    void selectContractorForChange(ContractorDTO contractorDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = contractorDTO.hashCode() + 4;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = contractorEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                selectedContractorDTO = contractorEndpoint.rememberSelectedContractorInState(contractorDTO.getContractorNumber()); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (contractorEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void resetPersonalData(ContractorDTO contractorDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = contractorDTO.hashCode() + 5;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = contractorEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                contractorEndpoint.resetPersonalData(contractorDTO); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (contractorEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

}
