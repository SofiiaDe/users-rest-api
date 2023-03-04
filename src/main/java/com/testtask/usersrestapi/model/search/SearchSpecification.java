package com.testtask.usersrestapi.model.search;

import com.testtask.usersrestapi.model.payload.request.FilterRequest;
import com.testtask.usersrestapi.model.payload.request.SearchRequest;
import com.testtask.usersrestapi.model.payload.request.SortRequest;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Log4j2
@AllArgsConstructor
public class SearchSpecification<T> implements Specification<T> {

  @Serial
  private static final long serialVersionUID = 745345343320750644L;

  private final transient SearchRequest request;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    Predicate predicate = cb.equal(cb.literal(Boolean.TRUE), Boolean.TRUE);

    for (FilterRequest filter : this.request.getFilters()) {
      log.info("Filter: {} {} {}", filter.getKey(), filter.getOperator().toString(),
          filter.getValue());
      predicate = filter.getOperator().build(root, cb, filter, predicate);
    }

    List<Order> orders = new ArrayList<>();
    for (SortRequest sort : this.request.getSorts()) {
      orders.add(sort.getDirection().build(root, cb, sort));
    }

    query.orderBy(orders);
    return predicate;
  }

  public static Pageable getPageable(Integer page, Integer size) {
    return PageRequest.of(Objects.requireNonNullElse(page, 0),
        Objects.requireNonNullElse(size, 100));
  }
}
