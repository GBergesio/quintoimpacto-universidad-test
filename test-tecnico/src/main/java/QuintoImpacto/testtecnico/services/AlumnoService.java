package QuintoImpacto.testtecnico.services;

import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface AlumnoService {
    ResponseEntity<?> getAllAlumnos();
    ResponseEntity<?> createAlumno(UserRequest alumnoRequest,Authentication authentication);
    ResponseEntity<?> updateAlumno(Long id, UserRequest alumnoRequest,Authentication authentication);
    ResponseEntity<?> deleteAlumno(Long id,Authentication authentication);
    ResponseEntity<?> findAlumnosByLetraCurso(String letra);
    ResponseEntity<?> findByCurso(Long id);
    ResponseEntity<?> findByNombre(String nombre);

}
