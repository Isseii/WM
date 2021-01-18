package pl.lodz.p.it.isdp.wm.dto;

import java.util.Objects;

public class ContractorDTO implements Comparable<ContractorDTO> {

    private String contractorNumber;
    private String contractorName;
    private String nip;
    private String regon;
    private String street;
    private String house;
    private String apartment;
    private String zip;
    private String city;

    public ContractorDTO(String contractorNumber, String contractorName, String nip, String regon, String street, String house, String apartment, String zip, String city) {
        this.contractorNumber = contractorNumber;
        this.contractorName = contractorName;
        this.nip = nip;
        this.regon = regon;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
        this.zip = zip;
        this.city = city;
    }

    public ContractorDTO() {
    }

    public String getContractorNumber() {
        return contractorNumber;
    }

    public void setContractorNumber(String contractorNumber) {
        this.contractorNumber = contractorNumber;
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

    public String getDisplayLabel() {
        return contractorNumber + "  " + contractorName + "  " + street + " " + house + ", " + city;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.contractorNumber);
        hash = 41 * hash + Objects.hashCode(this.contractorName);
        hash = 41 * hash + Objects.hashCode(this.street);
        hash = 41 * hash + Objects.hashCode(this.house);
        hash = 41 * hash + Objects.hashCode(this.apartment);
        hash = 41 * hash + Objects.hashCode(this.zip);
        hash = 41 * hash + Objects.hashCode(this.city);
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
        final ContractorDTO other = (ContractorDTO) obj;
        if (!Objects.equals(this.contractorNumber, other.contractorNumber)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(contractorNumber);
    }

    @Override
    public int compareTo(ContractorDTO o) {
        return this.contractorName.compareTo(o.contractorName);
    }
}
