package fr.ulille.iut.ramponno.ressources;

import fr.ulille.iut.ramponno.dao.DataAccess;
import fr.ulille.iut.ramponno.dao.UtilisateurEntity;
import fr.ulille.iut.ramponno.dto.UtilisateurDto;
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

@Path("/users")
public class UtilisateurRessource {
    final static Logger logger = LoggerFactory.getLogger(UtilisateurRessource.class);
    @Context
    public UriInfo uriInfo;

    public UtilisateurRessource() {}

    /* GET ALL USERS */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UtilisateurDto> getAll() {
        DataAccess dataAccess = DataAccess.begin();
        List<UtilisateurEntity> li = dataAccess.getAllUsers();
        dataAccess.closeConnection(true);
        return li.stream().map(UtilisateurEntity::convertToDto).collect(Collectors.toList());
    }

    /* POST OR CREATE A NEW USER */
    @POST
    public Response create(UtilisateurEntity utilisateurEntity) {
        DataAccess dataAccess = DataAccess.begin();
        if (utilisateurEntity.getLogin() == null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("login not specified\n").build();
        }
        else if(utilisateurEntity.getPassword() == null){
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("password not specified\n").build();
        }
        else if(utilisateurEntity.getEmail() == null){
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("email not specified\n").build();
        }
        utilisateurEntity.setRole("user");

        try {
            long id = dataAccess.createUser(utilisateurEntity);
            URI instanceURI = uriInfo.getAbsolutePathBuilder().path("" + id).build();
            dataAccess.closeConnection(true);
            return Response.created(instanceURI).status(201).entity(utilisateurEntity).location(instanceURI).build(); //  .created(instanceURI).build();
        }
        catch ( Exception ex ) {
            dataAccess.closeConnection(false);
            return Response.status(Response.Status.CONFLICT).entity("Duplicated login\n").build();
        }
    }

    /* GET ONE USER WITH LOGIN AND PASSWORD*/
    @GET
    @Path("/{login}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoginPassword(@PathParam("login") String login, @PathParam("password") String password) {
        DataAccess dataAccess = DataAccess.begin();
        UtilisateurEntity utilisateurEntity = dataAccess.getUserByLoginPassword(login, password);
        if ( utilisateurEntity != null ) {
            dataAccess.closeConnection(true);
            return Response.ok(utilisateurEntity).build();
        } else {
            dataAccess.closeConnection(false);
            return Response.status(Response.Status.NOT_FOUND).entity("User not found\n").build();
        }
    }

    /* UPADATE ONE USER WITH LOGIN AND PASSWORD */
    @PUT
    @Path("/{login}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("login") String login, @PathParam("password") String password,UtilisateurEntity utilisateurEntity) {
        DataAccess dataAccess = DataAccess.begin();
        UtilisateurEntity utilisateurBDD = dataAccess.getUserByLoginPassword(login, password);
        if (utilisateurBDD == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found\n").build();
        } else {
            try {
                utilisateurBDD.setEmail(utilisateurEntity.getEmail());
                utilisateurBDD.setPassword(utilisateurEntity.getPassword());
                dataAccess.updateUser(utilisateurBDD);
                dataAccess.closeConnection(true);
                return Response.ok(utilisateurBDD).build(); //  .created(instanceURI).build();
            } catch (Exception ex) {
                dataAccess.closeConnection(false);
                return Response.status(Response.Status.CONFLICT).entity("Duplicated login \n").build();
            }
        }
    }

    /* DELETE ONE USER WITH LOGIN*/
    @DELETE
    @Path("/{login}")
    public Response remove(@PathParam("login") String login) {
        DataAccess dataAccess = DataAccess.begin();
        try {
            dataAccess.deleteUser(login);
            dataAccess.closeConnection(true);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            dataAccess.closeConnection(false);
            return Response.status(Response.Status.NOT_FOUND).entity("User not found\n").build();
        }
    }
}
