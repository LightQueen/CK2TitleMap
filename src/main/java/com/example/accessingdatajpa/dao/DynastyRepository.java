package com.example.accessingdatajpa.dao;

import com.example.accessingdatajpa.entity.Dynasty;
import org.springframework.data.repository.CrudRepository;

public interface DynastyRepository extends CrudRepository<Dynasty, String> {


}