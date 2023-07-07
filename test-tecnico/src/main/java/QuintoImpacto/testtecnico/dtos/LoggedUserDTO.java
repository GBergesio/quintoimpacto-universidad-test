package QuintoImpacto.testtecnico.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoggedUserDTO {
    private long id;
    private String dni;
    private String nombre;
    private String apellido;
    private String celular;
    private String email;
    private String tipo;
}
