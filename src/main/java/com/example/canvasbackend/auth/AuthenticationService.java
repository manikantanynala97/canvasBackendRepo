package com.example.canvasbackend.auth;

import com.example.canvasbackend.config.JwtService;
import com.example.canvasbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(String email, String password) {
          authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(
                          email,
                          password
                  )
          );
          var user = userRepository.findByEmail(email)
                  .orElseThrow();

          var jwtToken = jwtService.generateToken(user);
          return AuthenticationResponse.builder()
                  .token(jwtToken)
                  .role(user.getRole())
                  .build();
    }
}
