package com.testtask.usersrestapi.utils.validation;

import com.testtask.usersrestapi.utils.validation.constraints.DateRangeValidation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@DateRangeValidation
public record DateRangeParameters(@Past
                                  @NotNull
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                  LocalDate from,
                                  @Past
                                  @NotNull
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                  LocalDate to) {


}
