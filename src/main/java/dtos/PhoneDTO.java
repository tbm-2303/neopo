package dtos;

import entities.Phone;

public class PhoneDTO {

    private int id;
    private String number;
    private String description;


    public PhoneDTO() {
    }

    public PhoneDTO(Phone phone){
        this.id = phone.getId();
        this.number = phone.getNumber();
        this.description = phone.getDescription();
    }





    public PhoneDTO(String number){
        this.number = number;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
