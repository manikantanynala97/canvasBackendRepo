package com.example.canvasbackend.auth;

import com.example.canvasbackend.entity.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
    private Role role;

}
