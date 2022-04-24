package com.signature.petclinic.services.jpa;

import com.signature.petclinic.model.Visit;
import com.signature.petclinic.repository.VisitRepository;
import com.signature.petclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Profile("spring-data-jpa")
public class VisitJpaService implements VisitService {

  private final VisitRepository visitRepository;

  public VisitJpaService(VisitRepository visitRepository) {
    this.visitRepository = visitRepository;
  }

  @Override
  public Visit save(Visit entity) {
    return visitRepository.save(entity);
  }

  @Override
  public Visit findById(Long id) {
    return visitRepository.findById(id).orElse(null);
  }

  @Override
  public Set<Visit> findAll() {
    return StreamSupport.stream(visitRepository.findAll().spliterator(), false)
      .collect(Collectors.toSet());
  }

  @Override
  public void deleteById(Long id) {
    visitRepository.deleteById(id);
  }

  @Override
  public void delete(Visit entity) {
    visitRepository.delete(entity);
  }
}