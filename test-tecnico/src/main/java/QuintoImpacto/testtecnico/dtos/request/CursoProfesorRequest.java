package QuintoImpacto.testtecnico.dtos.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CursoProfesorRequest {
    Long idCurso;
    Long idProfesor;
}
