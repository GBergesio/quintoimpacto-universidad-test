package QuintoImpacto.testtecnico.services;
import QuintoImpacto.testtecnico.dtos.request.CursoAlumnoRequest;
import QuintoImpacto.testtecnico.dtos.request.CursoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface CursoService {
    Boolean isAdmin(Authentication authentication);
    ResponseEntity<?> getAllCursos();
    ResponseEntity<?> createCurso(CursoRequest cursoRequest,Authentication authentication);
    ResponseEntity<?> updateCurso(Long id, CursoRequest cursoRequest,Authentication authentication);
    ResponseEntity<?> deleteCurso(Long id,Authentication authentication);
    ResponseEntity<?> setCursoAlumno(CursoAlumnoRequest request, Authentication authentication);
}
