package com.signature.petclinic.repository;

import com.signature.petclinic.model.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

  Owner findByLastName(String lastName);

  List<Owner> findAllByLastNameStartsWith(String lastName);

  Page<Owner> searchAllByLastNameStartsWith(String lastName, Pageable pageable);
}