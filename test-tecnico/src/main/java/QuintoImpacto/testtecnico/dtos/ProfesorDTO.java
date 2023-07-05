package QuintoImpacto.testtecnico.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfesorDTO {
    private long id;

    private String dni;
    private String nombre;
    private String apellido;
    private String celular;
    private String email;
    private String password;
    private Boolean deleted;
}
