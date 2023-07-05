package QuintoImpacto.testtecnico.services.implement;

import QuintoImpacto.testtecnico.dtos.AdministradorDTO;
import QuintoImpacto.testtecnico.dtos.LoggedUserDTO;
import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import QuintoImpacto.testtecnico.models.Administrador;
import QuintoImpacto.testtecnico.repositories.AdministradorRepository;
import QuintoImpacto.testtecnico.repositories.AlumnoRepository;
import QuintoImpacto.testtecnico.repositories.ProfesorRepository;
import QuintoImpacto.testtecnico.services.AdministradorService;
import QuintoImpacto.testtecnico.utils.MapperUtil;
import QuintoImpacto.testtecnico.utils.ResponseUtils;
import QuintoImpacto.testtecnico.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorServiceImplement implements AdministradorService {
    @Autowired
    AdministradorRepository administradorRepository;
    @Autowired
    ProfesorRepository profesorRepository;
    @Autowired
    AlumnoRepository alumnoRepository;

    @Override
    public Boolean isAdmin(Authentication authentication) {
        Object loggedUser = UserUtils.getLoggedUser(authentication, administradorRepository, profesorRepository, alumnoRepository);
        if (loggedUser instanceof Administrador) {
            return true;
        }
        return false;
    }

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

    @Override
    public ResponseEntity<?> getAllAdmin(Authentication authentication) {
        Boolean isAdminActive = isAdmin(authentication);

        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }

        List<Administrador> administradores = administradorRepository.findAll();
        List<AdministradorDTO> administradorDTOS = MapperUtil.convertToDtoList(administradores, AdministradorDTO.class);
        return ResponseUtils.dataResponse(administradorDTOS, null);
    }

    @Override
    public ResponseEntity<?> createAdmin(UserRequest adminRequest,Authentication authentication) {
        Boolean isAdminActive = isAdmin(authentication);

        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }
        List<Administrador> administradores;

        administradores = administradorRepository.findAll();

        Boolean adminExist = administradores.stream().anyMatch(a -> a.getDni().equals(adminRequest.getDni()));

        if (StringUtils.isBlank(adminRequest.getDni()))
            return ResponseUtils.badRequestResponse("Dni requerido");
        if (adminExist)
            return ResponseUtils.badRequestResponse("Dni en uso");
        if (StringUtils.isBlank(adminRequest.getNombre()))
            return ResponseUtils.badRequestResponse("Nombre requerido");
        if (StringUtils.isBlank(adminRequest.getApellido()))
            return ResponseUtils.badRequestResponse("Apellido requerido");
        if (StringUtils.isBlank(adminRequest.getCelular()))
            return ResponseUtils.badRequestResponse("Celular requerido");
        if (StringUtils.isBlank(adminRequest.getEmail()))
            return ResponseUtils.badRequestResponse("Email requerido");

        Administrador newAdmin = new Administrador();
        newAdmin.setDni(adminRequest.getDni());
        newAdmin.setNombre(adminRequest.getNombre());
        newAdmin.setApellido(adminRequest.getApellido());
        newAdmin.setCelular(adminRequest.getCelular());
        newAdmin.setEmail(adminRequest.getEmail());
        newAdmin.setPassword(adminRequest.getPassword()); //cambiar con el password encoder
        newAdmin.setDeleted(false);
        administradorRepository.save(newAdmin);

        AdministradorDTO administradorDTO = MapperUtil.convertToDto(newAdmin, AdministradorDTO.class);

        return ResponseUtils.createdResponse(administradorDTO);
    }

    @Override
    public ResponseEntity<?> updateAdmin(Long id, UserRequest adminRequest,Authentication authentication) {
        Boolean isAdminActive = isAdmin(authentication);

        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }
        Administrador administrador = administradorRepository.findById(id).orElse(null);
        if (administrador == null) {
            return ResponseUtils.badRequestResponse("Administrador no encontrado");
        }
        if(!administrador.getDni().equals(adminRequest.getDni())){
            Administrador admDNI = administradorRepository.findByDni(adminRequest.getDni());
            if(admDNI != null){
                return ResponseUtils.badRequestResponse("DNI en uso");
            }
        }

        if (StringUtils.isBlank(adminRequest.getDni()))
            return ResponseUtils.badRequestResponse("Dni requerido");
        if (StringUtils.isBlank(adminRequest.getNombre()))
            return ResponseUtils.badRequestResponse("Nombre requerido");
        if (StringUtils.isBlank(adminRequest.getApellido()))
            return ResponseUtils.badRequestResponse("Apellido requerido");
        if (StringUtils.isBlank(adminRequest.getCelular()))
            return ResponseUtils.badRequestResponse("Celular requerido");
        if (StringUtils.isBlank(adminRequest.getEmail()))
            return ResponseUtils.badRequestResponse("Email requerido");

        administrador.setDni(adminRequest.getDni());
        administrador.setNombre(adminRequest.getNombre());
        administrador.setApellido(adminRequest.getApellido());
        administrador.setCelular(adminRequest.getCelular());
        administrador.setEmail(adminRequest.getEmail());
        administrador.setPassword(adminRequest.getPassword()); //cambiar con el password encoder
        administradorRepository.save(administrador);

        AdministradorDTO administradorDTO = MapperUtil.convertToDto(administrador, AdministradorDTO.class);
        return ResponseUtils.updatedResponse(administradorDTO);
    }

    @Override
    public ResponseEntity<?> deleteAdmin(Long id, Authentication authentication) {
        Boolean isAdminActive = isAdmin(authentication);

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
