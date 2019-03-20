package fr.ulille.iut.pizzaland.ressources;

import fr.ulille.iut.pizzaland.Main;
import fr.ulille.iut.pizzaland.dto.IngredientDto;
import fr.ulille.iut.pizzaland.dto.IngredientPayloadDto;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import java.util.List;

import static org.junit.Assert.assertEquals;

public class IngredientRessourceTest {
    private static HttpServer server;
    private WebTarget target;

    @BeforeClass
    public static void setupServer() {
        // start the server
        server = Main.startServer(63002);
    }

    @AfterClass
    public static void shutdownServer() {
        server.shutdownNow();
    }

    @Before
    public void setup() {
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.getURI(63002));
    }

    @Test
    public void testGetAllUsers() {
        Response response = target.path("/users")
                .request()
                .get();
//        List<IngredientDto> ingredients;
//        ingredients = response.readEntity(new GenericType<List<IngredientDto>>(){});
//        assertEquals(8, ingredients.size());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testCreateUser() {
        UtilisateurDto utilisateur = new UtilisateurDto();
        utilisateur.setLogin("Cedric");
        utilisateur.setPassword("cedlefou");
        utilisateur.setEmail("cedric59@hotmail.fr");
        Response response = target.path("/users")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(ingredient));
        UtilisateurDto returnedEntity = response.readEntity(UtilisateurDto.class);

        assertEquals(returnedEntity.getLogin(), ingredient.getLogin());
        assertEquals(returnedEntity.getPassword(), ingredient.getPassword());
        assertEquals(returnedEntity.getEmail(), ingredient.getEmail());
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals((target.path("/users/" + returnedEntity.getId())).getUri(), response.getLocation());
    }

    @Test
    public void testCreateUtilisateur_406_login() {
        UtilisateurDto ingredient = new UtilisateurDto();
        utilisateur.setLogin(null);
        Response response = target.path("/users")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(utilisateur));
        String returned = response.readEntity(String.class);

        assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
        assertEquals("login not specified\n", returned);
    }

    @Test
    public void testCreateUtilisateur_406_password() {
        UtilisateurDto ingredient = new UtilisateurDto();
        utilisateur.setLogin("login");
        utilisateur.setPassword(null);
        Response response = target.path("/users")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(utilisateur));
        String returned = response.readEntity(String.class);

        assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
        assertEquals("password not specified\n", returned);
    }

    @Test
    public void testCreateUtilisateur_406_login() {
        UtilisateurDto ingredient = new UtilisateurDto();
        utilisateur.setLogin("login");
        utilisateur.setPassword("password");
        utilisateur.setEmail(null);
        Response response = target.path("/users")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(utilisateur));
        String returned = response.readEntity(String.class);

        assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
        assertEquals("email not specified\n", returned);
    }

    @Test
    public void testCreateUtilisateur_409() {
        UtilisateurDto utilisateur = new UtilisateurDto();
        utilisateur.setLogin("axel");
        Response response = target.path("/users")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(utilisateur));

        String returned = response.readEntity(String.class);
        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
        assertEquals("Duplicated login", returned);
    }

    @Test
    public void testGetOneUser() {
        Response response = target.path("/users/axel/axel")
                .request()
                .get();

        UtilisateurDto utilisateur;
        utilisateur = response.readEntity(UtilisateurDto.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("", utilisateur.getEmail());
    }

    @Test
    public void testGetOneUtilisateur_404() {
        Response response = target.path("/users/6000")
                .request()
                .get();

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDeleteOneUtilisateur() {
        Response response = target.path("/users/axel/axel")
                .request()
                .delete();

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDeleteOneIngredient_404() {
        Response response = target.path("/users/41200")
                .request()
                .delete();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}
