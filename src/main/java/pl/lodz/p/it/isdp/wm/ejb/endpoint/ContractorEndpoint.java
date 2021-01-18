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
import pl.lodz.p.it.isdp.wm.ejb.facade.ContractorFacade;
import pl.lodz.p.it.isdp.wm.ejb.facade.OfficeAccountFacade;
import pl.lodz.p.it.isdp.wm.ejb.facade.StockFacade;
import pl.lodz.p.it.isdp.wm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.isdp.wm.exception.AppBaseException;
import pl.lodz.p.it.isdp.wm.exception.ContractorException;
import pl.lodz.p.it.isdp.wm.model.Contractor;
import pl.lodz.p.it.isdp.wm.model.OfficeAccount;
import pl.lodz.p.it.isdp.wm.model.Stock;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
@RolesAllowed("Office")
public class ContractorEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @EJB
    private StockFacade stockFacade;

    @EJB
    private ContractorFacade contractorFacade;

    @EJB
    private OfficeAccountFacade officeAccountFacade;

    @Resource
    private SessionContext sessionContext;

    private Contractor contractorState;

    private OfficeAccount loadCurrentOffice() throws AppBaseException {
        //przypisanie konta użytkownika, który tworzył lub edytował kontrahenta
        String userLogin = sessionContext.getCallerPrincipal().getName();
        OfficeAccount account = officeAccountFacade.findByLogin(userLogin);
        if (account != null) {
            return account;
        } else {
            throw AppBaseException.createExceptionNotAuthorizedAction();
        }
    }

    public void registerContractor(ContractorDTO contractorDTO) throws AppBaseException {
        Contractor contractor = new Contractor();
        contractor.setContractorNumber(contractorDTO.getContractorNumber());
        contractor.setContractorName(contractorDTO.getContractorName());
        contractor.setNip(contractorDTO.getNip());
        contractor.setRegon(contractorDTO.getRegon());
        contractor.setStreet(contractorDTO.getStreet());
        contractor.setHouse(contractorDTO.getHouse());
        contractor.setApartment(contractorDTO.getApartment());
        contractor.setZip(contractorDTO.getZip());
        contractor.setCity(contractorDTO.getCity());
        contractor.setActive(true);
        contractor.setCreatedBy(loadCurrentOffice());
        contractorFacade.create(contractor);
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

    public void deleteContractor(ContractorDTO contractorDTO) throws AppBaseException {
        if (contractorState.getContractorNumber().equals(contractorDTO.getContractorNumber())) {
            List<Stock> stocks = stockFacade.findStockByContractorNumber(contractorDTO.getContractorNumber());
            if (stocks.isEmpty()) {
                Contractor contractor = contractorFacade.findByContractorNumber(contractorDTO.getContractorNumber());
                if (contractorState.getContractorNumber().equals(contractor.getContractorNumber())) {
                    contractorFacade.remove(contractorState);
                } else {
                    throw AppBaseException.createExceptionEditedObjectData();
                }
            } else {
                throw ContractorException.createExceptionDeleteContractor(contractorState);
            }
        } else {
            throw ContractorException.createExceptionWrongState(contractorState);
        }
    }

    public ContractorDTO rememberSelectedContractorInState(String contractorNumber) throws AppBaseException {
        contractorState = contractorFacade.findByContractorNumber(contractorNumber);
        return new ContractorDTO(contractorState.getContractorNumber(), contractorState.getContractorName(), contractorState.getNip(), contractorState.getRegon(), contractorState.getStreet(), contractorState.getHouse(), contractorState.getApartment(), contractorState.getZip(), contractorState.getCity());
    }

    public void editContractor(ContractorDTO contractorDTO) throws AppBaseException {
        if (contractorState.getContractorNumber().equals(contractorDTO.getContractorNumber())) {
            contractorState.setContractorName(contractorDTO.getContractorName());
            contractorState.setNip(contractorDTO.getNip());
            contractorState.setRegon(contractorDTO.getRegon());
            contractorState.setStreet(contractorDTO.getStreet());
            contractorState.setHouse(contractorDTO.getHouse());
            contractorState.setApartment(contractorDTO.getApartment());
            contractorState.setZip(contractorDTO.getZip());
            contractorState.setCity(contractorDTO.getCity());
            contractorState.setModificatedBy(loadCurrentOffice());
            contractorFacade.edit(contractorState);
        } else {
            throw ContractorException.createExceptionWrongState(contractorState);
        }
    }

    public void resetPersonalData(ContractorDTO contractorDTO) throws AppBaseException {
        if (contractorState.getContractorNumber().equals(contractorDTO.getContractorNumber())) {
            contractorState.setContractorName("RODO");
            contractorState.setNip("RODO");
            contractorState.setRegon("RODO");
            contractorState.setStreet("RODO");
            contractorState.setHouse("RODO");
            contractorState.setApartment("RODO");
            contractorState.setZip("RODO");
            contractorState.setCity("RODO");
            contractorState.setActive(false);
            contractorState.setModificatedBy(loadCurrentOffice());
            contractorFacade.edit(contractorState);
        } else {
            throw ContractorException.createExceptionWrongState(contractorState);
        }
    }
}
