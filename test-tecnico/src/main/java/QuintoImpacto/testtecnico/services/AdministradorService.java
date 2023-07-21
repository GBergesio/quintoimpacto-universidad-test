package QuintoImpacto.testtecnico.services;

import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface AdministradorService {

    ResponseEntity<?> getAllAdmin(Authentication authentication);
    ResponseEntity<?> createAdmin(UserRequest adminRequest,Authentication authentication);
    ResponseEntity<?> updateAdmin(Long id, UserRequest adminRequest,Authentication authentication);
    ResponseEntity<?> deleteAdmin(Long id,Authentication authentication);


}