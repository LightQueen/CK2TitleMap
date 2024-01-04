package com.example.accessingdatajpa.entity;

/**
 * 头衔级别
 *
 * @author Administrator
 */
public enum TitleLevel {
    N(0),B(1),C(5),D(10),K(20),E(50);
    private int score;
    TitleLevel(int score) {
        this.score = score;
    }
    public static TitleLevel of(String value){
        TitleLevel[] values = TitleLevel.values();
        for (TitleLevel t : values) {
            if (t.name().equals(value.substring(0,1).toUpperCase())){
                return t;
            }
        }
        return null;
    }

    public int getScore() {
        return score;
    }
}
