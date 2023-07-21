package QuintoImpacto.testtecnico.utils;

import QuintoImpacto.testtecnico.models.Administrador;
import QuintoImpacto.testtecnico.models.Alumno;
import QuintoImpacto.testtecnico.models.Profesor;
import QuintoImpacto.testtecnico.repositories.AdministradorRepository;
import QuintoImpacto.testtecnico.repositories.AlumnoRepository;
import QuintoImpacto.testtecnico.repositories.ProfesorRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {
    public static Object getLoggedUser(Authentication authentication, AdministradorRepository administradorRepository, ProfesorRepository profesorRepository, AlumnoRepository alumnoRepository) {
        String email = authentication.getName();

        Administrador administrador = null;
        Profesor profesor = null;
        Alumno alumno = null;

        if (authentication.getPrincipal() instanceof Administrador) {
            administrador = (Administrador) authentication.getPrincipal();
        } else {
            administrador = administradorRepository.findByEmail(email);
            profesor = profesorRepository.findByEmail(email);
            alumno = alumnoRepository.findByEmail(email);
        }

        if (administrador != null) {
            return administrador;
        } else if (profesor != null) {
            return profesor;
        } else if (alumno != null) {
            return alumno;
        } else {
            throw new RuntimeException(MessageRepository.getErrorNoUser());
        }
    }



}
