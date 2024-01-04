package com.example.accessingdatajpa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

@Entity
@ToString(of = {"code","name","fem","bd","dd","score"})
@Table(name="ch")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"fat","mot","host","guests","fatChildren","motChildren","dnt"})
public class Ch extends BaseEntity{

    @Id
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "fem")
    private Boolean fem = false;

    @Column(name = "b_d")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyy.M.d")
    private Date bd;

    @Column(name = "d_d")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyy.M.d")
    private Date dd;

    /**
     * 威望
     */
    @Column(name = "prs")
    private BigDecimal prs;

    /**
     * 虔诚
     */
    @Column(name = "piety")
    private BigDecimal piety;

    /**
     * 财富
     */
    @Column(name = "wealth")
    private BigDecimal wealth;

    @Column(name = "level")
    private TitleLevel level;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "dnt_code")
    private Dynasty dnt;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    private Ch fat;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "fat",cascade = CascadeType.ALL)
    @OrderBy("bd")
    private List<Ch> fatChildren = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    private Ch mot;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "mot",cascade = CascadeType.ALL)
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

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "host_code")
    private Ch host;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "host",cascade = CascadeType.ALL)
    private List<Ch> guests = new ArrayList<>();

    public void addGuest(Ch ch) {
        ch.setHost(this);
        guests.add(ch);
    }

    // @Column(name = "score", precision = 19, scale = 2)
    // private BigDecimal score = new BigDecimal(0);
    @Column(name = "score")
    private int score = 0;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "holder",cascade = CascadeType.ALL)
    @OrderBy("level desc")
    private List<Title> titles = new ArrayList<>();

//    public List<Title> getTitles() {
//        // 如果不想修改
//        // 那么 return Collections.unmodifiableList(titles);
//        return new ArrayList<>(titles);
//    }

    /**
     * 不建议使用 setHolder 方法，而是使用 addTitle 方法
     * @param title
     */
    public void addTitle(Title title) {
        title.setHolder(this);
        titles.add(title);
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "holder",cascade = CascadeType.ALL)
    @OrderBy("sd desc")
    private Set<TitleHistory> titleHistories = new LinkedHashSet<>();


//    @MappedCollection(keyColumn = "title_code") // 来自于 spring-data-jdbc 包
//    private final @org.springframework.data.annotation.AccessType
//   (org.springframework.data.annotation.AccessType.Type.FIELD) @With(AccessLevel.PACKAGE) Map<String, Oh> ohs = new HashMap<>();
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
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
