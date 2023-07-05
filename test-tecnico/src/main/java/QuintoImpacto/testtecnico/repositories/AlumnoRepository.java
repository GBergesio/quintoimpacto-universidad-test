package QuintoImpacto.testtecnico.repositories;

import QuintoImpacto.testtecnico.models.Alumno;
import QuintoImpacto.testtecnico.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    public Alumno findByDni(String dni);
    public Alumno findByEmail(String email);
    public Optional<Alumno> findById(Long id);
    public List<Alumno> findAll();
    @Query("SELECT a FROM Alumno a JOIN a.cursos c WHERE c.nombre LIKE %:letra%")
    List<Alumno> findByCursoNombreContainingLetra(@Param("letra") String letra);

    List<Alumno> findByCursos(Curso curso);

    List<Alumno> findByNombre(String nombre);

}
