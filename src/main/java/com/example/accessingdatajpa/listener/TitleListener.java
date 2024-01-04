package com.example.accessingdatajpa.listener;

import com.example.accessingdatajpa.entity.Title;

import javax.persistence.PrePersist;

public class TitleListener {

    @PrePersist
    public void prePersist(Title title) {

    }

}
