package com.example.health.pojo;


public class Department {

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
