package com.testtask.usersrestapi.model.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.testtask.usersrestapi.model.enumeration.FieldType;
import com.testtask.usersrestapi.model.enumeration.Operator;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Accessors(chain = true)
public class FilterRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 74333448490786454L;
  private String key;
  private Operator operator;
  private FieldType fieldType;
  private transient Object value;
  private transient Object valueTo;
  private transient List<Object> values;
}
