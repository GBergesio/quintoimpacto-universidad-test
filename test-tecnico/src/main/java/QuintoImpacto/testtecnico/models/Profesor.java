package QuintoImpacto.testtecnico.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "profesores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String dni;
    private String nombre;
    private String apellido;
    private String celular;
    private String email;
    private String password;
    private Boolean deleted;

    @Column(name = "tipo_usuario", columnDefinition = "VARCHAR(255) DEFAULT 'profesor'")
    private String tipo;

    @OneToMany(mappedBy = "profesor")
    private List<Curso> cursos;

}
