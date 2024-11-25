package vn.iotstar.Configs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import vn.iotstar.Service.impl.CustomerUserDetailsService;
import vn.iotstar.Service.impl.UserServiceImpl;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
	@Autowired
	private CustomerUserDetailsService userDetailsService;
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserServiceImpl();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
	}
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception
	{
		final List<GlobalAuthenticationConfigurerAdapter> configurers = new ArrayList<>();
		configurers.add(new GlobalAuthenticationConfigurerAdapter() {
			@Override
			public void configure(AuthenticationManagerBuilder auth) throws Exception
			{
				//
			}
		});
		return authConfig.getAuthenticationManager();
	}
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
        		.authorizeHttpRequests(auth -> auth
        		.requestMatchers("/").hasAnyAuthority("USER","ADMIN","EDITOR","CREATOR")
        		.requestMatchers("/new").hasAnyAuthority("ADMIN","CREATOR")
        		.requestMatchers("/edit/**").hasAnyAuthority("ADMIN","EDITOR")
        		.requestMatchers("/delete/**").hasAnyAuthority("ADMIN")
        		.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
        		.requestMatchers("/api/**").permitAll()
        		.anyRequest().authenticated())
        	.httpBasic(Customizer.withDefaults())
        	.formLogin(login -> login.loginPage("/login").permitAll())
        	.logout(logout -> logout.permitAll())
        	.exceptionHandling(handling -> handling.accessDeniedPage("/403"))
        	.build();
    }
}

