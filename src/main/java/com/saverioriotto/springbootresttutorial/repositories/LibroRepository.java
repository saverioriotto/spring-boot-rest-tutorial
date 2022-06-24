package com.saverioriotto.springbootresttutorial.repositories;

import com.saverioriotto.springbootresttutorial.entities.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {}