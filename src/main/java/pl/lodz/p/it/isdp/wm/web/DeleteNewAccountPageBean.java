package pl.lodz.p.it.isdp.wm.web;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.isdp.wm.dto.AccountDTO;
import pl.lodz.p.it.isdp.wm.exception.AppBaseException;
import pl.lodz.p.it.isdp.wm.web.utils.ContextUtils;

@Named(value = "deleteNewAccountPageBean")
@RequestScoped
public class DeleteNewAccountPageBean {

    @Inject
    private AccountControllerBean accountControllerBean;

    private AccountDTO accountDTO;

    public DeleteNewAccountPageBean() {
    }

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    @PostConstruct
    public void init() {
        accountDTO = accountControllerBean.getSelectedAccountDTO();
    }

    public String deleteAccountAction() {
        try {
            accountControllerBean.deleteAccount(accountDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(DeleteNewAccountPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        return "listNewRegisteredAccounts";
    }

}
