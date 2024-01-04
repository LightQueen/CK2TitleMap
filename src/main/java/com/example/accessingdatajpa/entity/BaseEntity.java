package com.example.accessingdatajpa.entity;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    abstract public String getCode();

    abstract public void setCode(String code);

}
