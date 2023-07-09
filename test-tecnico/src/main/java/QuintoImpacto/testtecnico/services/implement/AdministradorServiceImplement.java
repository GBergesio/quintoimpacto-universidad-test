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
import QuintoImpacto.testtecnico.utils.MapperUtil;
import QuintoImpacto.testtecnico.utils.ResponseUtils;
import QuintoImpacto.testtecnico.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Boolean isAdmin(Authentication authentication) {
        Object loggedUser = UserUtils.getLoggedUser(authentication, administradorRepository, profesorRepository, alumnoRepository);
        if (loggedUser instanceof Administrador) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean emailExist(UserRequest alumnoRequest) {
        if (alumnoRepository.existsByEmail(alumnoRequest.getEmail())
                || profesorRepository.existsByEmail(alumnoRequest.getEmail())
                || administradorRepository.existsByEmail(alumnoRequest.getEmail())){
            return true;
        }
        return false;
    }

    @Override
    public Boolean dniExist(UserRequest alumnoRequest) {
        if (alumnoRepository.existsByDni(alumnoRequest.getDni())
                || profesorRepository.existsByDni(alumnoRequest.getDni())
                || administradorRepository.existsByDni(alumnoRequest.getDni())){
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
        List<AdmiDTO> administradorDTOS = MapperUtil.convertToDtoList(administradores, AdmiDTO.class);
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

        if (StringUtils.isBlank(adminRequest.getDni())) {
            return ResponseUtils.badRequestResponse("Dni requerido");
        }
        Boolean isDniExists = dniExist(adminRequest);
        if (isDniExists) {
            return ResponseUtils.badRequestResponse("DNI en uso");
        }
        if (StringUtils.isBlank(adminRequest.getNombre())) {
            return ResponseUtils.badRequestResponse("Nombre requerido");
        }
        if (StringUtils.isBlank(adminRequest.getApellido())) {
            return ResponseUtils.badRequestResponse("Apellido requerido");
        }
        if (StringUtils.isBlank(adminRequest.getCelular())) {
            return ResponseUtils.badRequestResponse("Celular requerido");
        }
        if (StringUtils.isBlank(adminRequest.getEmail())) {
            return ResponseUtils.badRequestResponse("Email requerido");
        }
        Boolean isEmailExists = emailExist(adminRequest);
        if (isEmailExists) {
            return ResponseUtils.badRequestResponse("Email en uso");
        }
        if (StringUtils.isBlank(adminRequest.getPassword())) {
            return ResponseUtils.badRequestResponse("Password requerido");
        }

        Administrador newAdmin = new Administrador();
        newAdmin.setDni(adminRequest.getDni());
        newAdmin.setNombre(adminRequest.getNombre());
        newAdmin.setApellido(adminRequest.getApellido());
        newAdmin.setCelular(adminRequest.getCelular());
        newAdmin.setEmail(adminRequest.getEmail());
        newAdmin.setPassword(passwordEncoder.encode(adminRequest.getPassword()));
        newAdmin.setDeleted(adminRequest.getDeleted());
        newAdmin.setTipo("administrador");
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
        if (StringUtils.isBlank(adminRequest.getDni())){
            return ResponseUtils.badRequestResponse("Dni requerido");
        }
        Boolean isDniExists = dniExist(adminRequest);

        if(!administrador.getDni().equals(adminRequest.getDni())){
            if (isDniExists) {
                return ResponseUtils.badRequestResponse("DNI en uso");
            }
        }
        if (StringUtils.isBlank(adminRequest.getNombre())) {
            return ResponseUtils.badRequestResponse("Nombre requerido");
        }
        if (StringUtils.isBlank(adminRequest.getApellido())) {
            return ResponseUtils.badRequestResponse("Apellido requerido");
        }
        if (StringUtils.isBlank(adminRequest.getCelular())) {
            return ResponseUtils.badRequestResponse("Celular requerido");
        }
        if (StringUtils.isBlank(adminRequest.getEmail())) {
            return ResponseUtils.badRequestResponse("Email requerido");
        }
        Boolean isEmailExists = emailExist(adminRequest);
        if(!administrador.getEmail().equals(adminRequest.getEmail())){
            if (isEmailExists) {
                return ResponseUtils.badRequestResponse("Email en uso");
            }
        }

        administrador.setDni(adminRequest.getDni());
        administrador.setNombre(adminRequest.getNombre());
        administrador.setApellido(adminRequest.getApellido());
        administrador.setCelular(adminRequest.getCelular());
        administrador.setEmail(adminRequest.getEmail());
        administrador.setDeleted(adminRequest.getDeleted());

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
