package com.signature.petclinic.services;

import com.signature.petclinic.model.Vet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VetService extends CrudService<Vet, Long> {

  Page<Vet> findAll(Pageable pageable);
}