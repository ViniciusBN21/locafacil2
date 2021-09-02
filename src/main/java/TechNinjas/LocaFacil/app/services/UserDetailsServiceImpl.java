package TechNinjas.LocaFacil.app.services;

import TechNinjas.LocaFacil.app.models.Usuario;
import TechNinjas.LocaFacil.app.repositories.UsuarioRepository;
import TechNinjas.LocaFacil.app.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Interface central que carrega dados específicos do usuário
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    // Localiza o usuário com base no nome de usuário
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuario = repository.findByEmail(email);
        if(!usuario.isPresent()) {
            throw new UsernameNotFoundException(email);
        }
        return new UserSS(usuario.get().getId(), usuario.get().getEmail(), usuario.get().getPassword(), usuario.get().getPerfis());
    }
}
