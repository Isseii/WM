package pl.lodz.p.it.isdp.wm.model;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Version
    private int version;

    @NotNull
    @Column(name = "CREATION_DATE", nullable = false, updatable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;

    @NotNull
    @Column(name = "MODIFICATION_DATE", nullable = true, updatable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modificationDate;

    @PrePersist
    public void initCreationDate() {
        creationDate = new Date();
    }

    @PreUpdate
    public void initModificationDate() {
        modificationDate = new Date();
    }

    public int getVersion() {
        return version;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    @Override
    public int hashCode() {
        int hash = 101 * creationDate.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractEntity other = (AbstractEntity) obj;
        if (this.version != other.version) {
            return false;
        }
        if (!Objects.equals(this.creationDate, other.creationDate)) {
            return false;
        }
        if (!Objects.equals(this.modificationDate, other.modificationDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AbstractEntity: " + "version=" + version + ", creationDate=" + creationDate;
    }

}
