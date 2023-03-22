package dtos;

import entities.City;

public class CityDTO {
    private String zipcode;
    private String name;

    public CityDTO() {
    }

    public CityDTO(String zipcode, String name) {
        this.zipcode = zipcode;
        this.name = name;
    }

    public CityDTO(City city){
        this.zipcode = city.getZipcode();
        this.name = city.getName();
    }


    public String getZipCode() {
        return zipcode;
    }
    public void setZipCode(String zipcode) {
        this.zipcode = zipcode;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
