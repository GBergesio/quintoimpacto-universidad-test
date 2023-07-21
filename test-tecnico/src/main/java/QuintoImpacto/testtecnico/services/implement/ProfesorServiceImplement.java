package QuintoImpacto.testtecnico.services.implement;

import QuintoImpacto.testtecnico.dtos.combinacion.ProfesorCursosDTO;
import QuintoImpacto.testtecnico.dtos.request.CursoProfesorRequest;
import QuintoImpacto.testtecnico.models.Curso;
import QuintoImpacto.testtecnico.repositories.CursoRepository;
import QuintoImpacto.testtecnico.services.ValidationUserUtils;
import QuintoImpacto.testtecnico.utils.MapperUtil;
import QuintoImpacto.testtecnico.dtos.ProfesorDTO;
import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import QuintoImpacto.testtecnico.models.Profesor;
import QuintoImpacto.testtecnico.repositories.ProfesorRepository;
import QuintoImpacto.testtecnico.services.ProfesorService;
import QuintoImpacto.testtecnico.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProfesorServiceImplement implements ProfesorService {

    @Autowired
    ProfesorRepository profesorRepository;
    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    private final ValidationUserUtils validationUserUtils;

    @Autowired
    public ProfesorServiceImplement(ValidationUserUtils validationUserUtils) {
        this.validationUserUtils = validationUserUtils;
    }

    // Metodo que devuelve todos los profesores - se puede hacer otro para que devuelva todos y este modificarlo para que devuelva solo los deleted == false
    @Override
    public ResponseEntity<?> getAllProfesores() {
        List<Profesor> profesores = profesorRepository.findAll(); // Obtén la lista de profesores desde alguna fuente de datos
        List<ProfesorCursosDTO> profesorDTOS = MapperUtil.convertToDtoList(profesores, ProfesorCursosDTO.class);
        return ResponseUtils.dataResponse(profesorDTOS, null);
    }
    // Metodo para crear un profesor, utiliza el request generico para los 3 tipos de usuarios (ya que en este caso los 3 comparten los mismos atributos)
    // y el segundo parametro de autenticacion es para restringir que solo los administradores puedan crear a traves del booleano isAdminActive.
    @Transactional
    @Override
    public ResponseEntity<?> createProfesor(UserRequest userRequest,Authentication authentication) {
        Boolean isAdminActive = validationUserUtils.admin(authentication);
        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }
        ResponseEntity<?> validationResult = validationUserUtils.validateUserRequest(userRequest, false);
        if (validationResult != null) {
            return validationResult; // Hubo un error de validación
        }

        Profesor newProfesor = new Profesor();
        newProfesor.setDni(userRequest.getDni());
        newProfesor.setNombre(userRequest.getNombre());
        newProfesor.setApellido(userRequest.getApellido());
        newProfesor.setCelular(userRequest.getCelular());
        newProfesor.setEmail(userRequest.getEmail());
        newProfesor.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newProfesor.setDeleted(userRequest.getDeleted());
        newProfesor.setTipo("profesor");
        profesorRepository.save(newProfesor);

        ProfesorDTO profesorDTO = MapperUtil.convertToDto(newProfesor, ProfesorDTO.class);
        return ResponseUtils.createdResponse(profesorDTO);
    }
    //Metodo para modificar un admin, tiene en cuenta mismas consideraciones que el metodo de crear
    @Transactional
    @Override
    public ResponseEntity<?> updateProfesor(Long id, UserRequest userRequest,Authentication authentication) {
        Boolean isAdminActive = validationUserUtils.admin(authentication);
        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }
        Profesor profesor = profesorRepository.findById(id).orElse(null);
        if (profesor == null) {
            return ResponseUtils.badRequestResponse("Profesor no encontrado");
        }

       ResponseEntity<?> validationResult = validationUserUtils.validateUserRequest(userRequest, true);
        if (validationResult != null) {
            return validationResult; // Hubo un error de validación
        }

        if(!userRequest.getDni().equals(profesor.getDni())){
            Boolean isDniExists = validationUserUtils.dniExist(userRequest);
            if (isDniExists) {
                return ResponseUtils.badRequestResponse("DNI en uso");
            }
        }
        if(!userRequest.getEmail().equals(profesor.getEmail())){
            Boolean isEmailExists = validationUserUtils.emailExist(userRequest);
            if (isEmailExists) {
                return ResponseUtils.badRequestResponse("Email en uso");
            }
        }

        profesor.setDni(userRequest.getDni());
        profesor.setNombre(userRequest.getNombre());
        profesor.setApellido(userRequest.getApellido());
        profesor.setCelular(userRequest.getCelular());
        profesor.setEmail(userRequest.getEmail());
        profesor.setPassword(userRequest.getPassword());
        profesor.setDeleted(userRequest.getDeleted());
        profesorRepository.save(profesor);
        ProfesorDTO profesorDTO = MapperUtil.convertToDto(profesor, ProfesorDTO.class);
        return ResponseUtils.updatedResponse(profesorDTO);
    }
    // Borrado logico del profesor, solo se desactiva.
    // Si se quisiera eliminar completamente deberiamos chequear que el profesor no este relacionado con un curso primero.
    // Se podria hacer un condicional viendo el tamanio de profesor.getCursos y si es mayor a 0 eliminar la relacion entre los cursos y el profesor y luego si eliminar el profesor de la bdd
    @Override
    public ResponseEntity<?> deleteProfesor(Long id,Authentication authentication) {
        Boolean isAdminActive = validationUserUtils.admin(authentication);
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
    // Metodo para eliminar el profesor de un curso, recibe el id del curso como parametro, se busca en el repo, si existe se setea en null
    @Transactional
    @Override
    public ResponseEntity<?> releaseCurso(Long idCurso,Authentication authentication) {
        Boolean isAdminActive = validationUserUtils.admin(authentication);
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

    // Metodo para setear un profesor a un curso. Buscamos en el repositorio tanto al profe como al curso, si existen se setea y se genera la relacion.
    @Transactional
    @Override
    public ResponseEntity<?> setCursoProfesor(CursoProfesorRequest cursoProfesorRequest, Authentication authentication) {
        Boolean isAdminActive = validationUserUtils.admin(authentication);
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
