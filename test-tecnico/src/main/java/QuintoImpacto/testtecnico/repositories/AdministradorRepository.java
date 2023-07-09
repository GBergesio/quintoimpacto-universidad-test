package QuintoImpacto.testtecnico.repositories;

import QuintoImpacto.testtecnico.models.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    public Administrador findByDni(String dni);
    public Administrador findByEmail(String email);
    public Optional<Administrador> findById(Long id);
    public List<Administrador> findAll();

    boolean existsByEmail(String email);

    boolean existsByDni(String dni);
}
