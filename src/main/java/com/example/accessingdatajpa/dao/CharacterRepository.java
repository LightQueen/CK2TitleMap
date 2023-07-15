package com.example.accessingdatajpa.dao;

import com.example.accessingdatajpa.entity.Character;
import com.example.accessingdatajpa.entity.Dynasty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, String> {

    List<Character> findAllByDnt(Dynasty dynasty);

    List<Character> findAllByDnt_CodeOrderByLevelDesc(String code);

    List<Character> findAllByDntCodeAndDdIsNullOrderByLevelDesc(String code);

    List<Character> findAllByHost_CodeIn(String[] codes);

    List<Character> findAllByHostIn(Character[] hosts);
}