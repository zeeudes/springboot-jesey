package com.challenge.example.user.api.dto;

import com.challenge.example.user.entity.Phone;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO implements Serializable {

    @NotNull(message = "Missing fields")
    private Integer number;

    @NotNull(message = "Missing fields")
    @JsonProperty(value = "area_code")
    private Integer areaCode;

    @NotBlank(message = "Invalid fields")
    @NotNull(message = "Missing fields")
    @JsonProperty(value = "country_code")
    private String countryCode;

    public static Set<PhoneDTO> toPhoneDTOSet(Set<Phone> phones) {
        Gson gson = new Gson();
        return phones.stream().map(gson::toJson).map(json -> gson.fromJson(json, PhoneDTO.class)).collect(Collectors.toSet());
    }
}