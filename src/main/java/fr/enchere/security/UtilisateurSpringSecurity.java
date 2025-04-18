package fr.enchere.security;

import fr.enchere.model.User;
import fr.enchere.service.UserService;
import fr.enchere.service.UserServiceImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;

/**
 * Cette classe est un "wrapper" autour de ma classe Membre
 * Afin de pouvoir gérer moi même mes utilisateurs qui vont être verifiés par Spring Security
 */

public class UtilisateurSpringSecurity implements UserDetails {

    private User membre;

    public UtilisateurSpringSecurity(User membre) {
        this.membre = membre;
    }
    /**
     * Comment je recupère les permissions  ?
     * => à partir du membre
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // si le membre est admin
//        if (membre.isAdmin()) {
//            // je lui donne le rôle "admin" (équivalent à la permission "ROLE_admin")
//            return List.of(new SimpleGrantedAuthority("ROLE_admin"));
//        }
        // sinon je lui donne le rôle "user (équivalent à la permission "ROLE_user")
        return List.of(new SimpleGrantedAuthority("ROLE_user"));
    }

    /**
     * Comment je recupère le mot de passe ?
     * => à partir du membre
     */
    @Override
    public String getPassword() {
        return this.membre.getPassword();
    }

    /**
     * Comment je recupère le pseudo  ?
     * => à partir du membre
     */
    @Override
    public String getUsername() {
        return this.membre.getUsername();
    }

    // À ajouter dans la classe UtilisateurSpringSecurity
    @Override
    public boolean isAccountNonExpired() {
        return true;  // ou logique personnalisée
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // ou logique personnalisée
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // ou logique personnalisée
    }

    @Override
    public boolean isEnabled() {
        return true;  // ou logique personnalisée selon l'état de l'utilisateur
    }

    public Long getUserId() {
        return this.membre.getUserId();
    }
}
