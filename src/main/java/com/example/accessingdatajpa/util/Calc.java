package com.example.accessingdatajpa.util;

import com.example.accessingdatajpa.entity.Dynasty;
import com.example.accessingdatajpa.entity.Title;
import com.example.accessingdatajpa.entity.Ch;
import com.example.accessingdatajpa.entity.TitleLevel;

import java.util.List;

public class Calc {

    public static void calcLevel(Ch ch) {
        List<Title> titleList;
        // 根据状态，两种取法
        if(ch.getDd() == null) {
            titleList = ch.getTitles();
        } else {
            titleList = ch.getOh();
        }
        // 统计
        if(titleList.size() > 0) {
            ch.setLevel(titleList.get(0).getLevel());
        } else {
            // nobody
            ch.setLevel(TitleLevel.N);
        }
    }

    public static void calcLevel(Title title) {
        title.setLevel(TitleLevel.of(title.getCode()));
    }

    public static void calcScore(Ch ch) {
        int sum = 0;
        List<Title> titleList;
        // 根据状态，两种取法
        if(ch.getDd() == null) {
            titleList = ch.getTitles();
        } else {
            titleList = ch.getOh();
        }
        // 统计
        if (titleList.size() > 0) {
            for(Title title : titleList) {
                sum += title.getLevel().getScore();
            }
        }
        ch.setScore(sum);
    }

    public static void calcScore(Dynasty dynasty) {
        int sum = 0;
        List<Ch> chList = dynasty.getDescendants();
        if (chList.size() > 0) {
            for (Ch ch : chList) {
                sum += ch.getScore();
            }
        }
        dynasty.setScore(sum);
    }

}
