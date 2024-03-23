package pl.rstepniewski.loginpage.secutiry.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {"api/v*/registration/**"};
    private final AuthenticationProvider authenticationProvider;
    //private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
/*                                .requestMatchers("/api/v1/admin/**").hasAnyRole(ADMIN.name())
                                .requestMatchers(GET, "/api/v1/admin/**").hasAnyAuthority(ADMIN_READ.name() )
                                .requestMatchers(POST, "/api/v1/admin/**").hasAnyAuthority(ADMIN_CREATE.name() )
                                .requestMatchers(PUT, "/api/v1/admin/**").hasAnyAuthority(ADMIN_UPDATE.name() )
                                .requestMatchers(DELETE, "/api/v1/admin/**").hasAnyAuthority(ADMIN_DELETE.name() )*/
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                //.logout(logout ->
                //        logout.logoutUrl("/api/v1/auth/logout")
                //                .addLogoutHandler(logoutHandler)
                //                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                //)
        ;

        return http.build();
    }
}
