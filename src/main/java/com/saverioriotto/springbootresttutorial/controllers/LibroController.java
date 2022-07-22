package com.saverioriotto.springbootresttutorial.controllers;

import com.saverioriotto.springbootresttutorial.entities.Libro;
import com.saverioriotto.springbootresttutorial.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libro")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @GetMapping
    @PreAuthorize("hasAuthority('UTENTE') or hasAuthority('MODERATORE') or hasAuthority('ADMIN')")
    public List<Libro> findAllBooks() {
        return libroRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('UTENTE') or hasAuthority('MODERATORE') or hasAuthority('ADMIN')")
    public ResponseEntity<Libro> findBookById(@PathVariable(value = "id") long id) {
        Optional<Libro> libro = libroRepository.findById(id);

        if(libro.isPresent()) {
            return ResponseEntity.ok().body(libro.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('MODERATORE') or hasAuthority('ADMIN')")
    public Libro saveBook(@Validated @RequestBody Libro libro) {
        return libroRepository.save(libro);
    }

}
