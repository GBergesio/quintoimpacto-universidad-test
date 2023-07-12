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

// Este método busca un usuario en diferentes repositorios según su email y devuelve un objeto UserDetails que contiene los detalles necesarios
// para la autenticación y autorización del usuario.
// Es una implementación del método definido en la interfaz UserDetailsService de Spring Security.
// Su función principal es cargar los detalles de un usuario basándose en su nombre de usuario (en este caso, el parámetro username).
// Se realizan consultas a las instancias de AlumnoRepository, ProfesorRepository y AdministradorRepository para buscar un usuario con el email proporcionado (username).
// Si se encuentra un registro en alumnoRepository, se crea un objeto User utilizando el email y contraseña del alumno, y se asigna el rol "ALUMNO"
// mediante AuthorityUtils.createAuthorityList("ALUMNO").
// Esto mismo sucede con Profesor y Administrador...
// Si no se encuentra ningún usuario con el email proporcionado, se lanza una excepción UsernameNotFoundException indicando que el usuario es desconocido.
// En caso de encontrar un usuario correspondiente, se devuelve el objeto User creado con los detalles de ese usuario.