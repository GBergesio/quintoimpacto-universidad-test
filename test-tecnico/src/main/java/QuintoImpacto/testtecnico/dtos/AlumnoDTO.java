package QuintoImpacto.testtecnico.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AlumnoDTO {
    private long id;

    private String dni;
    private String nombre;
    private String apellido;
    private String celular;
    private String email;
    private Boolean deleted;
//    private List<CursoDTO> cursos;
}
