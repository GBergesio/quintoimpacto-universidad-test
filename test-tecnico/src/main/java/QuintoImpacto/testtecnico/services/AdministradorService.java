package QuintoImpacto.testtecnico.services;

import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import org.springframework.http.ResponseEntity;

public interface AdministradorService {

    ResponseEntity<?> getAllAdmin();
    ResponseEntity<?> createAdmin(UserRequest adminRequest);
    ResponseEntity<?> updateAdmin(Long id, UserRequest adminRequest);
    ResponseEntity<?> deleteAdmin(Long id);
}
