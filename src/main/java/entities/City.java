package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Table(name = "city")
@Entity
@NamedQueries({
        @NamedQuery(name = "City.findCity", query = "select c from City c where c.zipcode = :zipcode")
})
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "zipcode", length = 35)
    private String zipcode;

    @Column(name = "name", length = 35)
    private String name;

    @OneToMany(mappedBy = "city")
    private Set<Address> addresses = new LinkedHashSet<>();



    public City() {
    }
    public City(String zipcode) {
        this.zipcode = zipcode;
    }

    public City(String zipcode, String name) {
        this.zipcode = zipcode;
        this.name = name;
    }
    public City(int id, String zipCode, String name) {
        this.id = id;
        this.zipcode = zipCode;
        this.name = name;
    }


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Set<Address> getAddresses() { return addresses; }
    public void setAddresses(Set<Address> addresses) { this.addresses = addresses; }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }


    public void addAddress(Address address) {
       this.addresses.add(address);

    }
}