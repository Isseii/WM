package pl.lodz.p.it.isdp.wm.ejb.endpoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.SessionContext;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.isdp.wm.dto.AccountDTO;
import pl.lodz.p.it.isdp.wm.ejb.facade.AccountFacade;
import pl.lodz.p.it.isdp.wm.ejb.facade.AdministrationAccountFacade;
import pl.lodz.p.it.isdp.wm.ejb.facade.OfficeAccountFacade;
import pl.lodz.p.it.isdp.wm.ejb.facade.WarehouseAccountFacade;
import pl.lodz.p.it.isdp.wm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.isdp.wm.exception.AccountException;
import pl.lodz.p.it.isdp.wm.exception.AppBaseException;
import pl.lodz.p.it.isdp.wm.model.AccessLevel;
import pl.lodz.p.it.isdp.wm.model.Account;
import pl.lodz.p.it.isdp.wm.model.AdministrationAccount;
import pl.lodz.p.it.isdp.wm.model.NewRegisteredAccount;
import pl.lodz.p.it.isdp.wm.model.OfficeAccount;
import pl.lodz.p.it.isdp.wm.model.WarehouseAccount;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
@RolesAllowed("Administration")
public class AccountEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @EJB
    private AdministrationAccountFacade administrationAccountFacade;

    @EJB
    private OfficeAccountFacade officeAccountFacade;

    @EJB
    private WarehouseAccountFacade warehouseAccountFacade;

    @EJB
    private AccountFacade accountFacade;

    @Resource
    private SessionContext sessionContext;

    private Account accountState;

    private AdministrationAccount loadCurrentAdmin() throws AppBaseException {
        //przypisanie konta administratora, który wykonywał akcje na koncie
        String adminLogin = sessionContext.getCallerPrincipal().getName();
        AdministrationAccount administrationAccount = administrationAccountFacade.findByLogin(adminLogin);
        if (administrationAccount != null) {
            return administrationAccount;
        } else {
            throw AppBaseException.createExceptionNotAuthorizedAction();
        }
    }

    @PermitAll
    public void registerAccount(AccountDTO accountDTO) throws AppBaseException {
        Account account = new NewRegisteredAccount();
        account.setName(accountDTO.getName());
        account.setSurname(accountDTO.getSurname());
        account.setEmail(accountDTO.getEmail());
        account.setLogin(accountDTO.getLogin());
        account.setPassword(accountDTO.getPassword());
        account.setQuestion(accountDTO.getQuestion());
        account.setAnswer(accountDTO.getAnswer());
        account.setActive(false);
        accountFacade.create(account);
    }

    public List<AccountDTO> listNewRegisteredAccount() throws AppBaseException {
        List<Account> listRegisteredAccount = accountFacade.findNewRegisteredAccount();
        List<AccountDTO> listNewRegisteredAccount = new ArrayList<>();
        for (Account account : listRegisteredAccount) {
            AccountDTO accountDTO = new AccountDTO(account.getLogin(), account.getName(), account.getSurname(), account.getEmail());
            listNewRegisteredAccount.add(accountDTO);
        }
        Collections.sort(listNewRegisteredAccount);
        return listNewRegisteredAccount;
    }

    public void deleteAccount(AccountDTO accountDTO) throws AppBaseException {
        Account account = accountFacade.findByLogin(accountDTO.getLogin());
        if (account.getLogin().equals(accountState.getLogin())) {
            if (account.isActive() == false && account.getClass() == (NewRegisteredAccount.class)) {
                accountFacade.remove(account);
            } else {
                throw AccountException.createExceptionDeleteAuthorizedAccount(account);
            }
        } else {
            throw AccountException.createExceptionWrongState(account);
        }
    }

    public void setAccessLevelAccount(AccountDTO accountDTO) throws AppBaseException {
        Account newRegisteredAccount = accountFacade.findByLogin(accountDTO.getLogin());
        Account account = null;
        if (accountDTO.isActive() == false && newRegisteredAccount.getClass() == (NewRegisteredAccount.class)) {
            switch (accountDTO.getAccessLevel()) {
                case OFFICE:
                    account = new OfficeAccount(newRegisteredAccount);
                    break;
                case ADMINISTRATION:
                    account = new AdministrationAccount(newRegisteredAccount);
                    break;
                case WAREHOUSE:
                    account = new WarehouseAccount(newRegisteredAccount);
                    break;
            }
            if (account != null) {
                accountFacade.remove(newRegisteredAccount);
                account.setActive(true); // włączenie konta przy autoryzacji
                account.setAuthorizedBy(loadCurrentAdmin());
                accountFacade.create(account);
            }
        } else {
            throw AccountException.createExceptionAccountAlreadyAuthorized(account);
        }
    }

    public List<AccountDTO> listAuthorizedAccounts() throws AppBaseException {
        List<Account> listAuthorizedAccounts = accountFacade.findAuthorizedAccount();
        List<AccountDTO> listAccounts = new ArrayList<>();
        for (Account account : listAuthorizedAccounts) {
            AccountDTO accountDTO = new AccountDTO(account.getLogin(), account.getName(), account.getSurname(), account.getEmail(), account.isActive());

            AdministrationAccount administrationAccount = administrationAccountFacade.findByLogin(account.getLogin());
            OfficeAccount officeAccount = officeAccountFacade.findByLogin(account.getLogin());
            WarehouseAccount warehouseAccount = warehouseAccountFacade.findByLogin(account.getLogin());
            if (administrationAccount != null) {
                accountDTO.setAccessLevel(AccessLevel.ADMINISTRATION);
                accountDTO.setOldAccessLevel(AccessLevel.ADMINISTRATION);
            } else if (officeAccount != null) {
                accountDTO.setAccessLevel(AccessLevel.OFFICE);
                accountDTO.setOldAccessLevel(AccessLevel.OFFICE);
            } else if (warehouseAccount != null) {
                accountDTO.setAccessLevel(AccessLevel.WAREHOUSE);
                accountDTO.setOldAccessLevel(AccessLevel.WAREHOUSE);
            }
            listAccounts.add(accountDTO);
        }
        Collections.sort(listAccounts);
        return listAccounts;
    }

    public void activateAccount(String login) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        if (!account.isActive()) {
            account.setActive(true);
            account.setModificatedBy(loadCurrentAdmin());
        } else {
            throw AccountException.createExceptionForChangeActiveState(account);
        }
    }

    public void deactivateAccount(String login) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        if (account.isActive()) {
            account.setActive(false);
            account.setModificatedBy(loadCurrentAdmin());
        } else {
            throw AccountException.createExceptionForChangeActiveState(account);
        }
    }

    public void changeAccountPassword(AccountDTO accountDTO) throws AppBaseException {
        if (accountState.getLogin().equals(accountDTO.getLogin())) {
            accountState.setPassword(accountDTO.getPassword());
            accountState.setModificatedBy(loadCurrentAdmin());
            accountFacade.edit(accountState);
        } else {
            throw AccountException.createExceptionWrongState(accountState);
        }
    }

    @PermitAll // dostęp wymagany przy resetowaniu hasła
    public AccountDTO rememberSelectedAccountInState(String login) throws AppBaseException {
        accountState = accountFacade.findByLogin(login);
        return new AccountDTO(accountState.getName(), accountState.getSurname(), accountState.getEmail(), accountState.getLogin(), accountState.getPassword(), accountState.getQuestion(), accountState.getAnswer(), accountState.isActive());
    }

    @RolesAllowed({"Administration", "Office", "Warehouse"})
    public AccountDTO rememberMyAccountInState() throws AppBaseException {
        accountState = accountFacade.findByLogin(sessionContext.getCallerPrincipal().getName());
        return new AccountDTO(accountState.getName(), accountState.getSurname(), accountState.getEmail(), accountState.getLogin(), accountState.getPassword(), accountState.getQuestion(), accountState.getAnswer(), accountState.isActive());
    }

    public void editAccount(AccountDTO accountDTO) throws AppBaseException {
        if (accountState.getLogin().equals(accountDTO.getLogin())) {
            accountState.setName(accountDTO.getName());
            accountState.setSurname(accountDTO.getSurname());
            accountState.setEmail(accountDTO.getEmail());
            accountState.setModificatedBy(loadCurrentAdmin());
            accountFacade.edit(accountState);
        } else {
            throw AccountException.createExceptionWrongState(accountState);
        }
    }

    public void changeAccessLevelAccount(AccountDTO accountDTO) throws AppBaseException {

        switch (accountDTO.getOldAccessLevel()) {
            case OFFICE:
                if (officeAccountFacade.findByLogin(accountDTO.getLogin()) == null) {
                    throw AccountException.createExceptionAccessLevelAlreadyChanged();
                }
                break;
            case ADMINISTRATION:
                if (administrationAccountFacade.findByLogin(accountDTO.getLogin()) == null) {
                    throw AccountException.createExceptionAccessLevelAlreadyChanged();
                }
                break;
            case WAREHOUSE:
                if (warehouseAccountFacade.findByLogin(accountDTO.getLogin()) == null) {
                    throw AccountException.createExceptionAccessLevelAlreadyChanged();
                }
                break;
        }

        Account selectedAccount = accountFacade.findByLogin(accountDTO.getLogin());
        try {
            Account account = null;

            switch (accountDTO.getAccessLevel()) {
                case OFFICE:
                    account = new OfficeAccount(selectedAccount);
                    break;
                case ADMINISTRATION:
                    account = new AdministrationAccount(selectedAccount);
                    break;
                case WAREHOUSE:
                    account = new WarehouseAccount(selectedAccount);
                    break;
            }
            if (account != null) {
                accountFacade.remove(selectedAccount);
                account.setActive(true); // włączenie konta przy autoryzacji
                account.setAuthorizedBy(loadCurrentAdmin());
                accountFacade.create(account);
            }
        } catch (EJBTransactionRolledbackException e) {
            throw AccountException.createExceptionForChangeAccessLevel(e, selectedAccount);
        }
    }

    @RolesAllowed({"Administration", "Office", "Warehouse"})
    public void changeMyPassword(AccountDTO accountDTO) throws AppBaseException {
        if (accountState.getLogin().equals(accountDTO.getLogin())) {

            Account account = new Account();
            account.setPassword(accountDTO.getOldPassword()); // encja account utworzona wyłącznie w celu wyliczenia skrótu starego hasła

            if ((accountState.getPassword().equals(account.getPassword()))) {
                accountState.setPassword(accountDTO.getPassword());
                accountState.setModificatedBy(null);
                accountFacade.edit(accountState);
            } else {
                throw AccountException.createExceptionWrongPassword(accountState);
            }
        } else {
            throw AccountException.createExceptionWrongState(accountState);
        }
    }

    @PermitAll
    public void resetPassword(AccountDTO accountDTO) throws AppBaseException {
        if (accountState.getLogin().equals(accountDTO.getLogin())) {
            if (accountState.getAnswer().equals(accountDTO.getAnswer())) {
                accountState.setPassword(accountDTO.getPassword());
                accountFacade.edit(accountState);
            } else {
                throw AccountException.createExceptionWrongAnswer(accountState);
            }
        } else {
            throw AccountException.createExceptionWrongState(accountState);
        }
    }
}
