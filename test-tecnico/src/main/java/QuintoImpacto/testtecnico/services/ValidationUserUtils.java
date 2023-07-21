package QuintoImpacto.testtecnico.services;

import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface ValidationUserUtils {
    ResponseEntity<?> validateUserRequest(UserRequest userRequest, Boolean update);
    Boolean dniExist(UserRequest userRequest);
    Boolean emailExist(UserRequest userRequest);
    Boolean admin(Authentication authentication);
    ResponseEntity<?> loggedUser(Authentication authentication);
}
