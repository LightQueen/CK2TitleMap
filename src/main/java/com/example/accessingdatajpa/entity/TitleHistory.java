package com.example.accessingdatajpa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TitleHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "s_d")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyy.M.d")
    private Date sd;

    @Column(name = "e_d")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyy.M.d")
    private Date ed;

    @Column(name = "hold_days")
    @Builder.Default
    private Integer holdDays = 0;

    @Column(name = "expired_days")
    @Builder.Default
    private Integer expiredDays = 0;

    @Column(name = "is_latest")
    @Builder.Default
    private Boolean latest = false;

    @ManyToOne
    @JoinColumn(name = "title_code")
    private Title title;

    @ManyToOne
    @JoinColumn(name = "holder_code")
    private Character holder;

}
