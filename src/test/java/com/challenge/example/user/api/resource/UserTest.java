package com.challenge.example.user.api.resource;

import com.challenge.example.AbstractTest;
import com.challenge.example.user.api.dto.PhoneDTO;
import com.challenge.example.user.api.dto.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserTest extends AbstractTest {

    @Test
    public void getCurrentUserByAnonymousAccess() {
        final Response response = client.target(this.uri).path("/me").request().get();
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void getCurrentUserByUserAccess() {
        final String authHeader = getAuthHeader(tokenOfUser());
        final Response response = client.target(this.uri).path("/me").request()
                .header(HttpHeaders.AUTHORIZATION, authHeader).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }


    @Test
    public void saveUser() {
        final Set<PhoneDTO> phones = new HashSet<>();
        phones.add(new PhoneDTO(988887888, 81, "+55"));
        final UserDTO userDTO = new UserDTO("Hello", "World", "hello@world.com",
                "hunter2", phones, null, null);

        final Response response = client.target(this.uri).path("/signup").request()
                .post(Entity.entity(userDTO, MediaType.APPLICATION_JSON));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}
