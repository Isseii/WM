package pl.lodz.p.it.isdp.wm.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class IssueDTO implements Comparable<IssueDTO> {

    private int quantity;
    private String contractorNumber;
    private String productSymbol;
    private String description;
    private BigDecimal price;
    private String locationSymbol;
    private String login;
    private Date creationDate;

    public IssueDTO() {
    }

    public IssueDTO(int quantity, String contractorNumber, String productSymbol, String description, BigDecimal price, String locationSymbol, String login, Date creationDate) {
        this.quantity = quantity;
        this.contractorNumber = contractorNumber;
        this.productSymbol = productSymbol;
        this.description = description;
        this.price = price;
        this.locationSymbol = locationSymbol;
        this.login = login;
        this.creationDate = creationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getContractorNumber() {
        return contractorNumber;
    }

    public void setContractorNumber(String contractorNumber) {
        this.contractorNumber = contractorNumber;
    }

    public String getProductSymbol() {
        return productSymbol;
    }

    public void setProductSymbol(String productSymbol) {
        this.productSymbol = productSymbol;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationSymbol() {
        return locationSymbol;
    }

    public void setLocationSymbol(String locationSymbol) {
        this.locationSymbol = locationSymbol;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.contractorNumber);
        hash = 97 * hash + Objects.hashCode(this.productSymbol);
        hash = 97 * hash + Objects.hashCode(this.locationSymbol);
        hash = 97 * hash + Objects.hashCode(this.login);
        hash = 97 * hash + Objects.hashCode(this.creationDate);
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
        final IssueDTO other = (IssueDTO) obj;
        if (this.quantity != other.quantity) {
            return false;
        }
        if (!Objects.equals(this.contractorNumber, other.contractorNumber)) {
            return false;
        }
        if (!Objects.equals(this.productSymbol, other.productSymbol)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.locationSymbol, other.locationSymbol)) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.creationDate, other.creationDate)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(IssueDTO o) {
        return this.creationDate.compareTo(o.creationDate);
    }
}
