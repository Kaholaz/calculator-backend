package no.ntnu.calculator.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import no.ntnu.calculator.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * User details implementation
 */
@Getter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final Long id;
    private final String username;
    private final String password;

    /**
     * Create user details from a user entity
     * @param user The user entity
     */
    public UserDetailsImpl(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }


    /**
     * Get the authorities of the user (admin or user)
     * @return The authorities of the user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((GrantedAuthority) () -> "ROLE_USER");
    }

    /**
     * Get the username of the user (email)
     * @return The username of the user
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Check if the account is not expired (always true)
     * @return True if the account is not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Check if the account is not locked (always true)
     * @return True if the account is not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Check if the credentials are not expired (always true)
     * @return True if the credentials are not expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Check if the account is enabled (always true)
     * @return True if the account is enabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
