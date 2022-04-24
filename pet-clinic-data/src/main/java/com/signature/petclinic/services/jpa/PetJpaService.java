package com.signature.petclinic.services.jpa;

import com.signature.petclinic.model.Pet;
import com.signature.petclinic.repository.PetRepository;
import com.signature.petclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Profile("spring-data-jpa")
public class PetJpaService implements PetService {

  private final PetRepository petRepository;

  public PetJpaService(PetRepository petRepository) {
    this.petRepository = petRepository;
  }

  @Override
  public Pet save(Pet entity) {
    return petRepository.save(entity);
  }

  @Override
  public Pet findById(Long id) {
    return petRepository.findById(id).orElse(null);
  }

  @Override
  public Set<Pet> findAll() {
    return StreamSupport.stream(petRepository.findAll().spliterator(), false)
      .collect(Collectors.toSet());
  }

  @Override
  public void deleteById(Long id) {
    petRepository.deleteById(id);
  }

  @Override
  public void delete(Pet entity) {
    petRepository.delete(entity);
  }
}