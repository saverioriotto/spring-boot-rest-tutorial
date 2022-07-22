package com.saverioriotto.springbootresttutorial.repositories;

import com.saverioriotto.springbootresttutorial.entities.Ruolo;
import com.saverioriotto.springbootresttutorial.models.RuoloEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
    @Query(value = "SELECT * FROM ruoli WHERE name LIKE %?1%", nativeQuery = true)
    Optional<Ruolo> cercaPerNome(RuoloEnum name);


    @Query(value = "SELECT * FROM ruoli WHERE name LIKE %?1%", nativeQuery = true)
    List<Ruolo> cercaPerNomeList(RuoloEnum name);
}