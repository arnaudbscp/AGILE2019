package fr.ulille.iut.pizzaland.ressources;

import fr.ulille.iut.pizzaland.dao.DataAccess;
import fr.ulille.iut.pizzaland.dao.EvenementEntity;
import fr.ulille.iut.pizzaland.dto.EvenementDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/events")
public class EvenementRessource {
    final static Logger logger = LoggerFactory.getLogger(UtilisateurRessource.class);

    @Context
    public UriInfo uriInfo;

    public EvenementRessource() {}

    /* GET ALL EVENTS */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EvenementDto> getAll() {
        DataAccess dataAccess = DataAccess.begin();
        List<EvenementEntity> li = dataAccess.getAllEvents();
        dataAccess.closeConnection(true);
        return li.stream().map(EvenementEntity::convertToDto).collect(Collectors.toList());
    }

    /* POST OR CREATE NEW EVENT */
    @POST
    public Response create(EvenementEntity evenementEntity) {
        DataAccess dataAccess = DataAccess.begin();
        System.out.println(evenementEntity.toString());
        if (evenementEntity.getNom() == null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("name not specified\n").build();
        }
        else if(evenementEntity.getDate() == null){
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("date not specified\n").build();
        }
        else if(evenementEntity.getHeure() == null){
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("heure not specified\n").build();
        }

        try {
            long id = dataAccess.createEvent(evenementEntity);
            URI instanceURI = uriInfo.getAbsolutePathBuilder().path("" + id).build();
            dataAccess.closeConnection(true);
            return Response.created(instanceURI).status(201).entity(evenementEntity).location(instanceURI).build(); //  .created(instanceURI).build();
        }
        catch ( Exception ex ) {
            dataAccess.closeConnection(false);
            return Response.status(Response.Status.CONFLICT).entity("Duplicated name\n").build();
        }
    }

    /* GET EVENTS WITH DATE */
    @GET
    @Path("/{nom}/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EvenementDto> getNomDate(@PathParam("nom") String nom, @PathParam("date") String date) {
        DataAccess dataAccess = DataAccess.begin();
        List<EvenementEntity> li = dataAccess.getEventByDate(date);
        dataAccess.closeConnection(true);
        return li.stream().map(EvenementEntity::convertToDto).collect(Collectors.toList());
    }

    /*DELETE EVENT WITH NAME AND DATE*/
    @DELETE
    @Path("/{nom}/{date}")
    public Response remove(@PathParam("nom") String nom, @PathParam("date") String date) {
        DataAccess dataAccess = DataAccess.begin();
        try {
            dataAccess.deleteEvent(nom, date);
            dataAccess.closeConnection(true);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            dataAccess.closeConnection(false);
            return Response.status(Response.Status.NOT_FOUND).entity("Event not found\n").build();
        }
    }
}
