package dtos;

import entities.Person;

import java.util.HashSet;
import java.util.Set;


public class PersonDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<HobbyDTO> hobbies = new HashSet<>();

    public PersonDTO() {
    }

    public PersonDTO(Person person){
        if (person.getId() != null){
            this.id = person.getId();
        }
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.email = person.getEmail();
        //this.hobbies = HobbyDTO.getDtos(person.getHobbies());
        person.getHobbies().forEach(hobby->this.hobbies.add(new HobbyDTO(hobby)));
    }




    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email; }
    public void setEmail(String email) {
        this.email = email;
    }
    public Set<HobbyDTO> getHobbies() {
        return hobbies;
    }
    public void setHobbies(Set<HobbyDTO> hobbies) {
        this.hobbies = hobbies;
    }
}

