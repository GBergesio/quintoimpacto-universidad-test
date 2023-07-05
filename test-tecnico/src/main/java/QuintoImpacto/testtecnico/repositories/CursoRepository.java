package QuintoImpacto.testtecnico.repositories;

import QuintoImpacto.testtecnico.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface CursoRepository extends JpaRepository<Curso, Long> {

    public Curso findByNombre(String nombre);
    public Optional<Curso> findById(Long id);
    public List<Curso> findAll();

}
