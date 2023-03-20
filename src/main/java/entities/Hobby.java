package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Table(name = "hobby")
@Entity
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "name", length = 35)
    private String name;

    @Column(name = "description", length = 35)
    private String description;

    @ManyToMany(mappedBy = "hobbies")
    private Set<Person> persons = new LinkedHashSet<>();


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Set<Person> getPersons() {
        return persons;
    }
    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}