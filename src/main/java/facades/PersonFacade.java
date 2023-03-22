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
        return new PersonDTO(p);
    }

    public PersonDTO create(PersonDTO personDTO) throws PersonNotFoundException {
        Person p = new Person(personDTO.getFirstName(), personDTO.getLastName(), personDTO.getEmail());
        EntityManager em = getEntityManager();

        // test test test
        Query query = em.createNamedQuery("City.findCity");
        query.setParameter("zipcode", personDTO.getAddress().getZipcode());
        City city = (City) query.getSingleResult();

        Address address = new Address(personDTO.getAddress().getStreet(), personDTO.getAddress().getInfo(), city);
        try {
            TypedQuery<Address> query1 = em.createQuery("select a from Address a where a.street = :street AND a.city = :city", Address.class);
            query1.setParameter("street", address.getStreet());
            query1.setParameter("city", address.getCity());
            List<Address> addresses = query1.getResultList();

            if (addresses.size() > 0) { // if the address exist all ready, set the address object equal to the found address.
                address = query1.getSingleResult();
            }
        } catch (Exception e) {
            throw new PersonNotFoundException("Address not found");
        }

        if (address.getId() == null) {// if the address did not exist, the id will be uninitialized(null).
            // We therefore create the address in the db.

            try {
                em.getTransaction().begin();
                em.persist(address);
                em.getTransaction().commit();
            } catch (Exception e) {
                throw new PersonNotFoundException("could not persist address");
            } finally {
                em.close();
            }
        }
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
