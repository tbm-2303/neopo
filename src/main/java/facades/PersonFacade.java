package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import dtos.RenameMeDTO;
import entities.*;
import errorhandling.PersonNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

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


    public PersonDTO getById(int id) { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person p = em.find(Person.class, id);
//        if (rm == null)
//            throw new RenameMeNotFoundException("The RenameMe entity with ID: "+id+" Was not found");
       PersonDTO personDTO = new PersonDTO(p);
       personDTO.setId(id);
        return personDTO;
    }

    public PersonDTO create(PersonDTO personDTO) {
        EntityManager em = getEntityManager();
        Person p = new Person(personDTO.getFirstName(), personDTO.getLastName(), personDTO.getEmail());
        Address a = new Address(personDTO.getAddress());
        City c = (em.find(City.class, personDTO.getAddress().getCity().getId()));//finding the city
        //a.addCity(em.find(City.class, personDTO.getAddress().getCity().getId()));
        a.addCity(c);//adding city to address.




        for (HobbyDTO hobbyDTO : personDTO.getHobbies()) {
            p.addHobby(new Hobby(hobbyDTO));
        }
        for (PhoneDTO phoneDTO : personDTO.getPhones()) {
            p.addPhone(new Phone(phoneDTO));
        }

        try {
            em.getTransaction().begin();
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

}
