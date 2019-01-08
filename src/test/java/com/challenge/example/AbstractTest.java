package com.challenge.example;

import com.challenge.example.config.security.api.domain.AuthToken;
import com.challenge.example.config.security.api.domain.Login;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.net.URI;

public abstract class AbstractTest {
    @Value("${server.port}")
    private Integer port;
    @Value("${app.uri.base}")
    private String uriBase;
    protected URI uri;
    protected Client client;

    @Before
    public void init() throws Exception {
        this.uri = new URI(uriBase.concat(port.toString()));
        this.client = ClientBuilder.newClient();
    }

    protected String tokenOfUser() {

        final Login login = new Login();
        login.setEmail("nanadaime@konoha.com");
        login.setPassword("password");

        final AuthToken authToken = client.target(this.uri).path("/signin").request()
                .post(Entity.entity(login, MediaType.APPLICATION_JSON), AuthToken.class);
        return authToken.getToken();
    }

    protected String getAuthHeader(String authToken) {
        return "Bearer" + " " + authToken;
    }
}
