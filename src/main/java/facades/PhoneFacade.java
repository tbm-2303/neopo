package facades;

import dtos.HobbyDTO;
import entities.Hobby;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class PhoneFacade {

    private static PhoneFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PhoneFacade() {
    }

    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PhoneFacade getPhoneFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PhoneFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

/*
    public List<HobbyDTO> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p", Phone.class);
        List<Hobby> hobbies = query.getResultList();
        return HobbyDTO.getDtos(hobbies);
    }

 */
}
