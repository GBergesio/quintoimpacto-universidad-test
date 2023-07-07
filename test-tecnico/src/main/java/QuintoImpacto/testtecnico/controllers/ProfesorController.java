package QuintoImpacto.testtecnico.controllers;

import QuintoImpacto.testtecnico.dtos.request.CursoProfesorRequest;
import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import QuintoImpacto.testtecnico.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {

    @Autowired
    ProfesorService profesorService;

    @GetMapping("/current")
    ResponseEntity<?> getAllProfesores() {
        return profesorService.getAllProfesores();
    }

    @PostMapping("/current")
    ResponseEntity<?> newProfesor(@RequestBody UserRequest profesorRequest,Authentication authentication) {
        return profesorService.createProfesor(profesorRequest,authentication);
    }

    @PatchMapping("/current/id/{id}")
    ResponseEntity<?> updateProfesor(@PathVariable Long id,@RequestBody UserRequest profesorRequest,Authentication authentication) {
        return profesorService.updateProfesor(id, profesorRequest,authentication);
    }

    @DeleteMapping("/current/id/{id}")
    ResponseEntity<?> deleteProfesor(@PathVariable Long id,Authentication authentication) {
        return profesorService.deleteProfesor(id,authentication);
    }

    @DeleteMapping("/current/release/{idCurso}")
    ResponseEntity<?> newProfesor(@PathVariable Long idCurso, Authentication authentication) {
        return profesorService.releaseCurso(idCurso,authentication);
    }

    @PostMapping("/current/setCursoProfesor")
    ResponseEntity<?> setCursoProfesor(@RequestBody CursoProfesorRequest cursoProfesorRequest, Authentication authentication) {
        return profesorService.setCursoProfesor(cursoProfesorRequest,authentication);
    }
}
