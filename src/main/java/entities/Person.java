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
    private Long id;

    @Column(name = "firstName", length = 35)
    private String firstName;

    @Column(name = "last_name", length = 35)
    private String lastName;

    @Column(name = "email", length = 35)
    private String email;

    @ManyToMany
    @JoinTable(name = "person_hobbies",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "hobbies_id"))
    private Set<Hobby> hobbies = new LinkedHashSet<>();



    // add + remove
    public void addHobby(Hobby hobby){
        this.hobbies.add(hobby);
        if (hobby == null){

        }
    }



    //getters+Setters
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
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}