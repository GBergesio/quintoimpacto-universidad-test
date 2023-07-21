package QuintoImpacto.testtecnico.services.implement;

import QuintoImpacto.testtecnico.dtos.combinacion.AlumnoCursoDTO;
import QuintoImpacto.testtecnico.models.Curso;
import QuintoImpacto.testtecnico.repositories.CursoRepository;
import QuintoImpacto.testtecnico.services.ValidationUserUtils;
import QuintoImpacto.testtecnico.utils.MapperUtil;
import QuintoImpacto.testtecnico.dtos.AlumnoDTO;
import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import QuintoImpacto.testtecnico.models.Alumno;
import QuintoImpacto.testtecnico.repositories.AlumnoRepository;
import QuintoImpacto.testtecnico.services.AlumnoService;
import QuintoImpacto.testtecnico.utils.ResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AlumnoServiceImplement implements AlumnoService {

    @Autowired
    AlumnoRepository alumnoRepository;
    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    private final ValidationUserUtils validationUserUtils;

    @Autowired
    public AlumnoServiceImplement(ValidationUserUtils validationUserUtils) {
        this.validationUserUtils = validationUserUtils;
    }

    // Metodo que devuelve todos los alumnos - se puede hacer otro para que devuelva todos y este modificarlo para que devuelva solo los deleted == false
    @Override
    public ResponseEntity<?> getAllAlumnos() {
        List<Alumno> alumnos = alumnoRepository.findAll();
        List<AlumnoCursoDTO> alumnoDTOS = MapperUtil.convertToDtoList(alumnos, AlumnoCursoDTO.class);
        return ResponseUtils.dataResponse(alumnoDTOS, null);
    }

    // Metodo para crear un alumno, utiliza el request generico para los 3 tipos de usuarios (ya que en este caso los 3 comparten los mismos atributos)
    // En este caso se puede crear estando logueado como no. administradores pueden crear y persona que se registre como alumno.
    @Transactional
    @Override
    public ResponseEntity<?> createAlumno(UserRequest userRequest,Authentication authentication) {

        ResponseEntity<?> validationResult = validationUserUtils.validateUserRequest(userRequest, false);
        if (validationResult != null) {
            return validationResult; //
        }
        Alumno newAlumno = new Alumno();
        newAlumno.setDni(userRequest.getDni());
        newAlumno.setNombre(userRequest.getNombre());
        newAlumno.setApellido(userRequest.getApellido());
        newAlumno.setCelular(userRequest.getCelular());
        newAlumno.setEmail(userRequest.getEmail());
        newAlumno.setDeleted(userRequest.getDeleted());
        newAlumno.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newAlumno.setDeleted(userRequest.getDeleted());
        newAlumno.setTipo("alumno");
        alumnoRepository.save(newAlumno);

        AlumnoDTO newAlumnoDTO = MapperUtil.convertToDto(newAlumno, AlumnoDTO.class);

        return ResponseUtils.createdResponse(newAlumnoDTO);
    }
    //Metodo para modificar un profesor, tiene en cuenta mismas consideraciones que el metodo de crear, solo pueden hacer update los admin
    @Transactional
    @Override
    public ResponseEntity<?> updateAlumno(Long id,UserRequest userRequest,Authentication authentication) {
        Boolean isAdminActive = validationUserUtils.admin(authentication);
        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }

        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno == null) {
            return ResponseUtils.badRequestResponse("Alumno no encontrado");
        }

        ResponseEntity<?> validationResult = validationUserUtils.validateUserRequest(userRequest, true);
        if (validationResult != null) {
            return validationResult; // Hubo un error de validación
        }

        if(!userRequest.getDni().equals(alumno.getDni())){
            Boolean isDniExists = validationUserUtils.dniExist(userRequest);
            if (isDniExists) {
                return ResponseUtils.badRequestResponse("DNI en uso");
            }
        }
        if(!userRequest.getEmail().equals(alumno.getEmail())){
            Boolean isEmailExists = validationUserUtils.emailExist(userRequest);
            if (isEmailExists) {
                return ResponseUtils.badRequestResponse("Email en uso");
            }
        }

        alumno.setDni(userRequest.getDni());
        alumno.setNombre(userRequest.getNombre());
        alumno.setApellido(userRequest.getApellido());
        alumno.setCelular(userRequest.getCelular());
        alumno.setEmail(userRequest.getEmail());
        alumno.setDeleted(userRequest.getDeleted());
        alumnoRepository.save(alumno);

        AlumnoDTO newAlumnoDTO = MapperUtil.convertToDto(alumno, AlumnoDTO.class);
        return ResponseUtils.updatedResponse(newAlumnoDTO);
    }
    // Borrado logico del alumno, solo se desactiva. Para eliminar un admin del repositorio podriamos hacer un .delete() pero deberiamos
    // ver las relaciones que existan entre ese alumno y cursos. Se deberian eliminar primero y posterior si eliminarlo completamente
    @Override
    public ResponseEntity<?> deleteAlumno(Long id,Authentication authentication) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno == null) {
            return ResponseUtils.badRequestResponse("Alumno no encontrado");
        }
        alumno.setDeleted(!alumno.getDeleted());
        alumnoRepository.save(alumno);

        return ResponseUtils.activeDesactiveResponse(!alumno.getDeleted());
    }
    // Metodo para encontrar alumnos segun una letra o palabra del curso. Por ej Biologia
    @Override
    public ResponseEntity<?> findAlumnosByLetraCurso(String letra) {
        List<Alumno> alumnos = null;
        alumnos = alumnoRepository.findByCursoNombreContainingLetra(letra);
        List<AlumnoDTO> alumnoDTOS = MapperUtil.convertToDtoList(alumnos, AlumnoDTO.class);
        return ResponseUtils.dataResponse(alumnoDTOS, null);
    }
    // Metodo para traer alumnos segun id de curso.
    @Override
    public ResponseEntity<?> findByCurso(Long id) {
        List<Alumno> alumnos = null;
        Curso curso = cursoRepository.findById(id).orElse(null);

        if (curso == null) {
            return ResponseUtils.badRequestResponse("Curso no encontrado");
        }

        alumnos = alumnoRepository.findByCursos(curso);

        List<AlumnoDTO> alumnoDTOS = MapperUtil.convertToDtoList(alumnos, AlumnoDTO.class);
        return ResponseUtils.dataResponse(alumnoDTOS, null);
    }
    // Metodo para traer alumnos segun nombre del alumno.
    @Override
    public ResponseEntity<?> findByNombre(String nombre) {
        List<Alumno> alumnos = null;

        if (StringUtils.isBlank(nombre)) {
            return ResponseUtils.badRequestResponse("Ingrese un nombre");
        }
        alumnos = alumnoRepository.findByNombre(nombre);

        List<AlumnoDTO> alumnoDTOS = MapperUtil.convertToDtoList(alumnos, AlumnoDTO.class);
        return ResponseUtils.dataResponse(alumnoDTOS, null);
    }


}
