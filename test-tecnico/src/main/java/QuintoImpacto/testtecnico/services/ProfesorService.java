package QuintoImpacto.testtecnico.services;

import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface ProfesorService {
    Boolean loggedUser(Authentication authentication);
    ResponseEntity<?> getAllProfesores();
    ResponseEntity<?> createProfesor(UserRequest profesorRequest,Authentication authentication);
    ResponseEntity<?> updateProfesor(Long id, UserRequest profesorRequest,Authentication authentication);
    ResponseEntity<?> deleteProfesor(Long id,Authentication authentication);
    ResponseEntity<?> releaseCurso(Long idCurso,Authentication authentication);
    ResponseEntity<?> setCursoProfesor(Long idCurso,Long idProfesor,Authentication authentication);
}
