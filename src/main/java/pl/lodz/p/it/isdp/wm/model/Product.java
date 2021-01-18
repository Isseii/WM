package pl.lodz.p.it.isdp.wm.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PRODUCT", uniqueConstraints = {
    @UniqueConstraint(name = "PRODUCT_UNIQUE", columnNames ="PRODUCT_SYMBOL")
})
@TableGenerator(name = "ProductGenerator", table = "TableGenerator", pkColumnName = "ID", valueColumnName = "value", pkColumnValue = "ProductGen")
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
    ,
    @NamedQuery(name = "Product.findByProductSymbol", query = "SELECT p FROM Product p WHERE p.productSymbol = :pid")
})
public class Product extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ProductGenerator")
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Size(min = 10, max = 13)
    @Column(name = "PRODUCT_SYMBOL", nullable = false, updatable = false)
    private String productSymbol;

    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "DESCRIPTION", nullable = false, updatable = true)
    private String description;

    @Digits(integer = 6, fraction = 2)
    @DecimalMin(value = "0.01")
    @NotNull
    @Column(name = "PRICE", precision = 8, scale = 2, nullable = false)
    private BigDecimal price;

    @NotNull
    @Column(name = "WEIGHT_IN_G", nullable = false)
    private int weight;

    @NotNull
    @Column(name = "EASILY_DAMAGE", nullable = false)
    private boolean easilyDamage;

    @NotNull
    @JoinColumn(name = "CREATED_BY", nullable = false, updatable = false)
    @OneToOne
    private OfficeAccount createdBy;

    @NotNull
    @JoinColumn(name = "MODIFICATED_BY", nullable = true)
    @OneToOne
    private OfficeAccount modificatedBy;

    @OneToMany(mappedBy = "product")
    private List<Stock> stocks = new ArrayList<>();

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isEasilyDamage() {
        return easilyDamage;
    }

    public void setEasilyDamage(boolean easilyDamage) {
        this.easilyDamage = easilyDamage;
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

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Product other = (Product) obj;
        if (!Objects.equals(this.productSymbol, other.productSymbol)) {
            return false;
        }
        return true;
    }

}
