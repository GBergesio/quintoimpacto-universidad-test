package QuintoImpacto.testtecnico.services;

import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import org.springframework.http.ResponseEntity;

public interface ProfesorService {
    ResponseEntity<?> getAllProfesores();
    ResponseEntity<?> createProfesor(UserRequest profesorRequest);
    ResponseEntity<?> updateProfesor(Long id, UserRequest profesorRequest);
    ResponseEntity<?> deleteProfesor(Long id);
}
