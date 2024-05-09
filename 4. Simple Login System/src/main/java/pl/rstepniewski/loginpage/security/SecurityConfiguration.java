package pl.rstepniewski.loginpage.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {

	private static final String[] WHITE_LIST_URL = {
			"/",
			"/simple_login/",
			"/simple_login/allowed",
			"/simple_login/register",
			"/simple_login/confirmation",
			"/simple_login/verifyMail/**"
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(req -> req
						.requestMatchers(WHITE_LIST_URL)
						.permitAll()
						.requestMatchers("/simple_login/admin/**").hasRole("ADMIN")
						.requestMatchers("/simple_login/restricted/**").hasAnyRole("ADMIN", "USER")
						.anyRequest()
						.authenticated()
				)
				.formLogin(login -> login
						.loginPage("/simple_login/login")
						.usernameParameter("email")
						.passwordParameter("password")
						.defaultSuccessUrl("/simple_login/", true)
						.permitAll()
				)
				.logout(logout -> logout
						.logoutSuccessUrl("/simple_login/logout-success")
						.permitAll())
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService detailsService, PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
		daoProvider.setUserDetailsService(detailsService);
		daoProvider.setPasswordEncoder(passwordEncoder);
		return new ProviderManager(daoProvider);
	}

}
