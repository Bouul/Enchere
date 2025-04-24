package fr.enchere.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

import java.util.ArrayList;
import java.util.List;

// on a une classe dans laquelle on va définir la configuration de Spring Security

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private GestionPersonaliseeUtilisateurs userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/vendor/**", "/api/bids/filter","/images/**", "/connexion", "/inscription", "/signup", "/enchere", "/forgot-password", "/reset-password", "/").permitAll()

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/enchere", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/deconnexion-confirmation")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .permitAll()
                )
                .rememberMe(remember -> remember
                        .key("uniqueAndSecretKey")
                        .tokenValiditySeconds(86400)
                        .userDetailsService(userDetailsService)
                        .rememberMeParameter("remember-me")
                )
                .sessionManagement(session -> session
                        .invalidSessionUrl("/login?invalid=true")
                        .sessionFixation(fixation -> fixation.migrateSession())
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .sessionAuthenticationErrorUrl("/login?authError=true")
                        .maximumSessions(1)
                        .expiredUrl("/login?expired=true")
                ).csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**")  // Ignorer CSRF pour les APIs
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}