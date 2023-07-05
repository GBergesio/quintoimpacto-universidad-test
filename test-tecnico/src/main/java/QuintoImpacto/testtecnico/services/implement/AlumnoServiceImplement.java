package QuintoImpacto.testtecnico.services.implement;

import QuintoImpacto.testtecnico.dtos.combinacion.AlumnoCursoDTO;
import QuintoImpacto.testtecnico.models.Curso;
import QuintoImpacto.testtecnico.repositories.CursoRepository;
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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoServiceImplement implements AlumnoService {

    @Autowired
    AlumnoRepository alumnoRepository;

    @Autowired
    CursoRepository cursoRepository;

    @Override
    public ResponseEntity<?> getAllAlumnos() {
        List<Alumno> alumnos = alumnoRepository.findAll();
        List<AlumnoCursoDTO> alumnoDTOS = MapperUtil.convertToDtoList(alumnos, AlumnoCursoDTO.class);
        return ResponseUtils.dataResponse(alumnoDTOS, null);
    }

    @Override
    public ResponseEntity<?> createAlumno(UserRequest alumnoRequest) {
        List<Alumno> alumnos;

        alumnos = alumnoRepository.findAll();

        Boolean alumnosExist = alumnos.stream().anyMatch(a -> a.getDni().equals(alumnoRequest.getDni()));

        if (StringUtils.isBlank(alumnoRequest.getDni()))
            return ResponseUtils.badRequestResponse("Dni requerido");
        if (alumnosExist)
            return ResponseUtils.badRequestResponse("Dni en uso");
        if (StringUtils.isBlank(alumnoRequest.getNombre()))
            return ResponseUtils.badRequestResponse("Nombre requerido");
        if (StringUtils.isBlank(alumnoRequest.getApellido()))
            return ResponseUtils.badRequestResponse("Apellido requerido");
        if (StringUtils.isBlank(alumnoRequest.getCelular()))
            return ResponseUtils.badRequestResponse("Celular requerido");
        if (StringUtils.isBlank(alumnoRequest.getEmail()))
            return ResponseUtils.badRequestResponse("Email requerido");

        Alumno newAlumno = new Alumno();
        newAlumno.setDni(alumnoRequest.getDni());
        newAlumno.setNombre(alumnoRequest.getNombre());
        newAlumno.setApellido(alumnoRequest.getApellido());
        newAlumno.setCelular(alumnoRequest.getCelular());
        newAlumno.setEmail(alumnoRequest.getEmail());
        newAlumno.setPassword(alumnoRequest.getPassword()); //cambiar con el password encoder
        newAlumno.setDeleted(false);
        alumnoRepository.save(newAlumno);

        AlumnoDTO newAlumnoDTO = MapperUtil.convertToDto(newAlumno, AlumnoDTO.class);

        return ResponseUtils.createdResponse(newAlumnoDTO);
    }

    @Override
    public ResponseEntity<?> updateAlumno(Long id,UserRequest alumnoRequest) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno == null) {
            return ResponseUtils.badRequestResponse("Alumno no encontrado");
        }
        if(!alumno.getDni().equals(alumnoRequest.getDni())){
            Alumno alumnoDni = alumnoRepository.findByDni(alumnoRequest.getDni());
            if(alumnoDni != null){
                return ResponseUtils.badRequestResponse("DNI en uso");
            }
        }

        if (StringUtils.isBlank(alumnoRequest.getDni()))
            return ResponseUtils.badRequestResponse("Dni requerido");
        if (StringUtils.isBlank(alumnoRequest.getNombre()))
            return ResponseUtils.badRequestResponse("Nombre requerido");
        if (StringUtils.isBlank(alumnoRequest.getApellido()))
            return ResponseUtils.badRequestResponse("Apellido requerido");
        if (StringUtils.isBlank(alumnoRequest.getCelular()))
            return ResponseUtils.badRequestResponse("Celular requerido");
        if (StringUtils.isBlank(alumnoRequest.getEmail()))
            return ResponseUtils.badRequestResponse("Email requerido");

        alumno.setDni(alumnoRequest.getDni());
        alumno.setNombre(alumnoRequest.getNombre());
        alumno.setApellido(alumnoRequest.getApellido());
        alumno.setCelular(alumnoRequest.getCelular());
        alumno.setEmail(alumnoRequest.getEmail());
        alumno.setPassword(alumnoRequest.getPassword()); //cambiar con el password encoder
        alumnoRepository.save(alumno);

        AlumnoDTO newAlumnoDTO = MapperUtil.convertToDto(alumno, AlumnoDTO.class);
        return ResponseUtils.updatedResponse(newAlumnoDTO);
    }

    @Override
    public ResponseEntity<?> deleteAlumno(Long id) {
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
