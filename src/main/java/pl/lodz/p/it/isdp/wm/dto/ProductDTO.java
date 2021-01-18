package pl.lodz.p.it.isdp.wm.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductDTO implements Comparable<ProductDTO> {

    private String productSymbol;
    private String description;
    private BigDecimal price;
    private int weight;
    private boolean easilyDamage;

    private String priceFromForm;

    public ProductDTO() {
    }

    public ProductDTO(String productSymbol, String description, BigDecimal price, int weight, boolean easilyDamage) {
        this.productSymbol = productSymbol;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.easilyDamage = easilyDamage;
    }

    public ProductDTO(String productSymbol, String description, BigDecimal price) {
        this.productSymbol = productSymbol;
        this.description = description;
        this.price = price;
    }

    public ProductDTO(String productSymbol, String description, BigDecimal price, int weight) {
        this.productSymbol = productSymbol;
        this.description = description;
        this.price = price;
        this.weight = weight;
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

    // metoda przekształcająca Stringa w BigDecimala (w celu walidacji formatu ceny wprowadzanej w formularzu na stronie)
    public BigDecimal getPrice(String priceFromForm) {
        price = new BigDecimal(priceFromForm);
        return price;
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

    public String getPriceFromForm() {
        return priceFromForm;
    }

    public void setPriceFromForm(String priceFromForm) {
        this.priceFromForm = priceFromForm;
    }

    // metoda przekształcająca BigDecimala w Stringa (w celu wyświetlenia danych z bazy w formularzu edycji produktu na stronie)
    public String setPriceFromForm(BigDecimal price) {
        priceFromForm = price.toString();
        return priceFromForm;
    }

    public String getDisplayLabel() {
        return productSymbol + " " + description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.productSymbol);
        hash = 29 * hash + Objects.hashCode(this.description);
        hash = 29 * hash + Objects.hashCode(this.price);
        hash = 29 * hash + this.weight;
        hash = 29 * hash + (this.easilyDamage ? 1 : 0);
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
        final ProductDTO other = (ProductDTO) obj;
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(productSymbol);
    }

    @Override
    public int compareTo(ProductDTO o) {
        return this.productSymbol.compareTo(o.productSymbol);
    }
}
