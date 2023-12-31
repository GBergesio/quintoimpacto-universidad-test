package QuintoImpacto.testtecnico.repositories;

import QuintoImpacto.testtecnico.models.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    public Alumno findByDni(String dni);
    public Optional<Alumno> findById(Long id);
    public List<Alumno> findAll();
}
