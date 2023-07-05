package QuintoImpacto.testtecnico.controllers;

import QuintoImpacto.testtecnico.dtos.request.CursoRequest;
import QuintoImpacto.testtecnico.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<?> newCurso(@RequestBody CursoRequest cursoRequest) {
        return cursoService.createCurso(cursoRequest);
    }

    @PatchMapping("/current/id/{id}")
    ResponseEntity<?> updateCurso(@PathVariable Long id,@RequestBody CursoRequest cursoRequest) {
        return cursoService.updateCurso(id, cursoRequest);
    }

    @DeleteMapping("/current/id/{id}")
    ResponseEntity<?> deleteCurso(@PathVariable Long id) {
        return cursoService.deleteCurso(id);
    }
}
