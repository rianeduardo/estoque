package dev.rianeduardo.estoque.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import dev.rianeduardo.estoque.models.*;

public interface FuncionarioAutenticadoRepository extends JpaRepository<FuncionarioAutenticado, Long> {
    Optional<FuncionarioAutenticado> findByNifAndAtivoTrue(String nif);

    boolean existsByNifAndNomeAndAtivoTrue(String nif, String nome);
}