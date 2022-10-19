package com.saverioriotto.springbootresttutorial.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(	name = "utenti",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank
    @Size(max = 20)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    @Size(max = 120)
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "utente_ruoli",
            joinColumns = @JoinColumn(name = "id_utente"),
            inverseJoinColumns = @JoinColumn(name = "id_ruolo"))
    private Set<Ruolo> roles = new HashSet<>();
    public Utente() {
    }
    public Utente(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public Utente(String username, String email, String password, Set<Ruolo> ruoli) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = ruoli;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Set<Ruolo> getRoles() {
        return roles;
    }
    public void setRoles(Set<Ruolo> roles) {
        this.roles = roles;
    }
}