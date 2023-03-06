package com.testtask.usersrestapi.model.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
public class SearchRequest {

  private static final long serialVersionUID = 5487625832019794838L;

  private List<FilterRequest> filters;
  private List<SortRequest> sorts;
  private Integer page;
  private Integer size;

  public List<FilterRequest> getFilters() {
    if (Objects.isNull(this.filters)) {
      return new ArrayList<>();
    }
    return this.filters;
  }

  public List<SortRequest> getSorts() {
    if (Objects.isNull(this.sorts)) {
      return new ArrayList<>();
    }
    return this.sorts;
  }

}