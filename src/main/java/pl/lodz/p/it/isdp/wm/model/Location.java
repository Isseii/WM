package pl.lodz.p.it.isdp.wm.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "LOCATION", uniqueConstraints = {
    @UniqueConstraint(name = "LOCATION_UNIQUE", columnNames ="LOCATION_SYMBOL")
})
@TableGenerator(name = "LocationGenerator", table = "TableGenerator", pkColumnName = "ID", valueColumnName = "value", pkColumnValue = "LocationGen")
@NamedQueries({
    @NamedQuery(name = "Location.findAll", query = "SELECT l FROM Location l")
    ,
    @NamedQuery(name = "Location.findByLocation", query = "SELECT l FROM Location l WHERE l.locationSymbol = :lid")
    ,
    @NamedQuery(name = "Location.findEmptyLocation", query = "SELECT l FROM Location l WHERE l.emptyLocation=true")
})
public class Location extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "LocationGenerator")
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @OneToOne(mappedBy = "location")
    private Stock stock;

    @NotNull
    @Column(name = "LOCATION_TYPE", nullable = false, updatable = true)
    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    @NotNull
    @Size(min = 11, max = 11)
    @Column(name = "LOCATION_SYMBOL", nullable = false, updatable = false)
    private String locationSymbol;

    @NotNull
    @Column(name = "MAX_WEIGHT_IN_G", nullable = false)
    private int maxWeight;

    @NotNull
    @Column(name = "EMPTY_LOCATION", nullable = false, updatable = true)
    private boolean emptyLocation;

    @NotNull
    @JoinColumn(name = "CREATED_BY", nullable = false, updatable = false)
    @OneToOne
    private WarehouseAccount createdBy;

    @NotNull
    @JoinColumn(name = "MODIFICATED_BY", nullable = true)
    @OneToOne
    private WarehouseAccount modificatedBy;

    public Location() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
        this.maxWeight = locationType.getWeightLimit();
    }

    public String getLocationSymbol() {
        return locationSymbol;
    }

    public void setLocationSymbol(String locationSymbol) {
        this.locationSymbol = locationSymbol;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public boolean isEmptyLocation() {
        return emptyLocation;
    }

    public void setEmptyLocation(boolean emptyLocation) {
        this.emptyLocation = emptyLocation;
    }

    public WarehouseAccount getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(WarehouseAccount createdBy) {
        this.createdBy = createdBy;
    }

    public WarehouseAccount getModificatedBy() {
        return modificatedBy;
    }

    public void setModificatedBy(WarehouseAccount modificatedBy) {
        this.modificatedBy = modificatedBy;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
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
        final Location other = (Location) obj;
        if (!Objects.equals(this.locationSymbol, other.locationSymbol)) {
            return false;
        }
        return true;
    }

}
