package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import dtos.RenameMeDTO;
import errorhandling.NotFoundException;
import errorhandling.PersonNotFoundException;
import facades.FacadeExample;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("Person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"person endpoint root\"}";
    }

    @GET
    @Path("getAll")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonsWithHobby()  {
        List<PersonDTO> list= FACADE.getAllPersons();
        return Response.ok().entity(GSON.toJson(list)).build();
    }
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonById(@PathParam("id") int id) {
        PersonDTO personDTO = FACADE.getById(id);
        return Response.ok().entity(GSON.toJson(personDTO)).build();
    }

    @POST
    @Path("/create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createPerson(String input) throws PersonNotFoundException {
        PersonDTO personDTO = GSON.fromJson(input, PersonDTO.class);
        System.out.println(personDTO);
        personDTO = FACADE.create(personDTO);
        return Response.ok().entity(GSON.toJson(personDTO)).build();
    }


    //get number of people with given interest
    @GET
    @Path("/getAllByHobby/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getNumberOfPeopleWithGivenHobby(@PathParam("id") int id) throws NotFoundException {
        int amountOfPersons= FACADE.getNumberOfPeopleWithGivenHobby(id);
        return Response.ok().entity("{\"amount\":"+amountOfPersons+"}").build();
    }

    @GET
    @Path("/persons/{hobbyID}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPersonsByHobby(@PathParam("hobbyID") int hobbyID)  {
        List<PersonDTO> personDTOList = FACADE.getPersonsByHobby(hobbyID);
        return Response.ok().entity(GSON.toJson(personDTOList)).build();
    }

    //add hobby to person
    @POST
    @Path("addhobby/{person_id}/{hobby_id}")
    @Produces("application/json")
    public Response addHobbyToPersonByIds(@PathParam("person_id") int person_id, @PathParam("hobby_id") int hobby_id) {
        PersonDTO person = FACADE.addHobbyToPersonByIds(person_id, hobby_id);
        return Response.ok().entity(GSON.toJson(person)).build();
    }
}
