package com.example.accessingdatajpa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

@Entity
@ToString(of = {"code","name","fem","bd","dd","score"})
@Table(name="ch")
@Getter
@Setter
@EqualsAndHashCode
public class Character {


    @Id
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "fem")
    private Boolean fem;

    @Column(name = "b_d")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyy.M.d")
    private Date bd;

    @Column(name = "d_d")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyy.M.d")
    private Date dd;

    @Column(name = "level")
    private TitleLevel level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dnt_code")
    private Dynasty dnt;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Character fat;

    @OneToMany(mappedBy = "fat",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @OrderBy("bd")
    private List<Character> fatChild = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.ALL})
    private Character mot;

    @OneToMany(mappedBy = "mot",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @OrderBy("bd")
    private List<Character> motChild = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "host_code")
    private Character host;

    @OneToMany(mappedBy = "host",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Character> guests = new ArrayList<>();

    // @Column(name = "score", precision = 19, scale = 2)
    // private BigDecimal score = new BigDecimal(0);
    @Column(name = "score")
    private int score = 0;

    @OneToMany(mappedBy = "holder",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @OrderBy("level desc")
    private List<Title> titles = new ArrayList<>();

    @OneToMany(mappedBy = "holder",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<TitleHistory> titleHistories = new ArrayList<>();

//    @MappedCollection(keyColumn = "title_code") // 来自于 spring-data-jdbc 包
//    private final @org.springframework.data.annotation.AccessType
//   (org.springframework.data.annotation.AccessType.Type.FIELD) @With(AccessLevel.PACKAGE) Map<String, Oh> ohs = new HashMap<>();
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "oh",
            inverseJoinColumns = @JoinColumn(name = "title_code"),
            joinColumns = @JoinColumn(name = "ch_code"))
    private List<Title> oh = new ArrayList<>();

    public Character() {}

    public Character(String code,String name) {
        this.code = code;
        this.name = name;
    }

    public Character(String code, String name, Boolean fem, Date bd, Date dd) {
        this.code = code;
        this.name = name;
        this.fem = fem;
        this.bd = bd;
        this.dd = dd;
    }

    public Character(String code, String name, Boolean fem, Date bd, Date dd, Dynasty dnt, Character fat, Character mot, Character host) {
        this.code = code;
        this.name = name;
        this.fem = fem;
        this.bd = bd;
        this.dd = dd;
        this.dnt = dnt;
        this.fat = fat;
        this.mot = mot;
        this.host = host;
    }

}
