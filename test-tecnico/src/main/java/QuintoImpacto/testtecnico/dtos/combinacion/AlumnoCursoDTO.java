package QuintoImpacto.testtecnico.dtos.combinacion;

import QuintoImpacto.testtecnico.dtos.AlumnoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class AlumnoCursoDTO {
    private AlumnoDTO alumno;
    private List<CursoProfesorDTO> cursos;
    private String test;
}
