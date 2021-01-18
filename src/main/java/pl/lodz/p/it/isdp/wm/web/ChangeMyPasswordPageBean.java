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

@Named(value = "changeMyPasswordPageBean")
@RequestScoped
public class ChangeMyPasswordPageBean {

    @Inject
    private AccountControllerBean accountControllerBean;

    private AccountDTO accountDTO;

    private String newPasswordRepeat;

    private String oldPassword;

    public ChangeMyPasswordPageBean() {
    }

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    public String getNewPasswordRepeat() {
        return newPasswordRepeat;
    }

    public void setNewPasswordRepeat(String newPasswordRepeat) {
        this.newPasswordRepeat = newPasswordRepeat;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @PostConstruct
    public void init() {
        try {
            accountDTO = accountControllerBean.getMyAccountDTO();
        } catch (AppBaseException ex) {
            Logger.getLogger(ChangeMyPasswordPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
        }
    }

    public String saveChangedMyPasswordAction() {
        if (newPasswordRepeat.equals(accountDTO.getPassword())) {
            try {
                accountControllerBean.changeMyPassword(accountDTO);
            } catch (AppBaseException ex) {
                Logger.getLogger(ChangeMyPasswordPageBean.class.getName()).log(Level.SEVERE, null, ex);
                ContextUtils.emitI18NMessage(null, ex.getMessage());
                return null;
            }
        } else {
            ContextUtils.emitI18NMessage("ChangeMyPasswordForm:passwordRepeat", "error.passwords.not.matching");
            return null;
        }
        return "main";
    }
}
