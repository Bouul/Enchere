//package fr.enchere.security;
//
//import fr.enchere.model.User;
//import fr.enchere.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (userRepository.findByUsername("membre") == null) {
//            User user = new User();
//            user.setUsername("membre");
//            // Encodez le mot de passe avant de l’enregistrer.
//            user.setPassword(passwordEncoder.encode("azerty"));
//            // Définissez les autres propriétés nécessaires à l'entité User.
//            userRepository.save(user);
//        }
//    }
//}
