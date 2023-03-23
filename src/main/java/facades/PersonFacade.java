package facades;

import dtos.*;
import entities.*;
import errorhandling.NotFoundException;
import errorhandling.PersonNotFoundException;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonFacade {
    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }


    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public PersonDTO getById(int id) throws PersonNotFoundException { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person p = em.find(Person.class, id);
        if (p == null)
            throw new PersonNotFoundException("The Person entity with ID: "+id+" Was not found");
        PersonDTO personDTO = new PersonDTO(p);
        personDTO.setId(id);
        return personDTO;
    }

    //TODO:
    // if we create a person with a given address and city and then try to create a new person with the same
    // address, we will get problems. So if the address exist, then the address and relation to city is already established.
    // So we only need to add the new person to the address and then persist the person.
    public PersonDTO create(PersonDTO personDTO) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        Person p = new Person(personDTO.getFirstName(), personDTO.getLastName(), personDTO.getEmail());

        for (HobbyDTO hobbyDTO : personDTO.getHobbies()) {
            p.addHobby(em.find(Hobby.class, hobbyDTO.getId())); //only cares about hobby id given in the request json.
        }

        for (PhoneDTO phoneDTO : personDTO.getPhones()) {
            p.addPhone(new Phone(phoneDTO));
        }
        Address a = new Address(personDTO.getAddress().getStreet(), personDTO.getAddress().getInfo());
        //a = checkIfAddressExists(a, personDTO.getAddress().getCity().getId());
        City c = em.find(City.class, personDTO.getAddress().getCity().getId());//finding the city
        a.addCity(c);//adding city to address.
        p.addAddress(a);

        try {
            em.getTransaction().begin();
            em.persist(a);
            em.persist(p);
            for (Phone phone : p.getPhones()) {
                em.persist(phone);
            }

            for (Hobby hobby : p.getHobbies()) {
                em.persist(hobby);
            }


            em.getTransaction().commit();
        } finally {
            em.close();
        }
        PersonDTO pDTO = new PersonDTO(p);
        pDTO.setId(p.getId());
        return pDTO;
    }

    public List<PersonDTO> getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            List<Person> persons = query.getResultList();
            List<PersonDTO> list = new ArrayList<>();
            for (Person p : persons) {
                list.add(new PersonDTO(p));
            }
            return list;

        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getAllPersons2() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            List<Person> persons = query.getResultList();
            List<PersonDTO> list = new ArrayList<>();

            for (Person p : persons) {
                list.add(new PersonDTO(p.getFirstName(), p.getLastName(), p.getEmail(), HobbyDTO.getDtos(p.getHobbies())));
            }
            return list;

        } finally {
            em.close();
        }
    }


    public AddressDTO checkIfAddressExists(Address address) throws NotFoundException {
        EntityManager em = getEntityManager();
        Address addressFound;
        try {
            TypedQuery<Address> query = em.createQuery("SELECT a from Address a WHERE a.street=:street and a.info=:info", Address.class);
            query.setParameter("street", address.getStreet());
            query.setParameter("info", address.getInfo());
            addressFound = query.getSingleResult();
            return new AddressDTO(addressFound);
        } catch (Exception e){
            throw new NotFoundException("cant find the given address");

        }
        finally {
            em.close();
        }
    }


    public int getNumberOfPeopleWithGivenHobby(int hobbyId) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Hobby> query = em.createQuery("select h FROM Hobby h WHERE h.id=:hobbyId", Hobby.class);
            query.setParameter("hobbyId", hobbyId);
            try {
                Hobby hobby = query.getSingleResult();
                return hobby.getPersons().size();
            } catch (Exception e) {
                throw new NotFoundException("Cant find hobby with the given id");
            }
        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getPersonsByHobby(int hobbyid) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<PersonDTO> query = em.createQuery("SELECT new dtos.PersonDTO(p) FROM Person p join p.hobbies h where h.id=:hobbyid", PersonDTO.class);
            query.setParameter("hobbyid", hobbyid);
            List<PersonDTO> personDTOs = query.getResultList();
            return personDTOs;
        } finally {
            em.close();
        }
    }

    public PersonDTO addHobbyToPersonByIds(int person_id, int hobby_id) {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, person_id);
        Hobby hobby = em.find(Hobby.class, hobby_id);
        person.addHobby(hobby);
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

}
