package QuintoImpacto.testtecnico.controllers;

import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import QuintoImpacto.testtecnico.services.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    AlumnoService alumnoService;

    @GetMapping("/current")
    ResponseEntity<?> getAllAlumnos() {
        return alumnoService.getAllAlumnos();
    }

    @PostMapping("/current")
    ResponseEntity<?> newAlumno(@RequestBody UserRequest alumnoRequest, Authentication authentication) {
        return alumnoService.createAlumno(alumnoRequest,authentication);
    }

    @PatchMapping("/current/id/{id}")
    ResponseEntity<?> updateAlumno(@PathVariable Long id,@RequestBody UserRequest alumnoRequest,Authentication authentication) {
        return alumnoService.updateAlumno(id, alumnoRequest,authentication);
    }

    @DeleteMapping("/current/id/{id}")
    ResponseEntity<?> deleteAlumno(@PathVariable Long id, Authentication authentication) {
        return alumnoService.deleteAlumno(id,authentication);
    }

    @GetMapping("/current/letra")
    ResponseEntity<?> getAlumnosByLetra(@RequestParam String n) {
        return alumnoService.findAlumnosByLetraCurso(n);
    }

    @GetMapping("/current/curso/{id}")
    ResponseEntity<?> getAlumnosByCurso(@PathVariable Long id) {
        return alumnoService.findByCurso(id);
    }

    @GetMapping("/current/nombre/{nombre}")
    ResponseEntity<?> getAlumnosByCurso(@PathVariable String nombre) {
        return alumnoService.findByNombre(nombre);
    }
}