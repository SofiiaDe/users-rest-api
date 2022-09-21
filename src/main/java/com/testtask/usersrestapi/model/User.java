package com.testtask.usersrestapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

  private Long id;

  private String email;

  private String firstName;

  private String lastName;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private String birthDate;

  private String address;

  private String phoneNumber;


}
