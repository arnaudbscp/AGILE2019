package fr.ulille.iut.ramponno;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/v1/")
public class ApiV1 extends ResourceConfig {

    public ApiV1() {
        packages("fr.ulille.iut.ramponno.ressources");
        register(CORSFilter.class);

    }

}

