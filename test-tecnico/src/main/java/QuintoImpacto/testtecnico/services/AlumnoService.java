package QuintoImpacto.testtecnico.services;

import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import org.springframework.http.ResponseEntity;

public interface AlumnoService {
    ResponseEntity<?> getAllAlumnos();
    ResponseEntity<?> createAlumno(UserRequest alumnoRequest);
    ResponseEntity<?> updateAlumno(Long id, UserRequest alumnoRequest);
    ResponseEntity<?> deleteAlumno(Long id);
}
