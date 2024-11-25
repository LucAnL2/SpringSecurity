package vn.iotstar.Service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import vn.iotstar.Entity.Role;
import vn.iotstar.Entity.Users;



public class MyUserService implements UserDetails{
	private static final long serialVersionUID = 1L;
    private final Users user;
    Set<Role> role = null;
    Set<SimpleGrantedAuthority> authorities;

    public MyUserService(Users user) {
        this.user = user;
        role = user.getRoles();
        authorities = Collections.singleton(new SimpleGrantedAuthority(role.toString()));
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        return Collections.singleton(new SimpleGrantedAuthority(roles.toString()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
