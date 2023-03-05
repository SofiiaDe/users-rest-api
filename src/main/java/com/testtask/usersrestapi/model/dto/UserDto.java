package com.testtask.usersrestapi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.testtask.usersrestapi.utils.validation.constraints.BirthDateValidation;
//import jakarta.validation.constraints.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

//@NoArgsConstructor
//@AllArgsConstructor
@Data
@Accessors(chain = true)
public class UserDto {

    @NotNull
    private Long id;

    @NotBlank(message = "The email address is required.")
    @Email(message = "The email address is invalid.", flags = {Pattern.Flag.CASE_INSENSITIVE})
    private String email;

    @NotBlank(message = "The first name is required.")
    @Size(min = 2, max = 50, message = "The length of first name must be between 2 and 50 characters.")
    private String firstName;

    @NotBlank(message = "The last name is required.")
    @Size(min = 2, max = 50, message = "The length of first name must be between 2 and 50 characters.")
    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "The date of birth is required.")
    @BirthDateValidation(message = "User should be more than than 18 years old")
    @Past(message = "The date of birth must be in the past.")
    private LocalDate birthDate;

    private String address;

    @Pattern(regexp = "^(\\(0\\d{2}\\))(\\d){3}(\\-\\d{2}){2}$")
    private String phoneNumber;

}
