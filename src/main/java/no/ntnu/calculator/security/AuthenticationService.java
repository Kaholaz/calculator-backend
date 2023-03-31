package no.ntnu.calculator.security;

import lombok.RequiredArgsConstructor;
import no.ntnu.calculator.model.User;
import no.ntnu.calculator.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.stream.Collectors;

/**
 * Service for authentication
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    public static final TemporalAmount TOKEN_DURATION = ChronoUnit.MINUTES.getDuration().multipliedBy(10); // 10 minutes
    private final JwtEncoder encoder;
    private final JwtDecoder decoder;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    /**
     * Get the current user
     * @param authentication The authentication object
     * @return The current user
     */
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(TOKEN_DURATION))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Renew a token
     * @param token The token to renew
     * @return The renewed token
     */
    public String renewToken(String token) {
        User user = this.userService.getUser(this.decoder.decode(token).getSubject());
        UserDetails userDetails = new UserDetailsImpl(user);
        Authentication authentication = new UserAuthentication(userDetails);
        return this.generateToken(authentication);
    }

    public void register(String username, String password) {
        userService.createUser(username, passwordEncoder.encode(password));
    }

    /**
     * Get the expiration date of a token
     * @param token The token
     * @return The expiration date
     */
    public Instant getExpirationDate(String token) {
        return this.decoder.decode(token).getExpiresAt();
    }

    public Authentication getAuthentication(String token) {
        String username = this.decoder.decode(token).getSubject();
        User user = this.userService.getUser(username);
        UserDetails userDetails = new UserDetailsImpl(user);

        return new UserAuthentication(userDetails);
    }

    /**
     * Get the currently logged-in user
     * @return The currently logged-in user
     */
    public User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return null;
        String username = auth.getName();
        return this.userService.getUser(username);
    }

    public Authentication authenticate(String username, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
