package QuintoImpacto.testtecnico.controllers;

import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import QuintoImpacto.testtecnico.services.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administradores")
public class AdminController {

    @Autowired
    AdministradorService administradorService;

    @GetMapping("/current")
    ResponseEntity<?> getAllAdmins(Authentication authentication) {
        return administradorService.getAllAdmin(authentication);
    }
    @PostMapping("/current")
    ResponseEntity<?> newAdmin(@RequestBody UserRequest adminRequest,Authentication authentication) {
        return administradorService.createAdmin(adminRequest,authentication);
    }
    @PatchMapping("/current/id/{id}")
    ResponseEntity<?> updateAdmin(@PathVariable Long id,@RequestBody UserRequest adminRequest,Authentication authentication) {
        return administradorService.updateAdmin(id, adminRequest,authentication);
    }
    @DeleteMapping("/current/id/{id}")
    ResponseEntity<?> deleteAdmin(@PathVariable Long id,Authentication authentication) {
        return administradorService.deleteAdmin(id,authentication);
    }

}
