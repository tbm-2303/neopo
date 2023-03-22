package entities;

import dtos.PhoneDTO;

import javax.persistence.*;

@Table(name = "phone")
@Entity
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "number", length = 35)
    private String number;

    @Column(name = "description", length = 65)
    private String description;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;



    public Phone() {
    }

    public Phone(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public Phone(PhoneDTO phoneDTO){
        this.number = phoneDTO.getNumber();
        this.description = phoneDTO.getDescription();
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    public Person getPerson() {
        return person;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}