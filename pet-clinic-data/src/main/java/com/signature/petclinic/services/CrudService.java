package com.signature.petclinic.services;

import java.util.Set;

public interface CrudService<T, ID> {

    T save(T entity);

    T findById(ID id);

    Set<T> findAll();

    void deleteById(ID id);

    void delete(T entity);

}