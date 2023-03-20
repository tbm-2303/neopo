/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.RenameMeDTO;
import entities.Hobby;
import entities.Person;
import entities.RenameMe;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        FacadeExample fe = FacadeExample.getFacadeExample(emf);
        fe.create(new RenameMeDTO(new RenameMe("First 1", "Last 1")));
        fe.create(new RenameMeDTO(new RenameMe("First 2", "Last 2")));
        fe.create(new RenameMeDTO(new RenameMe("First 3", "Last 3")));
    }


    public static void populatePersons(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade pf = PersonFacade.getPersonFacade(emf);
        pf.create(new PersonDTO(new Person("Timmy","mortensen","timmy@hotmail.com")));
        pf.create(new PersonDTO(new Person("James","mortensen","James@hotmail.com")));
    }

    public static void populateHobbies(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        HobbyFacade hf = HobbyFacade.getHobbyFacade(emf);
        hf.create(new HobbyDTO(new Hobby("badminton","spil med fjerbold")));

    }
    
    public static void main(String[] args) {
        populateHobbies();
    }
}
