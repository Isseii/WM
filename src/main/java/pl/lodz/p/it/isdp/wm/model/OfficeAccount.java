package pl.lodz.p.it.isdp.wm.model;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue(AccessLevel.AccessLevelKeys.OFFICE_KEY)
@NamedQueries({
    @NamedQuery(name = "OfficeAccount.findAll", query = "SELECT o FROM OfficeAccount o")
    ,
    @NamedQuery(name = "OfficeAccount.findByLogin", query = "SELECT o FROM OfficeAccount o WHERE o.login = :lg")
})
public class OfficeAccount extends Account implements Serializable {

    public OfficeAccount() {
    }

    public OfficeAccount(Account account) {
        super(account.getId(), account.getName(), account.getSurname(), account.getEmail(), account.getQuestion(), account.getAnswer(), account.getLogin(), account.getPassword());
    }
}
