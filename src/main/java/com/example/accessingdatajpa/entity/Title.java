package com.example.accessingdatajpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@EntityListeners(TitleListener.class)
@ToString(of = {"code","name"})
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor//(access=AccessLevel.PRIVATE)
@RequiredArgsConstructor //会为 @NonNull 和 final 的属性，生成构造方法
public class Title {
    @Id
    @Column(name = "code", nullable = false)
    @NonNull
    private String code;

    @Column(name = "name")
    @NonNull
    private String name;

    @Column(name = "level")
    private TitleLevel level;

    @ManyToOne
    @JoinColumn(name = "holder_code")
    private Ch holder;

    @ManyToOne
    @JoinColumn(name = "liege_code")
    private Title liege;

    @OneToMany(mappedBy = "liege",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Title> vassals;

    public void addVassal(Title vassal){
        vassal.setLiege(this);
        vassals.add(vassal);
    }

    @ManyToOne
    @JoinColumn(name = "de_jure_liege_code")
    private Title de_jure_liege;

    @OneToMany(mappedBy = "de_jure_liege",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Title> de_jure_vassals;

    public void add_De_jure_vassal(Title de_jure_vassal) {
        de_jure_vassal.setDe_jure_liege(this);
        de_jure_vassals.add(de_jure_vassal);
    }

    @OneToMany(mappedBy = "title",cascade = CascadeType.ALL)
    @OrderBy("sd desc")
    private List<TitleHistory> titleHistories = new ArrayList<>();


    // @PrePersist
    // @PreUpdate
    // public void calcLevel() {
    //     System.out.println( "hello" );
    // }
}
