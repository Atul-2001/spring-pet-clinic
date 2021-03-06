package com.signature.petclinic.services.jpa;

import com.signature.petclinic.model.Vet;
import com.signature.petclinic.repository.VetRepository;
import com.signature.petclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Profile("spring-data-jpa")
public class VetJpaService implements VetService {

  private final VetRepository vetRepository;

  public VetJpaService(VetRepository vetRepository) {
    this.vetRepository = vetRepository;
  }

  @Override
  public Vet save(Vet entity) {
    return vetRepository.save(entity);
  }

  @Override
  public Vet findById(Long id) {
    return vetRepository.findById(id).orElse(null);
  }

  @Override
  public Set<Vet> findAll() {
    return StreamSupport.stream(vetRepository.findAll().spliterator(), false)
      .collect(Collectors.toSet());
  }

  @Override
  public Page<Vet> findAll(Pageable pageable) {
    return vetRepository.findAll(pageable);
  }

  @Override
  public void deleteById(Long id) {
    vetRepository.deleteById(id);
  }

  @Override
  public void delete(Vet entity) {
    vetRepository.delete(entity);
  }
}