package dtos;

import entities.Hobby;
import entities.Person;
import entities.Phone;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PersonDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<HobbyDTO> hobbies = new HashSet<>();
    private Set<PhoneDTO> phones = new HashSet<>();

    private AddressDTO address;//test test test

    public PersonDTO() {
    }

    public PersonDTO(Person person) {

        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.email = person.getEmail();
        //this.hobbies = HobbyDTO.getDtos(person.getHobbies());
        //person.getHobbies().forEach(hobby -> this.hobbies.add(new HobbyDTO(hobby))); //trying something new
        //person.getPhones().forEach(phone -> this.phones.add(new PhoneDTO(phone))); //trying something new
        this.hobbies = HobbyDTO.getDtos(person.getHobbies());
        this.phones = PhoneDTO.getDtos(person.getPhones());
        this.address = new AddressDTO(person.getAddress()); // test test test
    }

    public PersonDTO(String firstName, String lastName, String email, Set<HobbyDTO> hobbies, Set<PhoneDTO> phones, AddressDTO address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hobbies = hobbies;
        this.phones = phones;
        this.address = address;
    }

    public Set<Hobby> getHobbiesDTOS(){
        Set<Hobby> hobbies2 = new HashSet<>();
        for (HobbyDTO hobbyDTO : hobbies) {
            Hobby hobby = new Hobby(hobbyDTO.getName(),hobbyDTO.getDescription());
            hobbies2.add(hobby);
        }
        return hobbies2;
    }
    public static List<PersonDTO> getDtos(List<Person> personList){
        List<PersonDTO> personDTOS = new ArrayList();
        personList.forEach(person->personDTOS.add(new PersonDTO(person)));
        return personDTOS;
    }

    public AddressDTO getAddress() {
        return address;
    }
    public void setAddress(AddressDTO address) {
        this.address = address;
    }
    public Set<PhoneDTO> getPhones() {
        return phones;
    }
    public void setPhones(Set<PhoneDTO> phones) {
        this.phones = phones;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
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
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Set<HobbyDTO> getHobbies() {
        return hobbies;
    }
    public void setHobbies(Set<HobbyDTO> hobbies) {
        this.hobbies = hobbies;
    }


    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", hobbies=" + hobbies +
                ", phones=" + phones +
                '}';
    }
}

