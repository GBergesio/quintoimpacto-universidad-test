package QuintoImpacto.testtecnico.services.implement;

import QuintoImpacto.testtecnico.dtos.LoggedUserDTO;
import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import QuintoImpacto.testtecnico.models.Administrador;
import QuintoImpacto.testtecnico.repositories.AdministradorRepository;
import QuintoImpacto.testtecnico.repositories.AlumnoRepository;
import QuintoImpacto.testtecnico.repositories.ProfesorRepository;
import QuintoImpacto.testtecnico.services.ValidationUserUtils;
import QuintoImpacto.testtecnico.utils.MapperUtil;
import QuintoImpacto.testtecnico.utils.ResponseUtils;
import QuintoImpacto.testtecnico.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ValidationUserUtilsImpl implements ValidationUserUtils {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Override
    public  ResponseEntity<?> validateUserRequest(UserRequest userRequest, Boolean update){
            if (StringUtils.isBlank(userRequest.getDni())) {
                return ResponseUtils.badRequestResponse("Dni requerido");
            }

        if (!update) {
            Boolean isDniExists = dniExist(userRequest);
            if (isDniExists) {
                return ResponseUtils.badRequestResponse("DNI en uso");
            }

            Boolean isEmailExists = emailExist(userRequest);
            if (isEmailExists) {
                return ResponseUtils.badRequestResponse("Email en uso");
            }
        }
        if (StringUtils.isBlank(userRequest.getNombre())) {
            return ResponseUtils.badRequestResponse("Nombre requerido");
        }

        if (StringUtils.isBlank(userRequest.getApellido())) {
            return ResponseUtils.badRequestResponse("Apellido requerido");
        }

        if (StringUtils.isBlank(userRequest.getCelular())) {
            return ResponseUtils.badRequestResponse("Celular requerido");
        }

        if (StringUtils.isBlank(userRequest.getEmail())) {
            return ResponseUtils.badRequestResponse("Email requerido");
        }

        if (StringUtils.isBlank(userRequest.getPassword())) {
            return ResponseUtils.badRequestResponse("Password requerido");
        }

        return null; // Indica que todas las validaciones fueron exitosas
    }
    @Override
    public  Boolean dniExist(UserRequest userRequest) {
        if (alumnoRepository.existsByDni(userRequest.getDni())
                || profesorRepository.existsByDni(userRequest.getDni())
                || administradorRepository.existsByDni(userRequest.getDni())) {
            return true;
        }
        return false;
    }
    @Override
    public  Boolean emailExist(UserRequest userRequest) {
        if (alumnoRepository.existsByEmail(userRequest.getEmail())
                || profesorRepository.existsByEmail(userRequest.getEmail())
                || administradorRepository.existsByEmail(userRequest.getEmail())) {
            return true;
        }
        return false;
    }
    @Override
    public Boolean admin(Authentication authentication) {
        Object loggedUser = UserUtils.getLoggedUser(authentication, administradorRepository, profesorRepository, alumnoRepository);
        if (loggedUser instanceof Administrador) {
            return true;
        }
        return false;
    }
    //Devuelve el usuario logueado (Tanto Alumno, Profesor o Administrador)
    @Override
    public ResponseEntity<?> loggedUser(Authentication authentication) {
        Object loggedUser = UserUtils.getLoggedUser(authentication, administradorRepository, profesorRepository, alumnoRepository);
        if(loggedUser == null){
            return ResponseUtils.forbiddenResponse();
        } else{
            LoggedUserDTO loggedUserr = MapperUtil.convertToDto(loggedUser, LoggedUserDTO.class);
            return ResponseUtils.dataResponse(loggedUserr,null);
        }
    }

}
