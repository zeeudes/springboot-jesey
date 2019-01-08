package com.challenge.example.config.security.api.resource;

import com.challenge.example.AbstractTest;
import com.challenge.example.config.security.api.domain.AuthToken;
import com.challenge.example.config.security.api.domain.Login;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthenticationTest extends AbstractTest {

    @Test
    public void authenticateWithValidCredentials() {

        final Login login = new Login();
        login.setEmail("goku@capsule.corp.com");
        login.setPassword("password");

        final Response response = client.target(this.uri).path("/signin").request()
                .post(Entity.entity(login, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        final AuthToken authToken = response.readEntity(AuthToken.class);
        assertNotNull(authToken);
        assertNotNull(authToken.getToken());
    }

    @Test
    public void authenticateWithInvalidCredentials() {

        final Login login = new Login();
        login.setEmail("invalid-user");
        login.setPassword("wrong-password");

        final Response response = client.target(this.uri).path("/signin").request()
                .post(Entity.entity(login, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }
}
