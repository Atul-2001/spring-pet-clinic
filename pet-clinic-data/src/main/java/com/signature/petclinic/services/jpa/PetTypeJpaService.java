package com.signature.petclinic.services.jpa;

import com.signature.petclinic.model.PetType;
import com.signature.petclinic.repository.PetTypeRepository;
import com.signature.petclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Profile("spring-data-jpa")
public class PetTypeJpaService implements PetTypeService {

  private final PetTypeRepository petTypeRepository;

  public PetTypeJpaService(PetTypeRepository petTypeRepository) {
    this.petTypeRepository = petTypeRepository;
  }

  @Override
  public PetType save(PetType entity) {
    return petTypeRepository.save(entity);
  }

  @Override
  public PetType findById(Long id) {
    return petTypeRepository.findById(id).orElse(null);
  }

  @Override
  public Set<PetType> findAll() {
    return StreamSupport.stream(petTypeRepository.findAll().spliterator(), false)
      .collect(Collectors.toSet());
  }

  @Override
  public void deleteById(Long id) {
    petTypeRepository.deleteById(id);
  }

  @Override
  public void delete(PetType entity) {
    petTypeRepository.delete(entity);
  }
}