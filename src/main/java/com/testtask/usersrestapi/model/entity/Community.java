package com.testtask.usersrestapi.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity(name = "community")
@Data
@Accessors(chain = true)
public class Community {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;

}
