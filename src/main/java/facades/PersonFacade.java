package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import dtos.RenameMeDTO;
import entities.*;
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


    public PersonDTO getById(int id) { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person p = em.find(Person.class, id);
//        if (rm == null)
//            throw new RenameMeNotFoundException("The RenameMe entity with ID: "+id+" Was not found");
        PersonDTO personDTO = new PersonDTO(p);
        personDTO.setId(id);
        return personDTO;
    }

    public PersonDTO create(PersonDTO personDTO) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        Person p = new Person(personDTO.getFirstName(), personDTO.getLastName(), personDTO.getEmail());
        for (HobbyDTO hobbyDTO : personDTO.getHobbies()) {
            p.addHobby(new Hobby(hobbyDTO));
        }
        for (PhoneDTO phoneDTO : personDTO.getPhones()) {
            p.addPhone(new Phone(phoneDTO));
        }
        Address a = new Address(personDTO.getAddress().getStreet(), personDTO.getAddress().getInfo());
        //a = checkIfAddressExists(a, personDTO.getAddress().getCity().getId());
        City c = (em.find(City.class, personDTO.getAddress().getCity().getId()));//finding the city
        a.addCity(c);//adding city to address.
        p.addAddress(a);

        try {
            em.getTransaction().begin();
            if (a.getId()!= null){
                em.merge(a);
            }
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



    public Address checkIfAddressExists(Address address, int city_id){
        EntityManager em = emf.createEntityManager();
        Address addressFound;
        try{
            TypedQuery<Address> query = em.createQuery("SELECT a from Address a WHERE a.street=:street and a.info=:info and a.city.id =:city_id",Address.class);
            query.setParameter("street",address.getStreet());
            query.setParameter("info",address.getInfo());
            query.setParameter("city_id",city_id);
            try{
                addressFound = query.getSingleResult();
            }catch (NoResultException e){
                e.printStackTrace();
                address.addCity(em.find(City.class,city_id));
                return address;
            }
        }finally {
            em.close();
        }
        return addressFound;
    }

}
