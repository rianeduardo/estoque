package dev.rianeduardo.estoque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.rianeduardo.estoque.models.*;

@Repository
public interface AtivoPatrimonialRepository extends JpaRepository<Ativos, Long> {
}