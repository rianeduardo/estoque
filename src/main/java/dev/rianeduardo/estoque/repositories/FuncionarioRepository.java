package dev.rianeduardo.estoque.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import dev.rianeduardo.estoque.models.*;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByNif(String nif);

    boolean existsByNif(String nif);
}
