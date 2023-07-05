package QuintoImpacto.testtecnico.services;

import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import org.springframework.http.ResponseEntity;

public interface AlumnoService {
    ResponseEntity<?> getAllAlumnos();
    ResponseEntity<?> createAlumno(UserRequest alumnoRequest);
    ResponseEntity<?> updateAlumno(Long id, UserRequest alumnoRequest);
    ResponseEntity<?> deleteAlumno(Long id);
    ResponseEntity<?> findAlumnosByLetraCurso(String letra);
    ResponseEntity<?> findByCurso(Long id);
    ResponseEntity<?> findByNombre(String nombre);

}
