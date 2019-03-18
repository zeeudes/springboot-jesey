package com.challenge.example.user.api.dto;

import com.challenge.example.user.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    @NotBlank(message = "Invalid fields")
    @NotNull(message = "Missing fields")
    private String firstName;

    @NotBlank(message = "Invalid fields")
    @NotNull(message = "Missing fields")
    private String lastName;

    @NotBlank(message = "Invalid fields")
    @NotNull(message = "Missing fields")
    private String email;

    @NotBlank(message = "Invalid fields")
    @NotNull(message = "Missing fields")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    @Valid
    @NotEmpty(message = "Invalid fields")
    @NotNull(message = "Missing fields")
    private Set<PhoneDTO> phones;

    @JsonProperty(value = "created_at")
    private ZonedDateTime createdAt;

    @JsonProperty(value = "last_login")
    private ZonedDateTime lastLogin;

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(),
                PhoneDTO.toPhoneDTOSet(user.getPhones()), user.getCreatedAt(), user.getLastLogin());
    }

    public static User toUser(UserDTO dto) {
        final Gson gson = new Gson();
        return gson.fromJson(gson.toJson(dto), User.class);
    }
}
