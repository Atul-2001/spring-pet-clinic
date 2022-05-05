package com.signature.petclinic.repository;

import com.signature.petclinic.model.Vet;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VetRepository extends PagingAndSortingRepository<Vet, Long> {

}