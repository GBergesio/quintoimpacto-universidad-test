package QuintoImpacto.testtecnico.services;
import QuintoImpacto.testtecnico.dtos.request.CursoRequest;
import org.springframework.http.ResponseEntity;

public interface CursoService {

    ResponseEntity<?> getAllCursos();
    ResponseEntity<?> createCurso(CursoRequest cursoRequest);
    ResponseEntity<?> updateCurso(Long id, CursoRequest cursoRequest);
    ResponseEntity<?> deleteCurso(Long id);

}
