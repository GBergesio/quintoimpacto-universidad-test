package QuintoImpacto.testtecnico.services.implement;

import QuintoImpacto.testtecnico.dtos.AdministradorDTO;
import QuintoImpacto.testtecnico.dtos.LoggedUserDTO;
import QuintoImpacto.testtecnico.dtos.combinacion.AdmiDTO;
import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import QuintoImpacto.testtecnico.models.Administrador;
import QuintoImpacto.testtecnico.repositories.AdministradorRepository;
import QuintoImpacto.testtecnico.repositories.AlumnoRepository;
import QuintoImpacto.testtecnico.repositories.ProfesorRepository;
import QuintoImpacto.testtecnico.services.AdministradorService;
import QuintoImpacto.testtecnico.services.ValidationUserUtils;
import QuintoImpacto.testtecnico.utils.MapperUtil;
import QuintoImpacto.testtecnico.utils.ResponseUtils;
import QuintoImpacto.testtecnico.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AdministradorServiceImplement implements AdministradorService {
    @Autowired
    AdministradorRepository administradorRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    private final ValidationUserUtils validationUserUtils;

    @Autowired
    public AdministradorServiceImplement(ValidationUserUtils validationUserUtils) {
        this.validationUserUtils = validationUserUtils;
    }

    // Metodo que devuelve todos los administradores - se puede hacer otro para que devuelva todos y este modificarlo para que devuelva solo los deleted == false
    @Override
    public ResponseEntity<?> getAllAdmin(Authentication authentication) {
        Boolean isAdminActive = validationUserUtils.admin(authentication);
        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }

        List<Administrador> administradores = administradorRepository.findAll();
        List<AdmiDTO> administradorDTOS = MapperUtil.convertToDtoList(administradores, AdmiDTO.class);
        return ResponseUtils.dataResponse(administradorDTOS, null);
    }
    // Metodo para crear un admin, utiliza el request generico para los 3 tipos de usuarios (ya que en este caso los 3 comparten los mismos atributos)
    // y el segundo parametro de autenticacion es para restringir que solo los administradores puedan crear a traves del booleano isAdminActive.
    @Transactional
    @Override
    public ResponseEntity<?> createAdmin(UserRequest userRequest,Authentication authentication) {
        Boolean isAdminActive = validationUserUtils.admin(authentication);
        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }

        ResponseEntity<?> validationResult = validationUserUtils.validateUserRequest(userRequest, false);
        if (validationResult != null) {
            return validationResult; // Hubo un error de validación
        }

        Administrador newAdmin = new Administrador();
        newAdmin.setDni(userRequest.getDni());
        newAdmin.setNombre(userRequest.getNombre());
        newAdmin.setApellido(userRequest.getApellido());
        newAdmin.setCelular(userRequest.getCelular());
        newAdmin.setEmail(userRequest.getEmail());
        newAdmin.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newAdmin.setDeleted(userRequest.getDeleted());
        newAdmin.setTipo("administrador");
        administradorRepository.save(newAdmin);

        AdministradorDTO administradorDTO = MapperUtil.convertToDto(newAdmin, AdministradorDTO.class);

        return ResponseUtils.createdResponse(administradorDTO);
    }
    //Metodo para modificar un admin, tiene en cuenta mismas consideraciones que el metodo de crear
    @Transactional
    @Override
    public ResponseEntity<?> updateAdmin(Long id, UserRequest userRequest,Authentication authentication) {
        Boolean isAdminActive = validationUserUtils.admin(authentication);
        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }

        Administrador administrador = administradorRepository.findById(id).orElse(null);
        if (administrador == null) {
            return ResponseUtils.badRequestResponse("Administrador no encontrado");
        }

        if(!userRequest.getDni().equals(administrador.getDni())){
            Boolean isDniExists = validationUserUtils.dniExist(userRequest);
            if (isDniExists) {
                return ResponseUtils.badRequestResponse("DNI en uso");
            }
        }
        if(!userRequest.getEmail().equals(administrador.getEmail())){
            Boolean isEmailExists = validationUserUtils.emailExist(userRequest);
            if (isEmailExists) {
                return ResponseUtils.badRequestResponse("Email en uso");
            }
        }

        administrador.setDni(userRequest.getDni());
        administrador.setNombre(userRequest.getNombre());
        administrador.setApellido(userRequest.getApellido());
        administrador.setCelular(userRequest.getCelular());
        administrador.setEmail(userRequest.getEmail());
        administrador.setDeleted(userRequest.getDeleted());

        administradorRepository.save(administrador);

        AdministradorDTO administradorDTO = MapperUtil.convertToDto(administrador, AdministradorDTO.class);
        return ResponseUtils.updatedResponse(administradorDTO);
    }
    // Borrado logico del administrador, solo se desactiva. Para eliminar un admin del repositorio podriamos hacer un .delete()
    // y no habria mayores riesgos ya que no tiene relacion con otras entidades.
    @Override
    public ResponseEntity<?> deleteAdmin(Long id, Authentication authentication) {
        Boolean isAdminActive = validationUserUtils.admin(authentication);
        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }
        Administrador administrador = administradorRepository.findById(id).orElse(null);
        if (administrador == null) {
            return ResponseUtils.badRequestResponse("Administrador no encontrado");
        }
        administrador.setDeleted(!administrador.getDeleted());
        administradorRepository.save(administrador);

        return ResponseUtils.activeDesactiveResponse(!administrador.getDeleted());
    }

}
