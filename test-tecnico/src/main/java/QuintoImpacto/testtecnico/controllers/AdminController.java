package QuintoImpacto.testtecnico.controllers;

import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import QuintoImpacto.testtecnico.services.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administradores")
public class AdminController {

    @Autowired
    AdministradorService administradorService;

    @GetMapping("/current")
    ResponseEntity<?> getAllAdmins() {
        return administradorService.getAllAdmin();
    }

    @PostMapping("/current")
    ResponseEntity<?> newAdmin(@RequestBody UserRequest adminRequest) {
        return administradorService.createAdmin(adminRequest);
    }

    @PatchMapping("/current/id/{id}")
    ResponseEntity<?> updateAdmin(@PathVariable Long id,@RequestBody UserRequest adminRequest) {
        return administradorService.updateAdmin(id, adminRequest);
    }

    @DeleteMapping("/current/id/{id}")
    ResponseEntity<?> deleteAdmin(@PathVariable Long id) {
        return administradorService.deleteAdmin(id);
    }
}
