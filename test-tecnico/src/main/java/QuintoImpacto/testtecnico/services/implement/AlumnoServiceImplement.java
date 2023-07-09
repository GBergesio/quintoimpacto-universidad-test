package QuintoImpacto.testtecnico.services.implement;

import QuintoImpacto.testtecnico.dtos.combinacion.AlumnoCursoDTO;
import QuintoImpacto.testtecnico.dtos.request.CursoAlumnoRequest;
import QuintoImpacto.testtecnico.models.Administrador;
import QuintoImpacto.testtecnico.models.Curso;
import QuintoImpacto.testtecnico.repositories.AdministradorRepository;
import QuintoImpacto.testtecnico.repositories.CursoRepository;
import QuintoImpacto.testtecnico.repositories.ProfesorRepository;
import QuintoImpacto.testtecnico.utils.MapperUtil;
import QuintoImpacto.testtecnico.dtos.AlumnoDTO;
import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import QuintoImpacto.testtecnico.models.Alumno;
import QuintoImpacto.testtecnico.repositories.AlumnoRepository;
import QuintoImpacto.testtecnico.services.AlumnoService;
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
public class AlumnoServiceImplement implements AlumnoService {

    @Autowired
    AlumnoRepository alumnoRepository;
    @Autowired
    AdministradorRepository administradorRepository;
    @Autowired
    ProfesorRepository profesorRepository;
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
    public ResponseEntity<?> getAllAlumnos() {
        List<Alumno> alumnos = alumnoRepository.findAll();
        List<AlumnoCursoDTO> alumnoDTOS = MapperUtil.convertToDtoList(alumnos, AlumnoCursoDTO.class);
        return ResponseUtils.dataResponse(alumnoDTOS, null);
    }

    @Override
    public ResponseEntity<?> createAlumno(UserRequest alumnoRequest,Authentication authentication) {

        List<Alumno> alumnos = alumnoRepository.findAll();

        if (StringUtils.isBlank(alumnoRequest.getDni())) {
            return ResponseUtils.badRequestResponse("Dni requerido");
        }
        Boolean isDniExists = dniExist(alumnoRequest);
        if (isDniExists) {
            return ResponseUtils.badRequestResponse("DNI en uso");
        }
        if (StringUtils.isBlank(alumnoRequest.getNombre())) {
            return ResponseUtils.badRequestResponse("Nombre requerido");
        }
        if (StringUtils.isBlank(alumnoRequest.getApellido())) {
            return ResponseUtils.badRequestResponse("Apellido requerido");
        }
        if (StringUtils.isBlank(alumnoRequest.getCelular())) {
            return ResponseUtils.badRequestResponse("Celular requerido");
        }
        if (StringUtils.isBlank(alumnoRequest.getEmail())) {
            return ResponseUtils.badRequestResponse("Email requerido");
        }
        Boolean isEmailExists = emailExist(alumnoRequest);
        if (isEmailExists) {
            return ResponseUtils.badRequestResponse("Email en uso");
        }
        if (StringUtils.isBlank(alumnoRequest.getPassword())) {
            return ResponseUtils.badRequestResponse("Password requerido");
        }
        Alumno newAlumno = new Alumno();
        newAlumno.setDni(alumnoRequest.getDni());
        newAlumno.setNombre(alumnoRequest.getNombre());
        newAlumno.setApellido(alumnoRequest.getApellido());
        newAlumno.setCelular(alumnoRequest.getCelular());
        newAlumno.setEmail(alumnoRequest.getEmail());
        newAlumno.setDeleted(alumnoRequest.getDeleted());
        newAlumno.setPassword(passwordEncoder.encode(alumnoRequest.getPassword()));
        newAlumno.setDeleted(alumnoRequest.getDeleted());
        newAlumno.setTipo("alumno");
        alumnoRepository.save(newAlumno);

        AlumnoDTO newAlumnoDTO = MapperUtil.convertToDto(newAlumno, AlumnoDTO.class);

        return ResponseUtils.createdResponse(newAlumnoDTO);
    }

    @Override
    public ResponseEntity<?> updateAlumno(Long id,UserRequest alumnoRequest,Authentication authentication) {
        Boolean isAdminActive = loggedUser(authentication);
        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno == null) {
            return ResponseUtils.badRequestResponse("Alumno no encontrado");
        }
        if (StringUtils.isBlank(alumnoRequest.getDni())) {
            return ResponseUtils.badRequestResponse("Dni requerido");
        }
        Boolean isDniExists = dniExist(alumnoRequest);

        if(!alumno.getDni().equals(alumnoRequest.getDni())){
            if (isDniExists) {
                return ResponseUtils.badRequestResponse("DNI en uso");
            }
        }
        if (StringUtils.isBlank(alumnoRequest.getNombre())) {
            return ResponseUtils.badRequestResponse("Nombre requerido");
        }
        if (StringUtils.isBlank(alumnoRequest.getApellido())) {
            return ResponseUtils.badRequestResponse("Apellido requerido");
        }
        if (StringUtils.isBlank(alumnoRequest.getCelular())) {
            return ResponseUtils.badRequestResponse("Celular requerido");
        }
        if (StringUtils.isBlank(alumnoRequest.getEmail())) {
            return ResponseUtils.badRequestResponse("Email requerido");
        }
        Boolean isEmailExists = emailExist(alumnoRequest);
        if(!alumno.getEmail().equals(alumnoRequest.getEmail())){
            if (isEmailExists) {
                return ResponseUtils.badRequestResponse("Email en uso");
            }
        }

        alumno.setDni(alumnoRequest.getDni());
        alumno.setNombre(alumnoRequest.getNombre());
        alumno.setApellido(alumnoRequest.getApellido());
        alumno.setCelular(alumnoRequest.getCelular());
        alumno.setEmail(alumnoRequest.getEmail());
        alumno.setDeleted(alumnoRequest.getDeleted());
        alumnoRepository.save(alumno);

        AlumnoDTO newAlumnoDTO = MapperUtil.convertToDto(alumno, AlumnoDTO.class);
        return ResponseUtils.updatedResponse(newAlumnoDTO);
    }

    @Override
    public ResponseEntity<?> deleteAlumno(Long id,Authentication authentication) {
        Boolean isAdminActive = loggedUser(authentication);
        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno == null) {
            return ResponseUtils.badRequestResponse("Alumno no encontrado");
        }
        alumno.setDeleted(!alumno.getDeleted());
        alumnoRepository.save(alumno);

        return ResponseUtils.activeDesactiveResponse(!alumno.getDeleted());
    }

    @Override
    public ResponseEntity<?> findAlumnosByLetraCurso(String letra) {
        List<Alumno> alumnos = null;
        alumnos = alumnoRepository.findByCursoNombreContainingLetra(letra);
        List<AlumnoDTO> alumnoDTOS = MapperUtil.convertToDtoList(alumnos, AlumnoDTO.class);
        return ResponseUtils.dataResponse(alumnoDTOS, null);
    }

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
