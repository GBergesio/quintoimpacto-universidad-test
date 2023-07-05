package QuintoImpacto.testtecnico.controllers;

import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import QuintoImpacto.testtecnico.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<?> newProfesor(@RequestBody UserRequest profesorRequest) {
        return profesorService.createProfesor(profesorRequest);
    }

    @PatchMapping("/current/id/{id}")
    ResponseEntity<?> updateProfesor(@PathVariable Long id,@RequestBody UserRequest profesorRequest) {
        return profesorService.updateProfesor(id, profesorRequest);
    }

    @DeleteMapping("/current/id/{id}")
    ResponseEntity<?> deleteProfesor(@PathVariable Long id) {
        return profesorService.deleteProfesor(id);
    }

    @PostMapping("/current/release")
    ResponseEntity<?> newProfesor(@RequestParam Long idCurso) {
        return profesorService.releaseCurso(idCurso);
    }

    @PostMapping("/current/setCursoProfesor")
    ResponseEntity<?> setCursoProfesor(@RequestParam Long idCurso,@RequestParam Long idProfesor) {
        return profesorService.setCursoProfesor(idCurso,idProfesor);
    }
}
