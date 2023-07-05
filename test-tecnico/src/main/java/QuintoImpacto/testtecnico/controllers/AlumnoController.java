package QuintoImpacto.testtecnico.controllers;

import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import QuintoImpacto.testtecnico.services.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<?> newAlumno(@RequestBody UserRequest alumnoRequest) {
        return alumnoService.createAlumno(alumnoRequest);
    }

    @PatchMapping("/current/id/{id}")
    ResponseEntity<?> updateAlumno(@PathVariable Long id,@RequestBody UserRequest alumnoRequest) {
        return alumnoService.updateAlumno(id, alumnoRequest);
    }

    @DeleteMapping("/current/id/{id}")
    ResponseEntity<?> deleteAlumno(@PathVariable Long id) {
        return alumnoService.deleteAlumno(id);
    }
}