package fr.enchere.security;

import fr.enchere.model.User;
import fr.enchere.service.UserService;
import fr.enchere.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * On code un service qui va effectuer la gestion personalisée des utilisateurs
 * Cette classe doit implémenter UserDetailsService
 */
@Service // ne pas oublier de mettre Service
public class GestionPersonaliseeUtilisateurs implements UserDetailsService {

    @Autowired
    private UserServiceImpl UserService;

    /*
     * Elle doit donc définir comment on recupère un UtilisateurSpringSecurity
     * à partir d'un pseudo
     * => A partir de notre service qui gère les membres
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFound = UserService.findByUsername(username);
        if (userFound == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec le pseudo: " + username);
        }

        // Vérification si le compte est actif
        if (!userFound.isActive()) {
            throw new UsernameNotFoundException("Ce compte est désactivé. Veuillez contacter l'administrateur.");
        }

        return new UtilisateurSpringSecurity(userFound);
    }
}
