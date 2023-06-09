package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Table(name = "person")
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "firstName", length = 35)
    private String firstName;

    @Column(name = "lastName", length = 35)
    private String lastName;

    @Column(name = "email", length = 35)
    private String email;

    @ManyToMany
    @JoinTable(name = "person_hobbies",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "hobbies_id"))
    private Set<Hobby> hobbies = new LinkedHashSet<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    private Set<Phone> phones = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;



    public Person() {
    }

    public Person(String firstName, String lastName,String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


    public Person(int id, String firstName, String lastName, String email, Set<Hobby> hobbies, Set<Phone> phones) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hobbies = hobbies;
        this.phones = phones;
    }
    public Person(int id, String firstName, String lastName, String email, Set<Hobby> hobbies, Set<Phone> phones, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hobbies = hobbies;
        this.phones = phones;
        this.address = address;
    }

    public Person(String firstName, String lastName, String email, Set<Hobby> hobbies, Set<Phone> phones) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hobbies = hobbies;
        this.phones = phones;
    }




    // add methods
    public void addHobby(Hobby hobby){
        this.hobbies.add(hobby);
       hobby.getPersons().add(this);
    }

    public void addPhone(Phone phone){
        this.phones.add(phone);
        phone.setPerson(this);
    }
    public void addAddress(Address address){
        this.address = address;
        address.getPersons().add(this);
    }


    //getters+Setters
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) { this.address = address; }
    public Set<Phone> getPhones() {
        return phones;
    }
    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }
    public Set<Hobby> getHobbies() {
        return hobbies;
    }
    public void setHobbies(Set<Hobby> hobbies) {
        this.hobbies = hobbies;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}