package com.testtask.usersrestapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class UserDto {

  @NotBlank
  private Long id;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Size(min = 2)
  private String firstName;

  @NotBlank
  @Size(min = 2)
  private String lastName;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDate;

  private String address;

  @Pattern(regexp = "^(\\+38)(\\(0\\d{2}\\))(\\d){3}(\\-\\d{2}){2}$")
  private String phoneNumber;

}
