package fr.enchere.security;

import fr.enchere.model.User;
import fr.enchere.service.UserService;
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
    private UserService UserService;

    /*
     * Elle doit donc définir comment on recupère un UtilisateurSpringSecurity
     * à partir d'un pseudo
     * => A partir de notre service qui gère les membres
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User membreTrouve =  UserService.getPersonByPseudo(username);

        return new UtilisateurSpringSecurity(membreTrouve);
    }
}
