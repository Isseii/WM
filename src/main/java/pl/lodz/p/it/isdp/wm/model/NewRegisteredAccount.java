package pl.lodz.p.it.isdp.wm.model;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(AccessLevel.AccessLevelKeys.NEWREGISTERED_KEY)
public class NewRegisteredAccount extends Account implements Serializable {

    public NewRegisteredAccount() {
    }

}
