package QuintoImpacto.testtecnico.dtos.combinacion;

import QuintoImpacto.testtecnico.dtos.CursoDTO;
import QuintoImpacto.testtecnico.dtos.ProfesorDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class ProfesorCursosDTO{
    private ProfesorDTO profesor;
    private List<CursoDTO> cursos;
}
