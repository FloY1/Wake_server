package net.proselyte.jwtappdemo.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.proselyte.jwtappdemo.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * Spring Security wrapper for class {@link User}.
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

public class JwtUser implements UserDetails {

    private final Long id;
    private final String phone;
    private final String password;
    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(Long id, String phone, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
