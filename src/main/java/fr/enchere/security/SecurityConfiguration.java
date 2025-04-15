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
                        // les requêtes qui vont au chemin "/pageConnecte" doivent être effectuées avec un utilisateur authentifié (peut importe son rôle)
                        .requestMatchers("/pageConnecte").authenticated()
                        // les requêtes qui vont au chemin "/pageAdmin" doivent être effectuées avec un utilisateur authentifié AVEC LE ROLE admin
                        .requestMatchers("/pageAdmin").hasRole("admin")
                        // toutes les autres requêtes qui n'ont pas eu de match avec les précédentes expressions
                        // on laisse accéder à la resource (permitAll())
                        .requestMatchers("/**").permitAll()
                )
                // on effectue une authentification basique (user/mdp)
                .httpBasic(Customizer.withDefaults())
                // on utilise le formulaire par défaut de Spring
                .formLogin(Customizer.withDefaults())
                // quand on se déconnecte=> on redirige vers l'accueil
                .logout((logout) -> logout.logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean // on définit un bean pour l'utilitaire d'encryption de mot de passe
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // ATTENTION : désactiver ce bean lorsqu'on utilise une gestion personalisée des utilisateurs
    // @Bean  on définit un bean pour la gestion des utilisateurs en mémoire
    public InMemoryUserDetailsManager userDetailsService() {
        // je gère à la manoo une liste d'utilisateurs Spring qui doivent implémenter l'interface UserDetails (ici on prend la classe User de Spring Security)
        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(User.withUsername("membre").password(passwordEncoder().encode("azerty"))
                .roles("user").build());
        userDetailsList.add(User.withUsername("admin").password(passwordEncoder().encode("azerty"))
                .roles("admin", "user").build());
        return new InMemoryUserDetailsManager(userDetailsList);
    }

}