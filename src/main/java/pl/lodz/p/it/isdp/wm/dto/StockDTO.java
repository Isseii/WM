package pl.lodz.p.it.isdp.wm.dto;

import java.util.Objects;

public class StockDTO implements Comparable<StockDTO> {

    private ProductDTO product;
    private LocationDTO location;
    private ContractorDTO contractor;

    private int quantity;
    private int quantityToIssue;
    private int quantityToMove;

    private String contractorNumber;
    private String productSymbol;
    private String description;
    private String price;
    private String weight;
    private String locationSymbol;
    private String locationTo;
    private String maxWeight;
    private String locationType;

    public StockDTO() {
    }

    public StockDTO(ProductDTO product, int quantity, LocationDTO location, ContractorDTO contractor) {
        this.product = product;
        this.quantity = quantity;
        this.location = location;
        this.contractor = contractor;
    }

    public StockDTO(String productSymbol) {
        this.productSymbol = productSymbol;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProductDTO(ProductDTO product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public ContractorDTO getContractor() {
        return contractor;
    }

    public void setContractor(ContractorDTO contractor) {
        this.contractor = contractor;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLocationSymbol() {
        return locationSymbol;
    }

    public void setLocationSymbol(String locationSymbol) {
        this.locationSymbol = locationSymbol;
    }

    public String getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(String locationTo) {
        this.locationTo = locationTo;
    }

    public String getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(String maxWeight) {
        this.maxWeight = maxWeight;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public int getQuantityToIssue() {
        return quantityToIssue;
    }

    public void setQuantityToIssue(int quantityToIssue) {
        this.quantityToIssue = quantityToIssue;
    }

    public int getQuantityToMove() {
        return quantityToMove;
    }

    public void setQuantityToMove(int quantityToMove) {
        this.quantityToMove = quantityToMove;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.product);
        hash = 97 * hash + this.quantity;
        hash = 97 * hash + Objects.hashCode(this.location);
        hash = 97 * hash + Objects.hashCode(this.contractor);
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
        final StockDTO other = (StockDTO) obj;
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.contractor, other.contractor)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StockDTO: " + "product=" + product + ", quantity=" + quantity + ", location=" + location + ", contractor=" + contractor;
    }

    @Override
    public int compareTo(StockDTO o) {
        return this.location.getLocationSymbol().compareTo(o.location.getLocationSymbol());
    }
}
