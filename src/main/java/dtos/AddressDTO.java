package dtos;

import entities.Address;

public class AddressDTO {
    private int id;
    private String street;
    private String info;
    private CityDTO city;
    //from city
    // private String zipcode;
    //private String name;


    public AddressDTO() {
    }

    public AddressDTO(Address address) {
        if (address.getId() != null){
            this.id = address.getId();
        }
        this.street = address.getStreet();
        this.info = address.getInfo();
        this.city = new CityDTO(address.getCity());
        // this.zipcode = address.getCity().getZipcode();
        // this.name = address.getCity().getName();

    }

    public CityDTO getCity() {
        return city;
    }
    public void setCity(CityDTO city) {
        this.city = city;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    // public String getName() { return name; }
    //public void setName(String name) { this.name = name; }
    //public String getZipcode() {return zipcode;}
    //public void setZipcode(String zipcode) {this.zipcode = zipcode;}
}
