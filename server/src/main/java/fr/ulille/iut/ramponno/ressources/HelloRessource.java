package fr.ulille.iut.ramponno.ressources;

import fr.ulille.iut.HelloWorld;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Hello resource (exposed at "hello" path)
 */
@Path("hello")
public class HelloRessource extends HelloWorld {
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return this.say();
    }
}
