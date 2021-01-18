package pl.lodz.p.it.isdp.wm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CONTRACTOR", uniqueConstraints = {
    @UniqueConstraint(name = "CONTRACTOR_UNIQUE", columnNames ="CONTRACTOR_NUMBER")
})
@TableGenerator(name = "ContractorGenerator", table = "TableGenerator", pkColumnName = "ID", valueColumnName = "value", pkColumnValue = "ContractorGen")
@NamedQueries({
    @NamedQuery(name = "Contractor.findAll", query = "SELECT c FROM Contractor c")
    ,
    @NamedQuery(name = "Contractor.findActive", query = "SELECT c FROM Contractor c WHERE c.active = true")
    ,
    @NamedQuery(name = "Contractor.findByContractorNumber", query = "SELECT c FROM Contractor c WHERE c.contractorNumber = :cn")
})
public class Contractor extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ContractorGenerator")
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Size(min = 13, max = 13)
    @Column(name = "CONTRACTOR_NUMBER", nullable = false, updatable = false)
    private String contractorNumber;

    @NotNull
    @JoinColumn(name = "CREATED_BY", nullable = false, updatable = false)
    @OneToOne
    private OfficeAccount createdBy;

    @NotNull
    @JoinColumn(name = "MODIFICATED_BY", nullable = true)
    @OneToOne
    private OfficeAccount modificatedBy;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "COMPANY_NAME_OR_NAME_AND_SURNAME", nullable = false)
    private String contractorName;

    @NotNull
    @Size(min = 13, max = 13)
    @Column(name = "NIP", nullable = true)
    private String nip;

    @NotNull
    @Size(min = 9, max = 14)
    @Column(name = "REGON", nullable = true)
    private String regon;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "STREET", nullable = false)
    private String street;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "HOUSE", nullable = false)
    private String house;

    @NotNull
    @Size(min = 0, max = 10)
    @Column(name = "APARTMENT", nullable = true)
    private String apartment;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ZIP", nullable = false)
    private String zip;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CITY", nullable = false)
    private String city;

    @NotNull
    @Column(name = "ACTIVE", nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "contractor")
    private List<Stock> stocks = new ArrayList<>();

    public Contractor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractorNumber() {
        return contractorNumber;
    }

    public void setContractorNumber(String contractorNumber) {
        this.contractorNumber = contractorNumber;
    }

    public OfficeAccount getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(OfficeAccount createdBy) {
        this.createdBy = createdBy;
    }

    public OfficeAccount getModificatedBy() {
        return modificatedBy;
    }

    public void setModificatedBy(OfficeAccount modificatedBy) {
        this.modificatedBy = modificatedBy;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getRegon() {
        return regon;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Contractor(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Contractor other = (Contractor) obj;
        if (!Objects.equals(this.contractorNumber, other.contractorNumber)) {
            return false;
        }
        return true;
    }

}
