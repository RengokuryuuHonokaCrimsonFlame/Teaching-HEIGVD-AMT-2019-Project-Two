package ch.heigvd.dnd.configuration;

import ch.heigvd.dnd.entities.UtilisateurEntity;
import ch.heigvd.dnd.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository playerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UtilisateurEntity player = playerRepository.findById(username).get();
        if (player == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(player.getEmail(), player.getPassword(),
                new ArrayList<>());
    }

}
