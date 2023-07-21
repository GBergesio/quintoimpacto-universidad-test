package QuintoImpacto.testtecnico.dtos.request;

import QuintoImpacto.testtecnico.Enum.Turno;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CursoRequest {
    private String nombre;
    private Turno turno;
    private Long idProfesor;
    private Boolean deleted;
}
