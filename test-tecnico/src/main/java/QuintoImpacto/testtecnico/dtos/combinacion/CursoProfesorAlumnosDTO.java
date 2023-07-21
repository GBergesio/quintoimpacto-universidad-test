package QuintoImpacto.testtecnico.dtos.combinacion;

import QuintoImpacto.testtecnico.dtos.AlumnoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class CursoProfesorAlumnosDTO {
    private CursoProfesorDTO curso;
    private List<AlumnoDTO> alumnos;
}
