package QuintoImpacto.testtecnico.services.implement;

import QuintoImpacto.testtecnico.dtos.AlumnoDTO;
import QuintoImpacto.testtecnico.dtos.combinacion.ProfesorCursosDTO;
import QuintoImpacto.testtecnico.dtos.request.CursoProfesorRequest;
import QuintoImpacto.testtecnico.models.Administrador;
import QuintoImpacto.testtecnico.models.Curso;
import QuintoImpacto.testtecnico.repositories.AdministradorRepository;
import QuintoImpacto.testtecnico.repositories.AlumnoRepository;
import QuintoImpacto.testtecnico.repositories.CursoRepository;
import QuintoImpacto.testtecnico.utils.MapperUtil;
import QuintoImpacto.testtecnico.dtos.ProfesorDTO;
import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import QuintoImpacto.testtecnico.models.Profesor;
import QuintoImpacto.testtecnico.repositories.ProfesorRepository;
import QuintoImpacto.testtecnico.services.ProfesorService;
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
public class ProfesorServiceImplement implements ProfesorService {

    @Autowired
    ProfesorRepository profesorRepository;
    @Autowired
    AdministradorRepository administradorRepository;
    @Autowired
    AlumnoRepository alumnoRepository;
    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Boolean loggedUser(Authentication authentication) {
        Object loggedUser = UserUtils.getLoggedUser(authentication, administradorRepository, profesorRepository, alumnoRepository);
        if (loggedUser instanceof Administrador) {
            return true;
        }
        return false;
    }

    @Override
    public ResponseEntity<?> getAllProfesores() {
        List<Profesor> profesores = profesorRepository.findAll(); // Obtén la lista de profesores desde alguna fuente de datos
        List<ProfesorCursosDTO> profesorDTOS = MapperUtil.convertToDtoList(profesores, ProfesorCursosDTO.class);
        return ResponseUtils.dataResponse(profesorDTOS, null);
    }

    @Override
    public ResponseEntity<?> createProfesor(UserRequest profesorRequest,Authentication authentication) {
        Boolean isAdminActive = loggedUser(authentication);
        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }

        List<Profesor> profesores;

        profesores = profesorRepository.findAll();

        Boolean profesorExist = profesores.stream().anyMatch(p -> p.getDni().equals(profesorRequest.getDni()));

        if (StringUtils.isBlank(profesorRequest.getDni()))
            return ResponseUtils.badRequestResponse("Dni requerido");
        if (profesorExist)
            return ResponseUtils.badRequestResponse("Dni en uso");
        if (StringUtils.isBlank(profesorRequest.getNombre()))
            return ResponseUtils.badRequestResponse("Nombre requerido");
        if (StringUtils.isBlank(profesorRequest.getApellido()))
            return ResponseUtils.badRequestResponse("Apellido requerido");
        if (StringUtils.isBlank(profesorRequest.getCelular()))
            return ResponseUtils.badRequestResponse("Celular requerido");
        if (StringUtils.isBlank(profesorRequest.getEmail()))
            return ResponseUtils.badRequestResponse("Email requerido");
        if (StringUtils.isBlank(profesorRequest.getPassword()))
            return ResponseUtils.badRequestResponse("Password requerido");

        Profesor newProfesor = new Profesor();
        newProfesor.setDni(profesorRequest.getDni());
        newProfesor.setNombre(profesorRequest.getNombre());
        newProfesor.setApellido(profesorRequest.getApellido());
        newProfesor.setCelular(profesorRequest.getCelular());
        newProfesor.setEmail(profesorRequest.getEmail());
        newProfesor.setPassword(passwordEncoder.encode(profesorRequest.getPassword()));
        newProfesor.setDeleted(profesorRequest.getDeleted());
        newProfesor.setTipo("profesor");
        profesorRepository.save(newProfesor);

        ProfesorDTO profesorDTO = MapperUtil.convertToDto(newProfesor, ProfesorDTO.class);
        return ResponseUtils.createdResponse(profesorDTO);
    }

    @Override
    public ResponseEntity<?> updateProfesor(Long id, UserRequest profesorRequest,Authentication authentication) {
        Boolean isAdminActive = loggedUser(authentication);
        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }

        Profesor profesor = profesorRepository.findById(id).orElse(null);
        if (profesor == null) {
            return ResponseUtils.badRequestResponse("Profesor no encontrado");
        }
        if(!profesor.getDni().equals(profesorRequest.getDni())){
            Profesor profesorDni = profesorRepository.findByDni(profesorRequest.getDni());
            if(profesorDni != null){
                return ResponseUtils.badRequestResponse("DNI en uso");
            }
        }
        if (StringUtils.isBlank(profesorRequest.getDni()))
            return ResponseUtils.badRequestResponse("Dni requerido");
        if (StringUtils.isBlank(profesorRequest.getNombre()))
            return ResponseUtils.badRequestResponse("Nombre requerido");
        if (StringUtils.isBlank(profesorRequest.getApellido()))
            return ResponseUtils.badRequestResponse("Apellido requerido");
        if (StringUtils.isBlank(profesorRequest.getCelular()))
            return ResponseUtils.badRequestResponse("Celular requerido");
        if (StringUtils.isBlank(profesorRequest.getEmail()))
            return ResponseUtils.badRequestResponse("Email requerido");

        profesor.setDni(profesorRequest.getDni());
        profesor.setNombre(profesorRequest.getNombre());
        profesor.setApellido(profesorRequest.getApellido());
        profesor.setCelular(profesorRequest.getCelular());
        profesor.setEmail(profesorRequest.getEmail());
        profesor.setPassword(profesorRequest.getPassword());
        profesor.setDeleted(profesorRequest.getDeleted());
        profesorRepository.save(profesor);
        ProfesorDTO profesorDTO = MapperUtil.convertToDto(profesor, ProfesorDTO.class);
        return ResponseUtils.updatedResponse(profesorDTO);
    }

    @Override
    public ResponseEntity<?> deleteProfesor(Long id,Authentication authentication) {
        Boolean isAdminActive = loggedUser(authentication);
        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }
        Profesor profesor = profesorRepository.findById(id).orElse(null);
        if (profesor == null) {
            return ResponseUtils.badRequestResponse("Profesor no encontrado");
        }
        profesor.setDeleted(!profesor.getDeleted());
        profesorRepository.save(profesor);

        return ResponseUtils.activeDesactiveResponse(!profesor.getDeleted());
    }

    @Override
    public ResponseEntity<?> releaseCurso(Long idCurso,Authentication authentication) {
        Boolean isAdminActive = loggedUser(authentication);
        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }
        Curso curso = cursoRepository.findById(idCurso).orElse(null);
        if (curso == null) {
            return ResponseUtils.badRequestResponse("Curso no encontrado");
        }

        if (curso.getProfesor() != null) {
            curso.setProfesor(null);
            cursoRepository.save(curso);
        }

        return ResponseUtils.updatedResponse(null);
    }

    @Override
    public ResponseEntity<?> setCursoProfesor(CursoProfesorRequest cursoProfesorRequest, Authentication authentication) {
        Boolean isAdminActive = loggedUser(authentication);
        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }
        Curso curso = cursoRepository.findById(cursoProfesorRequest.getIdCurso()).orElse(null);
        Profesor profesor = profesorRepository.findById(cursoProfesorRequest.getIdProfesor()).orElse(null);
        if (curso == null) {
            return ResponseUtils.badRequestResponse("Curso  no encontrado");
        }
        if (profesor == null) {
            return ResponseUtils.badRequestResponse("Profesor no encontrado");
        }

        if (curso.getProfesor() != null){
            return ResponseUtils.badRequestResponse("El Curso ya posee un profesor");
        }

        curso.setProfesor(profesor);
        cursoRepository.save(curso);

        return ResponseUtils.updatedResponse(null);
    }
}
