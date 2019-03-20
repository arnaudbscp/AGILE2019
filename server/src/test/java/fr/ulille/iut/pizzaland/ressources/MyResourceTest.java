package fr.ulille.iut.pizzaland.ressources;

import fr.ulille.iut.pizzaland.ApiV1;
import fr.ulille.iut.pizzaland.dao.DataAccess;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MyResourceTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ApiV1();
    }

    @Test
    public void testGetIt() {
        String responseMsg = target("myresource").request().get(String.class);
        assertEquals("Got it!", responseMsg);
    }

    @Test
    public void testInitH2() {
        DataAccess dataAccess = DataAccess.begin();
        UtilisateurEntity utilisateur = dataAccess.getUserById(1);
        assertNotNull(utilisateur);
        assertEquals("axel", utilisateur.getLogin());
        dataAccess.closeConnection(false);
    }
}
