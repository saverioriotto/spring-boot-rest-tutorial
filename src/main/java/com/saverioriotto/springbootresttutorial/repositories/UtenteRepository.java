package com.saverioriotto.springbootresttutorial.repositories;

import com.saverioriotto.springbootresttutorial.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    @Query(value = "SELECT * FROM utenti WHERE username LIKE %?1%", nativeQuery = true)
    Optional<Utente> cercaPerUsername(String username);

    @Query(value = "SELECT * FROM utenti WHERE email LIKE %?1%", nativeQuery = true)
    Optional<Utente> cercaPerEmail(String email);

}