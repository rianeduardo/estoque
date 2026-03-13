package dev.rianeduardo.estoque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.rianeduardo.estoque.models.*;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Materiais, Long> {
    List<Materiais> findByEstoqueLessThanEqual(Integer estoque);

    long countByEstoqueLessThanEqual(int estoque);
}