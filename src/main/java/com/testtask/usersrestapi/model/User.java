package com.testtask.usersrestapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User {

  private Long id;

  private String email;

  private String firstName;

  private String lastName;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDate;

  private String address;

  private String phoneNumber;


}
