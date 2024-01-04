package com.example.accessingdatajpa.dao;

import com.example.accessingdatajpa.entity.Ch;
import com.example.accessingdatajpa.entity.Dynasty;

import java.util.List;

public interface ChRepository extends BaseRepository<Ch> {

    List<Ch> findAllByDnt(Dynasty dynasty);


    List<Ch> findAllByDnt_CodeOrderByLevelDesc(String code);

    List<Ch> findAllByDntCodeAndDdIsNullOrderByLevelDesc(String code);

    List<Ch> findAllByHost_CodeIn(String[] codes);

    List<Ch> findAllByHostIn(Ch[] hosts);
}