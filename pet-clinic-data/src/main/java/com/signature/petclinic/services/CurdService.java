package com.signature.petclinic.services;

import java.util.Optional;
import java.util.Set;

public interface CurdService<T, ID> {

    T save(T entity);

    Optional<T> findById(ID id);

    Set<T> findAll();

    void deleteById(ID id);

    void delete(T entity);

}