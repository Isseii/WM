package pl.lodz.p.it.isdp.wm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ISSUE")
@TableGenerator(name = "IssueGenerator", table = "TableGenerator", pkColumnName = "ID", valueColumnName = "value", pkColumnValue = "IssueGen")
@NamedQueries({
    @NamedQuery(name = "Issue.findByProduct", query = "SELECT i FROM Issue i WHERE i.productSymbol = :p")
    ,
    @NamedQuery(name = "Issue.findByLocation", query = "SELECT i FROM Issue i WHERE i.locationSymbol = :loc")
    ,
    @NamedQuery(name = "Issue.findByContractor", query = "SELECT i FROM Issue i WHERE i.contractorNumber = :cn")
})
public class Issue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "IssueGenerator")
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Size(min = 1, max = Integer.MAX_VALUE)
    @Column(name = "QUANTITY", nullable = false, updatable = false)
    private int quantity;

    @NotNull
    @Size(min = 11, max = 11)
    @Column(name = "LOCATION", nullable = false, updatable = false)
    private String locationSymbol;

    @NotNull
    @Size(min = 10, max = 13)
    @Column(name = "PRODUCT", nullable = false, updatable = false)
    private String productSymbol;

    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "DESCRIPTION", nullable = false, updatable = false)
    private String description;

    @Digits(integer = 6, fraction = 2)
    @DecimalMin(value = "0.01")
    @NotNull
    @Column(name = "PRICE", precision = 8, scale = 2, nullable = false)
    private BigDecimal price;

    @NotNull
    @Size(min = 13, max = 15)
    @Column(name = "CONTRACTOR", nullable = false, updatable = false)
    private String contractorNumber;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private String login;

    @NotNull
    @Column(name = "CREATION_DATE", nullable = false, updatable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;

    public Issue() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setLocationSymbol(String locationSymbol) {
        this.locationSymbol = locationSymbol;
    }

    public void setProductSymbol(String productSymbol) {
        this.productSymbol = productSymbol;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setContractorNumber(String contractorNumber) {
        this.contractorNumber = contractorNumber;
    }

    public void setCreatedBy(WarehouseAccount loadCurrentWarehouse) {
        this.login = loadCurrentWarehouse.getLogin();
    }

    public int getQuantity() {
        return quantity;
    }

    public String getLocationSymbol() {
        return locationSymbol;
    }

    public String getProductSymbol() {
        return productSymbol;
    }

    public String getDescription() {
        return description;
    }

    public String getContractorNumber() {
        return contractorNumber;
    }

    public String getLogin() {
        return login;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    @PrePersist
    public void initCreationDate() {
        creationDate = new Date();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + this.quantity;
        hash = 59 * hash + Objects.hashCode(this.locationSymbol);
        hash = 59 * hash + Objects.hashCode(this.productSymbol);
        hash = 59 * hash + Objects.hashCode(this.description);
        hash = 59 * hash + Objects.hashCode(this.contractorNumber);
        hash = 59 * hash + Objects.hashCode(this.login);
        hash = 59 * hash + Objects.hashCode(this.creationDate);
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
        final Issue other = (Issue) obj;
        if (this.quantity != other.quantity) {
            return false;
        }
        if (!Objects.equals(this.locationSymbol, other.locationSymbol)) {
            return false;
        }
        if (!Objects.equals(this.productSymbol, other.productSymbol)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.contractorNumber, other.contractorNumber)) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.creationDate, other.creationDate)) {
            return false;
        }
        return true;
    }

}
