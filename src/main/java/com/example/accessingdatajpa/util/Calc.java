package com.example.accessingdatajpa.util;

import com.example.accessingdatajpa.entity.Dynasty;
import com.example.accessingdatajpa.entity.Title;
import com.example.accessingdatajpa.entity.Character;
import com.example.accessingdatajpa.entity.TitleLevel;

import java.util.List;

public class Calc {

    public static void calcLevel(Character character) {
        List<Title> titleList;
        // 根据状态，两种取法
        if(character.getDd() == null) {
            titleList = character.getTitles();
        } else {
            titleList = character.getOh();
        }
        // 统计
        if(titleList.size() > 0) {
            character.setLevel(titleList.get(0).getLevel());
        } else {
            // nobody
            character.setLevel(TitleLevel.N);
        }
    }

    public static void calcScore(Character character) {
        int sum = 0;
        List<Title> titleList;
        // 根据状态，两种取法
        if(character.getDd() == null) {
            titleList = character.getTitles();
        } else {
            titleList = character.getOh();
        }
        // 统计
        if (titleList.size() > 0) {
            for(Title title : titleList) {
                sum += title.getLevel().getScore();
            }
        }
        character.setScore(sum);
    }

    public static void calcScore(Dynasty dynasty) {
        int sum = 0;
        List<Character> characterList = dynasty.getDescendant();
        if (characterList.size() > 0) {
            for (Character character:characterList) {
                sum += character.getScore();
            }
        }
        dynasty.setScore(sum);
    }

}
