package com.example.accessingdatajpa.dao;

import com.example.accessingdatajpa.entity.Character;
import com.example.accessingdatajpa.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TitleRepository extends JpaRepository<Title, String> {

    List<Title> findByHolderInAndCodeLike(Character[] chs, String code);

    List<Title> findByHolder_CodeInAndCodeLike(String[] codes, String code);

}