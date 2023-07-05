package QuintoImpacto.testtecnico.services.implement;

import QuintoImpacto.testtecnico.dtos.CursoDTO;
import QuintoImpacto.testtecnico.dtos.combinacion.CursoProfesorAlumnosDTO;
import QuintoImpacto.testtecnico.dtos.request.CursoRequest;
import QuintoImpacto.testtecnico.models.Alumno;
import QuintoImpacto.testtecnico.models.Curso;
import QuintoImpacto.testtecnico.repositories.CursoRepository;
import QuintoImpacto.testtecnico.services.CursoService;
import QuintoImpacto.testtecnico.utils.MapperUtil;
import QuintoImpacto.testtecnico.utils.ResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoServiceImplement implements CursoService {

    @Autowired
    CursoRepository cursoRepository;

    @Override
    public ResponseEntity<?> getAllCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        List<CursoProfesorAlumnosDTO> cursosDTOS = MapperUtil.convertToDtoList(cursos, CursoProfesorAlumnosDTO.class);
        return ResponseUtils.dataResponse(cursosDTOS, null);
    }

    @Override
    public ResponseEntity<?> createCurso(CursoRequest cursoRequest) {
        List<Curso> cursos;

        cursos = cursoRepository.findAll();

        Boolean cursoExist = cursos.stream().anyMatch(c -> c.getNombre().equals(cursoRequest.getNombre()));

        if (StringUtils.isBlank(cursoRequest.getNombre()))
            return ResponseUtils.badRequestResponse("Nombre del curso requerido");
        if (cursoExist)
            return ResponseUtils.badRequestResponse("Nombre del curso en uso");
        Curso newCurso = new Curso();
        newCurso.setNombre(cursoRequest.getNombre());
        newCurso.setDeleted(false);

        cursoRepository.save(newCurso);

        CursoDTO newCursoDTO = MapperUtil.convertToDto(newCurso, CursoDTO.class);

        return ResponseUtils.createdResponse(newCursoDTO);
    }

    @Override
    public ResponseEntity<?> updateCurso(Long id, CursoRequest cursoRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteCurso(Long id) {
        return null;
    }


}
