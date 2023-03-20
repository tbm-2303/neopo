package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import dtos.RenameMeDTO;
import entities.RenameMe;
import facades.PersonFacade;
import utils.EMF_Creator;
import facades.FacadeExample;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("xxx")
public class RenameMeResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final FacadeExample FACADE = FacadeExample.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final PersonFacade personfacade = PersonFacade.getPersonFacade(EMF);

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("count")
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {

        long count = FACADE.getRenameMeCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeById(@PathParam("id") int id) {
        RenameMeDTO rmdto = FACADE.getById(id);
        return GSON.toJson(rmdto);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response postExample(String input) {
        RenameMeDTO rmdto = GSON.fromJson(input, RenameMeDTO.class);
        System.out.println(rmdto);
        rmdto = FACADE.create(rmdto);
        return Response.ok().entity(rmdto).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Response updateExample(@PathParam("id") int id, String input) throws Exception {
        RenameMeDTO update = GSON.fromJson(input, RenameMeDTO.class);
        System.out.println(update);
        update.setId(id);
        update = FACADE.update(update);
        return Response.ok().entity(update).build();
    }


    @GET
    @Path("person{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonById(@PathParam("id") int id) {
        PersonDTO personDTO = personfacade.getById(id);
        return GSON.toJson(personDTO);
    }

    @POST
    @Path("addperson")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createPerson(String input) {
        PersonDTO personDTO = GSON.fromJson(input, PersonDTO.class);
        System.out.println(personDTO);
        personDTO = personfacade.create(personDTO);
        return Response.ok().entity(personDTO).build();
    }

}