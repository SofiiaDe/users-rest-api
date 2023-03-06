package com.testtask.usersrestapi.utils.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.testtask.usersrestapi.utils.validation.constraints.DateRangeValidation;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;


@DateRangeValidation
public record DateRangeParameters(@Past
                                  @NotNull
                                  @DateTimeFormat(pattern = "yyyy-MM-dd")
                                  @JsonProperty("from")
                                  @JsonSerialize(using = LocalDateSerializer.class)
                                  LocalDate from,
                                  @Past
                                  @NotNull
                                  @DateTimeFormat(pattern = "yyyy-MM-dd")
                                  @JsonProperty("to")
                                  @JsonSerialize(using = LocalDateSerializer.class)
                                  LocalDate to) {

}
