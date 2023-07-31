package com.example.accessingdatajpa.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private List<Ch> descendants = new ArrayList<>();

    public void addDescendant(Ch ch){
        ch.setDnt(this);
        descendants.add(ch);
    }

    public Dynasty() {}

    public Dynasty(String code,String name) {
        this.code = code;
        this.name = name;
    }



}
