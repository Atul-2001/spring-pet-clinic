package com.signature.petclinic.services.jpa;

import com.signature.petclinic.model.Speciality;
import com.signature.petclinic.repository.SpecialityRepository;
import com.signature.petclinic.services.SpecialityService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Profile("spring-data-jpa")
public class SpecialityJpaService implements SpecialityService {

  private final SpecialityRepository specialityRepository;

  public SpecialityJpaService(SpecialityRepository specialityRepository) {
    this.specialityRepository = specialityRepository;
  }

  @Override
  public Speciality save(Speciality entity) {
    return specialityRepository.save(entity);
  }

  @Override
  public Speciality findById(Long id) {
    return specialityRepository.findById(id).orElse(null);
  }

  @Override
  public Set<Speciality> findAll() {
    return StreamSupport.stream(specialityRepository.findAll().spliterator(), false)
      .collect(Collectors.toSet());
  }

  @Override
  public void deleteById(Long id) {
    specialityRepository.deleteById(id);
  }

  @Override
  public void delete(Speciality entity) {
    specialityRepository.delete(entity);
  }
}