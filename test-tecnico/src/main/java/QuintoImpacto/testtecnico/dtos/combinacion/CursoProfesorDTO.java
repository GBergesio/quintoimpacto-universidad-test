package QuintoImpacto.testtecnico.dtos.combinacion;

import QuintoImpacto.testtecnico.dtos.ProfesorDTO;
import QuintoImpacto.testtecnico.models.Turno;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CursoProfesorDTO {
    private Long id;
    private String nombre;
    private Turno turno;
    private Boolean deleted;
    private ProfesorDTO profesor;
}
