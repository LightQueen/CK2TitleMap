package com.example.accessingdatajpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString(of = {"code","name"})
@Getter
@Setter
@EqualsAndHashCode(exclude = {"holder","liege","vassals","de_jure_liege","de_jure_vassals","titleHistories"})
@NoArgsConstructor
//@RequiredArgsConstructor //会为 @NonNull 和 final 的属性，生成构造方法
public class Title extends BaseEntity{
    @Id
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "level")
    private TitleLevel level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "holder_code")
    private Ch holder;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "liege_code")
    private Title liege;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "liege",cascade = CascadeType.ALL)
    private List<Title> vassals;

    public void addVassal(Title vassal){
        vassal.setLiege(this);
        vassals.add(vassal);
    }

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "de_jure_liege_code")
    private Title de_jure_liege;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "de_jure_liege",cascade = CascadeType.ALL)
    private List<Title> de_jure_vassals;

    public void add_De_jure_vassal(Title de_jure_vassal) {
        de_jure_vassal.setDe_jure_liege(this);
        de_jure_vassals.add(de_jure_vassal);
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "title",cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sd desc")
    private List<TitleHistory> titleHistories = new ArrayList<>();

    public void addTitleHistory(TitleHistory titleHistory) {
        titleHistories.add(titleHistory);
        titleHistory.setTitle(this);
    }

    public Title(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // @PrePersist
    // @PreUpdate
    // public void calcLevel() {
    //     System.out.println( "hello" );
    // }
}
