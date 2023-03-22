package dtos;

import entities.City;

public class CityDTO {
    private int id;
    private String zipcode;
    private String name;

    public CityDTO() {
    }

    public CityDTO(String zipcode, String name) {
        this.zipcode = zipcode;
        this.name = name;
    }

    public CityDTO(City city){
        if (city.getId() != null){
            this.id = city.getId();
        }
        this.zipcode = city.getZipcode();
        this.name = city.getName();
    }

    public int getId() { return id; }
    public void setId(int id) {
        this.id = id;
    }
    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) { this.zipcode = zipcode; }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
