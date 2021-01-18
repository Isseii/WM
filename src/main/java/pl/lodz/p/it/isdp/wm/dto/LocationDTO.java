package pl.lodz.p.it.isdp.wm.dto;

import java.util.Objects;
import pl.lodz.p.it.isdp.wm.model.LocationType;

public class LocationDTO implements Comparable<LocationDTO> {

    private String locationSymbol;
    private int maxWeight;
    private LocationType locationType;
    private boolean emptyLocation;
    private String loactionTypeI18NValue;

    public LocationDTO() {
    }

    public LocationDTO(String locationSymbol, LocationType locationType, boolean emptyLocation, int maxWeight) {
        this.locationSymbol = locationSymbol;
        this.locationType = locationType;
        this.emptyLocation = emptyLocation;
        this.maxWeight = maxWeight;
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

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public boolean isEmptyLocation() {
        return emptyLocation;
    }

    public String getDisplayLabel() {
        return locationSymbol + " " + (locationType.getWeightLimit() / 1000 + " KG");
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.locationSymbol);
        hash = 59 * hash + this.maxWeight;
        hash = 59 * hash + Objects.hashCode(this.locationType);
        hash = 59 * hash + (this.emptyLocation ? 1 : 0);
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
        final LocationDTO other = (LocationDTO) obj;
        if (!Objects.equals(this.locationSymbol, other.locationSymbol)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(locationSymbol);
    }

    @Override
    public int compareTo(LocationDTO o) {
        return this.locationSymbol.compareTo(o.locationSymbol);
    }
}
