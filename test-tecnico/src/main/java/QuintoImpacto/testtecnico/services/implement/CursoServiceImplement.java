package QuintoImpacto.testtecnico.services.implement;

import QuintoImpacto.testtecnico.dtos.CursoDTO;
import QuintoImpacto.testtecnico.dtos.combinacion.CursoProfesorAlumnosDTO;
import QuintoImpacto.testtecnico.dtos.request.CursoAlumnoRequest;
import QuintoImpacto.testtecnico.dtos.request.CursoRequest;
import QuintoImpacto.testtecnico.models.*;
import QuintoImpacto.testtecnico.repositories.AdministradorRepository;
import QuintoImpacto.testtecnico.repositories.AlumnoRepository;
import QuintoImpacto.testtecnico.repositories.CursoRepository;
import QuintoImpacto.testtecnico.repositories.ProfesorRepository;
import QuintoImpacto.testtecnico.services.CursoService;
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
public class CursoServiceImplement implements CursoService {

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    AlumnoRepository alumnoRepository;
    @Autowired
    AdministradorRepository administradorRepository;
    @Autowired
    ProfesorRepository profesorRepository;

    @Override
    public Boolean isAdmin(Authentication authentication) {
        Object loggedUser = UserUtils.getLoggedUser(authentication, administradorRepository, profesorRepository, alumnoRepository);
        if (loggedUser instanceof Administrador) {
            return true;
        }
        return false;
    }

    @Override
    public ResponseEntity<?> getAllCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        List<CursoProfesorAlumnosDTO> cursosDTOS = MapperUtil.convertToDtoList(cursos, CursoProfesorAlumnosDTO.class);
        return ResponseUtils.dataResponse(cursosDTOS, null);
    }

    @Override
    public ResponseEntity<?> createCurso(CursoRequest cursoRequest,Authentication authentication) {
        Boolean isAdminActive = isAdmin(authentication);

        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }

        List<Curso> cursos = cursoRepository.findAll();
        Boolean cursoExist = cursos.stream().anyMatch(c -> c.getNombre().equals(cursoRequest.getNombre()));

        if (StringUtils.isBlank(cursoRequest.getNombre())) {
            return ResponseUtils.badRequestResponse("Nombre del curso requerido");
        }
        if (cursoExist) {
            return ResponseUtils.badRequestResponse("Nombre del curso en uso");
        }
        Curso newCurso = new Curso();
        newCurso.setNombre(cursoRequest.getNombre());
        newCurso.setDeleted(false);
        newCurso.setTurno(cursoRequest.getTurno());
        if(cursoRequest.getIdProfesor() != null){
            Profesor profesor = profesorRepository.findById(cursoRequest.getIdProfesor()).orElse(null);
            if(profesor == null){
                return ResponseUtils.badRequestResponse("No se encontró el profesor");
            }
            newCurso.setProfesor(profesor);
        }
        cursoRepository.save(newCurso);

        CursoDTO newCursoDTO = MapperUtil.convertToDto(newCurso, CursoDTO.class);

        return ResponseUtils.createdResponse(newCursoDTO);
    }

    @Override
    public ResponseEntity<?> updateCurso(Long id, CursoRequest cursoRequest,Authentication authentication) {
        Boolean isAdminActive = isAdmin(authentication);

        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }
        Curso curso = cursoRepository.findById(id).orElse(null);
        if(curso == null){
            return ResponseUtils.badRequestResponse("No se encontró el curso");
        }
        List<Curso> cursos = cursoRepository.findAll();
        Boolean cursoExist = cursos.stream().anyMatch(c -> c.getNombre().equals(cursoRequest.getNombre()));

        if (StringUtils.isBlank(cursoRequest.getNombre())) {
            return ResponseUtils.badRequestResponse("Nombre del curso requerido");
        }
        if(!curso.getNombre().equals(cursoRequest.getNombre())){
            if (cursoExist) {
                return ResponseUtils.badRequestResponse("Nombre del curso en uso");
            }
        }
        if(cursoRequest.getIdProfesor() != null){
            Profesor profesor = profesorRepository.findById(cursoRequest.getIdProfesor()).orElse(null);
            if(profesor == null){
                return ResponseUtils.badRequestResponse("No se encontró el profesor");
            }
            curso.setProfesor(profesor);
        }
        curso.setNombre(cursoRequest.getNombre());
        curso.setTurno(cursoRequest.getTurno());

        cursoRepository.save(curso);

        CursoProfesorAlumnosDTO cursoDTO = MapperUtil.convertToDto(curso, CursoProfesorAlumnosDTO.class);

        return ResponseUtils.updatedResponse(cursoDTO);
    }

    @Override
    public ResponseEntity<?> deleteCurso(Long id,Authentication authentication) {
        Boolean isAdminActive = isAdmin(authentication);
        if (!isAdminActive){
            return ResponseUtils.forbiddenResponse();
        }
        Curso curso = cursoRepository.findById(id).orElse(null);
        if(curso == null){
            return ResponseUtils.badRequestResponse("No se encontró el curso");
        }
        curso.setDeleted(!curso.getDeleted());
        cursoRepository.save(curso);

        return ResponseUtils.activeDesactiveResponse(!curso.getDeleted());
    }
        @Override
    public ResponseEntity<?> setCursoAlumno(CursoAlumnoRequest request, Authentication authentication) {
//        Boolean isAdminActive = isAdmin(authentication);
//        if (!isAdminActive){
//            return ResponseUtils.forbiddenResponse();
//        }
        Curso curso = cursoRepository.findById(request.getIdCurso()).orElse(null);
        if(curso == null){
            return ResponseUtils.badRequestResponse("No se encontró el curso");
        }
        Alumno alumno = alumnoRepository.findById(request.getIdAlumno()).orElse(null);
        if(alumno == null){
            return ResponseUtils.badRequestResponse("No se encontró el Alumno");
        }
        List<Alumno> alumnos = curso.getAlumnos();
        if (!request.getTipo().equals("remove") && !request.getTipo().equals("set")) {
            return ResponseUtils.badRequestResponse("Elige el tipo de acción válido");
        }

        if (request.getTipo().equals("remove")) {
            if (!alumnos.contains(alumno)) {
                return ResponseUtils.badRequestResponse("El alumno no existe en el curso");
            }

            alumnos.remove(alumno);
        } else if (request.getTipo().equals("set")) {
            if (alumnos.contains(alumno)) {
                return ResponseUtils.badRequestResponse("El alumno ya existe en el curso");
            }

            alumnos.add(alumno);
        } else {
            return ResponseUtils.badRequestResponse("Tipo de acción no válido");
        }
        curso.setAlumnos(alumnos);
        cursoRepository.save(curso);

        CursoProfesorAlumnosDTO cursoDTO = MapperUtil.convertToDto(curso, CursoProfesorAlumnosDTO.class);

        return ResponseUtils.updatedResponse(cursoDTO);
    }


}
