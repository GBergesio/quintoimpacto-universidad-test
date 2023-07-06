package QuintoImpacto.testtecnico.controllers;

import QuintoImpacto.testtecnico.dtos.request.CursoAlumnoRequest;
import QuintoImpacto.testtecnico.dtos.request.CursoRequest;
import QuintoImpacto.testtecnico.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    CursoService cursoService;

    @GetMapping("/current")
    ResponseEntity<?> getAllCursos() {
        return cursoService.getAllCursos();
    }

    @PostMapping("/current")
    ResponseEntity<?> newCurso(@RequestBody CursoRequest cursoRequest,Authentication authentication) {
        return cursoService.createCurso(cursoRequest,authentication);
    }

    @PatchMapping("/current/id/{id}")
    ResponseEntity<?> updateCurso(@PathVariable Long id,@RequestBody CursoRequest cursoRequest,Authentication authentication) {
        return cursoService.updateCurso(id, cursoRequest,authentication);
    }

    @DeleteMapping("/current/id/{id}")
    ResponseEntity<?> deleteCurso(@PathVariable Long id, Authentication authentication) {
        return cursoService.deleteCurso(id,authentication);
    }

    @PostMapping("/current/setCursoAlumno")
    ResponseEntity<?> setCursoAlumno(@RequestBody CursoAlumnoRequest request, Authentication authentication) {
        return cursoService.setCursoAlumno(request,authentication);
    }

}
