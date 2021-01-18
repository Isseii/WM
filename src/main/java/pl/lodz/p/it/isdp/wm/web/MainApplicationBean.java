package pl.lodz.p.it.isdp.wm.web;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import pl.lodz.p.it.isdp.wm.web.utils.ContextUtils;

@Named(value = "mainApplicationPageBean")
@ApplicationScoped
public class MainApplicationBean {

    public MainApplicationBean() {
    }

    public String signOutAction() {
        ContextUtils.invalidateSession();
        return "cancelAction";
    }

    public String getMyLogin() {
        return ContextUtils.getUserName();
    }
}
