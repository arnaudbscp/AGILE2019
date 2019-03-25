package fr.ulille.iut.pizzaland.ressources;

import com.sendgrid.Content;
import com.sendgrid.Email;
import fr.ulille.iut.pizzaland.dao.DataAccess;
import fr.ulille.iut.pizzaland.dao.EvenementEntity;
import fr.ulille.iut.pizzaland.dao.UtilisateurEntity;
import fr.ulille.iut.pizzaland.dto.EvenementDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/events")
public class EvenementRessource {
    final static Logger logger = LoggerFactory.getLogger(UtilisateurRessource.class);
    final static String FROM_EMAIL = "dacruzaxel21@gmail.com";

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
    public Response create(EvenementDto evenementDto) {
        DataAccess dataAccess = DataAccess.begin();
        EvenementEntity evenementEntity = EvenementEntity.convertFromEvenementDto(evenementDto);

        System.out.println(evenementDto.toString());
        System.out.println(evenementEntity.toString());

        if (evenementDto.getNom() == null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("name not specified\n").build();
        }
        else if(evenementDto.getDate() == null){
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("date not specified\n").build();
        }
        else if(evenementDto.getHeure() == null){
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("heure not specified\n").build();
        }

        //try {
            long id = dataAccess.createEvent(evenementEntity);
            URI instanceURI = uriInfo.getAbsolutePathBuilder().path("" + id).build();
            dataAccess.closeConnection(true);
            return Response.created(instanceURI).status(201).entity(evenementDto).location(instanceURI).build(); //  .created(instanceURI).build();
        //}
        //catch ( Exception ex ) {
            //dataAccess.closeConnection(false);
            //ex.printStackTrace();
            //return Response.status(Response.Status.CONFLICT).entity("Duplicated name\n").build();
        //}
    }

    /* GET EVENTS WITH DATE */
    @GET
    @Path("/{nom}/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public EvenementDto getNomDate(@PathParam("nom") String nom, @PathParam("date") String date) {
        DataAccess dataAccess = DataAccess.begin();
        List<EvenementEntity> li = dataAccess.getEventByDate(date);
        dataAccess.closeConnection(true);
        return li.stream().filter(e -> e.getNom().equals(nom)).map(EvenementEntity::convertToDto).collect(Collectors.toList()).get(0);
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

    @PUT
    @Path("/{nom}/{login}")
    public Response addUser(@PathParam("nom") String nom, @PathParam("login") String login){
        DataAccess dataAccess = DataAccess.begin();
        EvenementEntity ee = dataAccess.getAllEvents()
                .stream()
                .filter(e -> e.getNom().equals(nom))
                .collect(Collectors.toList())
                .get(0);
        if(ee.getReservations().size() == ee.getPlace()) return Response.status(Response.Status.LENGTH_REQUIRED).entity("Le cours est plein\nj").build();
        try {
            ee.getReservations().add(dataAccess.getUserByLogin(login));
            dataAccess.updateEvent(ee);
            dataAccess.closeConnection(true);
            String TO_EMAIL = dataAccess.getUserByLogin(login).getEmail();
            EvenementEntity evenementEntity = null;
            for (EvenementEntity evenementEntity1 : dataAccess.getEventsByLogin(login)) {
                if(evenementEntity1.getNom().equals(nom)) {
                    evenementEntity = evenementEntity1;
                }
            }
            System.out.println("TO EMAIL: " + TO_EMAIL);
            SendMail sendMail = new SendMail(new Email(FROM_EMAIL), "Evenement à venir", new Email("dacruzaxel21@gmail.com"), new Content());
            if(sendMail.sendMail("Félicitations ! Vous venez de vous inscrire au cours " + nom + ". Le " + evenementEntity.getDate() + " à " + evenementEntity.getHeure() + "jusque " + evenementEntity.getHeureFin() + ", au prix de " + evenementEntity.getPrix() + ". Je vous attendrai !")) {
                System.out.println("MAIL IS SEND TO " + TO_EMAIL);
            }
            else {
                System.out.println("MAIL NOT SEND");
            }
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            dataAccess.closeConnection(false);
            return Response.status(Response.Status.NOT_FOUND).entity("User not found\n").build();
        }
    }

    @GET
    @Path("/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EvenementDto> getEventsOfUser(@PathParam("login") String login){
        DataAccess dataAccess = DataAccess.begin();
        List<EvenementEntity> li = dataAccess.getEventsByLogin(login);
        dataAccess.closeConnection(true);
        return li.stream().map(EvenementEntity::convertToDto).collect(Collectors.toList());
    }
}
