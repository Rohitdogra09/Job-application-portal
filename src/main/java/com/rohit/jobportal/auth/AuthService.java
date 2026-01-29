package com.rohit.jobportal.auth;

import com.rohit.jobportal.entity.*;
import com.rohit.jobportal.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.exp-minutes}")
    private long expMinutes;

    // LOGIN
    public AuthResponse login(LoginRequest req) {

        // Authenticate email + password
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.email(), req.password()
                )
        );

        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(expMinutes * 60);

        // Roles → JWT scope
        String scope = auth.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(expiry)
                .subject(auth.getName())
                .claim("scope", scope)
                .build();

        String token = jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();

        return new AuthResponse(token, "Bearer", expMinutes * 60);
    }

    // REGISTER USER
    public void register(RegisterRequest req) {

        if (userRepository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow();

        User user = User.builder()
                .email(req.email())
                .fullName(req.fullName())
                .passwordHash(passwordEncoder.encode(req.password()))
                .enabled(true)
                .roles(java.util.Set.of(userRole))
                .build();

        userRepository.save(user);
    }
}