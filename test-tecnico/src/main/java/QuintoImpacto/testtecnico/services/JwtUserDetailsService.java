package QuintoImpacto.testtecnico.services;

import QuintoImpacto.testtecnico.models.Administrador;
import QuintoImpacto.testtecnico.models.Alumno;
import QuintoImpacto.testtecnico.models.Profesor;
import QuintoImpacto.testtecnico.repositories.AdministradorRepository;
import QuintoImpacto.testtecnico.repositories.AlumnoRepository;
import QuintoImpacto.testtecnico.repositories.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private AdministradorRepository administradorRepository;
    @Autowired
    private ProfesorRepository profesorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Alumno alumno = alumnoRepository.findByEmail(username);
        Profesor profesor = profesorRepository.findByEmail(username);
        Administrador administrador = administradorRepository.findByEmail(username);
        if(alumno != null){
            return new User(alumno.getEmail(), alumno.getPassword(), AuthorityUtils.createAuthorityList("ALUMNO"));
        }
        if(administrador != null){
            return new User(administrador.getEmail(), administrador.getPassword(), AuthorityUtils.createAuthorityList("ALUMNO"));
        }
        if(profesor != null){
            return new User(profesor.getEmail(), profesor.getPassword(), AuthorityUtils.createAuthorityList("ALUMNO"));
        }
        else {
            throw new UsernameNotFoundException("Usuario desconocido: " + username);
        }
    }
}
