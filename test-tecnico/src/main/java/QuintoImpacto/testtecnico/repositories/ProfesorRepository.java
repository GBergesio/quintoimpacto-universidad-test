package QuintoImpacto.testtecnico.repositories;

import QuintoImpacto.testtecnico.models.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    public Profesor findByDni(String dni);
    public Profesor findByEmail(String email);
    public Optional<Profesor> findById(Long id);
    public List<Profesor> findAll();
    boolean existsByEmail(String email);

    boolean existsByDni(String dni);
}
