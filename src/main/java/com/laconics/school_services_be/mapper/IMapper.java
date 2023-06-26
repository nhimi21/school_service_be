package com.laconics.school_services_be.mapper;

public interface IMapper<E, D> {
    D toDto(E e);
    E toEntity(D d);
}
