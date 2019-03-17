package com.challenge.example.pojo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MessageError {
    private String message;
    private Integer errorCode;
}
