package QuintoImpacto.testtecnico.controllers.login;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    @Transactional
    @PostMapping()
    ResponseEntity<?> logout(){
        return new ResponseEntity<>("Logout", HttpStatus.OK);
    }
}
