package com.saverioriotto.springbootresttutorial.entities;

import com.saverioriotto.springbootresttutorial.models.RuoloEnum;

import javax.persistence.*;

@Entity
@Table(name = "ruoli")
public class Ruolo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RuoloEnum name;
    public Ruolo() {
    }
    public Ruolo(RuoloEnum name) {
        this.name = name;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public RuoloEnum getName() {
        return name;
    }
    public void setName(RuoloEnum name) {
        this.name = name;
    }
}
