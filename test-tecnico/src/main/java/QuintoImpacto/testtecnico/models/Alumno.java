package QuintoImpacto.testtecnico.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "alumnos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alumno {

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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "alumnos")
    private List<Curso> cursos;

}
