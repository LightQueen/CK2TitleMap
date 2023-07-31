package com.example.accessingdatajpa.dao;

import com.example.accessingdatajpa.entity.Ch;
import com.example.accessingdatajpa.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TitleRepository extends JpaRepository<Title, String> {

    List<Title> findByHolderInAndCodeLike(Ch[] chs, String code);

    List<Title> findByHolder_CodeInAndCodeLike(String[] codes, String code);

}