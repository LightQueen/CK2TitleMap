package com.example.accessingdatajpa.dao;

import com.example.accessingdatajpa.entity.Ch;
import com.example.accessingdatajpa.entity.Title;
import com.example.accessingdatajpa.entity.TitleHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TitleHistoryRepository extends CrudRepository<TitleHistory, Long> {

    List<TitleHistory> findAllByHolder(Ch holder);

    List<TitleHistory> findAllByHolder_code(String code);

    void deleteByTitle(Title title);

}
