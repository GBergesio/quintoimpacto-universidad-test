package QuintoImpacto.testtecnico.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cursos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    private Boolean deleted;
    private Turno turno;

    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "curso_alumno",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "alumno_id")
    )
    private List<Alumno> alumnos;

}
// La relación @ManyToOne con Profesor indica que un curso pertenece a un único profesor y el profesor puede estar asociado a varios cursos.
// La relación @ManyToMany con Alumno indica que un curso puede tener varios alumnos asociados y un alumno puede estar inscrito en varios cursos.
// La relación se gestiona mediante una tabla de unión llamada curso_alumno, donde se almacenan las asociaciones entre los cursos y los alumnos.