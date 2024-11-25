package vn.iotstar.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
		UserDetails admin = User.withUsername("trung").password(encoder.encode("123")).roles("ADMIN").build();

		UserDetails user = User.withUsername("user").password(encoder.encode("123")).roles("USER").build();

		return new InMemoryUserDetailsManager(admin, user);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable()) // Tắt CSRF (chỉ dùng khi cần)
				.authorizeHttpRequests(auth -> auth.requestMatchers("/").permitAll() // Cho phép truy cập không cần xác
																						// thực
						.requestMatchers(new AntPathRequestMatcher("/customer/**")).authenticated() // Yêu cầu xác thực
				// .anyRequest().authenticated() // Bật nếu cần xác thực toàn bộ các request
				// khác
				).formLogin(Customizer.withDefaults()) // Sử dụng cấu hình mặc định cho form login
				.build();
	}
}
