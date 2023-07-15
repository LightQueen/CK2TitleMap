package com.example.accessingdatajpa.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@ToString(of = {"code","name"})
@Getter
@Setter
@EqualsAndHashCode
public class Dynasty {
    @Id
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "score")
    private int score;

    @OneToMany(mappedBy = "dnt", cascade = CascadeType.ALL)
    private List<Character> descendant = new ArrayList<>();

    public Dynasty() {}

    public Dynasty(String code,String name) {
        this.code = code;
        this.name = name;
    }



}
