package QuintoImpacto.testtecnico.dtos;

import QuintoImpacto.testtecnico.Enum.Turno;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CursoDTO {
    private Long id;
    private String nombre;
    private Turno turno;
    private Boolean deleted;
}
