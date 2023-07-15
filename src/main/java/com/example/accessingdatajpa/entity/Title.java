package com.example.accessingdatajpa.entity;

import com.example.accessingdatajpa.listener.TitleListener;
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
    private Character holder;

    @ManyToOne
    @JoinColumn(name = "liege_code")
    private Title liege;

    @ManyToOne
    @JoinColumn(name = "de_jure_liege_code")
    private Title de_jure_liege;

    @OneToMany(mappedBy = "title",cascade = CascadeType.ALL)
    @OrderBy("sd desc")
    private List<TitleHistory> tittleHistories = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void calcLevel() {
        System.out.println( "hello" );
    }
}
