package com.example.health.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Department {

  @Id
  @GeneratedValue
  private Integer id;

  private String name;
  private Integer limitPerPeriod;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public Integer getLimitPerPeriod() {
    return limitPerPeriod;
  }

  public void setLimitPerPeriod(Integer limitPerPeriod) {
    this.limitPerPeriod = limitPerPeriod;
  }

}
