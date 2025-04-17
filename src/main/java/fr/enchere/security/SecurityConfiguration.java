package fr.enchere.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

// on a une classe dans laquelle on va définir la configuration de Spring Security

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/css/**", "/js/**", "/vendor/**","/images/**", "/connexion", "/inscription", "/api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // URL de votre page de connexion personnalisée
                        .loginProcessingUrl("/perform_login") // URL pour traiter la soumission du formulaire
                        .defaultSuccessUrl("/", true) // Redirection après connexion réussie
                        .failureUrl("/connexion?error=true") // Redirection en cas d'échec
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/connexion?logout=true")
                        .permitAll())
                .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**") // désactive CSRF pour les routes REST
        );

        return http.build();
    }

    @Bean // on définit un bean pour l'utilitaire d'encryption de mot de passe
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}