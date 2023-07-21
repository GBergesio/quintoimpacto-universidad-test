package QuintoImpacto.testtecnico.controllers.login;

import QuintoImpacto.testtecnico.services.AdministradorService;
import QuintoImpacto.testtecnico.services.JwtUserDetailsService;
import QuintoImpacto.testtecnico.services.ValidationUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.transaction.Transactional;

@CrossOrigin()
@RestController
@RequestMapping("/currentUser")
public class CurrentAuth {

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private ValidationUserUtils validationUserUtils;

    @GetMapping()
    ResponseEntity<?> loggedUser(Authentication authentication) {
        return validationUserUtils.loggedUser(authentication);
    }

    @Transactional
    @GetMapping("/credentials")
    ResponseEntity<?> currentUser(Authentication authentication) {

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());

        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

}
