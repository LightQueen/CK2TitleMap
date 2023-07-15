package com.example.accessingdatajpa.entity;

import lombok.ToString;

@ToString
public class TitleDay {
    private String title;
    private Integer hold_days;
    private Integer expired_days;
    public TitleDay(String title,Integer hold_days,Integer expired_days) {
        this.title = title;
        this.hold_days = hold_days;
        this.expired_days = expired_days;
    }
}
