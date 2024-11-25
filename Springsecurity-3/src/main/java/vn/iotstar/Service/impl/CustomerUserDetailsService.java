package vn.iotstar.Service.impl;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.iotstar.Repository.UserRepository;
import vn.iotstar.Entity.Role;
import vn.iotstar.Entity.Users;

@Service
public class CustomerUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository repo;
	public CustomerUserDetailsService(UserRepository repo)
	{
		this.repo = repo;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = repo.findByUsernameOrEmail(username, username)
				.orElseThrow(() ->
				new UsernameNotFoundException("User not found with username or email: " + username));
		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(), mapRolesToAuthorities(user.getRoles()));
		
	}
	private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
