package entities;

import dtos.AddressDTO;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Table(name = "address")
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "street", length = 35)
    private String street;

    @Column(name = "info", length = 100)
    private String info;

    @OneToMany(mappedBy = "address")
    private Set<Person> persons = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;




    public Address() {
    }

    public Address(String street, String info, City city) {
        this.street = street;
        this.info = info;
        this.city = city;
    }

    public Address(AddressDTO addressDTO) {
        this.street = addressDTO.getStreet();
        this.info = addressDTO.getInfo();
        }






    public void addCity(City city){
        this.city = city;
        city.addAddress(this);
    }

    public City getCity() {
        return city;
    }
    public void setCity(City city) {
        this.city = city;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Set<Person> getPersons() {
        return persons;
    }
    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
}