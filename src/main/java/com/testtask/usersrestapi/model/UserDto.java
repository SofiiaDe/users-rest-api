package com.testtask.usersrestapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class UserDto {

  private Long id;

  private String email;

  private String firstName;

  private String lastName;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private String birthDate;

  private String address;

  private String phoneNumber;

}
