package com.example.accessingdatajpa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.*;

@Entity
@ToString(of = {"code","name","fem","bd","dd","score"})
@Table(name="ch")
@Getter
@Setter
@EqualsAndHashCode
public class Ch {
    

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
    private Ch fat;

    @OneToMany(mappedBy = "fat",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @OrderBy("bd")
    private List<Ch> fatChildren = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.ALL})
    private Ch mot;

    @OneToMany(mappedBy = "mot",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @OrderBy("bd")
    private List<Ch> motChildren = new ArrayList<>();

    public void addChild(Ch ch){
        if(fem){
            ch.setMot(this);
            motChildren.add(ch);
        } else {
            ch.setFat(this);
            fatChildren.add(ch);
        }
    }

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "host_code")
    private Ch host;

    @OneToMany(mappedBy = "host",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Ch> guests = new ArrayList<>();

    public void addGuest(Ch ch) {
        ch.setHost(this);
        guests.add(ch);
    }

    // @Column(name = "score", precision = 19, scale = 2)
    // private BigDecimal score = new BigDecimal(0);
    @Column(name = "score")
    private int score = 0;

    @OneToMany(mappedBy = "holder",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @OrderBy("level desc")
    private List<Title> titles = new ArrayList<>();

    public List<Title> getTitles() {
        // 如果不想修改
        // 那么 return Collections.unmodifiableList(titles);
        return new ArrayList<>(titles);
    }

    /**
     * 相应的，不使用 setHolder 方法
     * @param title
     */
    public void addTitle(Title title) {
        title.setHolder(this);
        titles.add(title);
    }

    @OneToMany(mappedBy = "holder",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @OrderBy("sd desc")
    private List<TitleHistory> titleHistories = new ArrayList<>();


//    @MappedCollection(keyColumn = "title_code") // 来自于 spring-data-jdbc 包
//    private final @org.springframework.data.annotation.AccessType
//   (org.springframework.data.annotation.AccessType.Type.FIELD) @With(AccessLevel.PACKAGE) Map<String, Oh> ohs = new HashMap<>();
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "oh",
            inverseJoinColumns = @JoinColumn(name = "title_code"),
            joinColumns = @JoinColumn(name = "ch_code"))
    private List<Title> oh = new ArrayList<>();

    public Ch() {}

    public Ch(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Ch(String code, String name, Boolean fem, Date bd, Date dd) {
        this.code = code;
        this.name = name;
        this.fem = fem;
        this.bd = bd;
        this.dd = dd;
    }

    public Ch(String code, String name, Boolean fem, Date bd, Date dd, Dynasty dnt, Ch fat, Ch mot, Ch host) {
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
