package pl.lodz.p.it.isdp.wm.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import pl.lodz.p.it.isdp.wm.dto.AccountDTO;
import pl.lodz.p.it.isdp.wm.ejb.endpoint.AccountEndpoint;
import pl.lodz.p.it.isdp.wm.exception.AppBaseException;

@Named(value = "accountControllerBean")
@SessionScoped
public class AccountControllerBean implements Serializable {

    @EJB
    private AccountEndpoint accountEndpoint;

    private int lastActionMethod = 0;

    private AccountDTO selectedAccountDTO;

    public AccountControllerBean() {
    }

    public AccountDTO getSelectedAccountDTO() {
        return selectedAccountDTO;
    }

    public void setSelectedAccountDTO(AccountDTO selectedAccountDTO) {
        this.selectedAccountDTO = selectedAccountDTO;
    }

    public void registerAccount(final AccountDTO accountDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = accountDTO.hashCode() + 1;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = accountEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                accountEndpoint.registerAccount(accountDTO); // wywołanie metody biznesowej punktu dostępowego EJB 
                endpointCallCounter--;
            } while (accountEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void deleteAccount(final AccountDTO accountDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = accountDTO.hashCode() + 2;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = accountEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                accountEndpoint.deleteAccount(accountDTO); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (accountEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void setAccessLevelAccount(final AccountDTO accountDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = accountDTO.hashCode() + 3;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = accountEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                accountEndpoint.setAccessLevelAccount(accountDTO); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (accountEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void editAccount(final AccountDTO accountDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = accountDTO.hashCode() + 4;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = accountEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                accountEndpoint.editAccount(accountDTO); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (accountEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void activateAccount(final AccountDTO accountDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = accountDTO.hashCode() + 5;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = accountEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                accountEndpoint.activateAccount(accountDTO.getLogin()); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (accountEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void deactivateAccount(final AccountDTO accountDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = accountDTO.hashCode() + 6;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = accountEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                accountEndpoint.deactivateAccount(accountDTO.getLogin()); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (accountEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    void changeAccountPassword(AccountDTO accountDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = accountDTO.hashCode() + 7;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = accountEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                accountEndpoint.changeAccountPassword(accountDTO); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (accountEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    void selectAccountForChange(AccountDTO accountDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = accountDTO.hashCode() + 8;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = accountEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                selectedAccountDTO = accountEndpoint.rememberSelectedAccountInState(accountDTO.getLogin()); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (accountEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void changeAccessLevelAccount(final AccountDTO accountDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = accountDTO.hashCode() + 9;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = accountEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                accountEndpoint.changeAccessLevelAccount(accountDTO); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (accountEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void changeMyPassword(AccountDTO accountDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = accountDTO.hashCode() + 10;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = accountEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                accountEndpoint.changeMyPassword(accountDTO); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (accountEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void resetPassword(AccountDTO accountDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = accountDTO.hashCode() + 11;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = accountEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                accountEndpoint.resetPassword(accountDTO); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (accountEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public AccountDTO getMyAccountDTO() throws AppBaseException {
        final int UNIQ_METHOD_ID = accountEndpoint.hashCode() + 12;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = accountEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                selectedAccountDTO = accountEndpoint.rememberMyAccountInState(); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (accountEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
        return selectedAccountDTO;
    }
}
