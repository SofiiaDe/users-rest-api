package com.testtask.usersrestapi.model.mapper;

public interface EntityMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

}
