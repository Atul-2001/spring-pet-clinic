package com.signature.petclinic.services;

import com.signature.petclinic.model.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OwnerService extends CrudService<Owner, Long> {

  Owner findByLastName(String lastName);

  List<Owner> findAllByLastNameLike(String lastName);

  Page<Owner> searchAllByLastNameLike(String lastName, Pageable pageable);
}