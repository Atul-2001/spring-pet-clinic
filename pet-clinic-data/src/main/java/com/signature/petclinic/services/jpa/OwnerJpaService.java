package com.signature.petclinic.services.jpa;

import com.signature.petclinic.model.Owner;
import com.signature.petclinic.repository.OwnerRepository;
import com.signature.petclinic.services.OwnerService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Profile("spring-data-jpa")
public class OwnerJpaService implements OwnerService {

  private final OwnerRepository ownerRepository;

  public OwnerJpaService(OwnerRepository ownerRepository) {
    this.ownerRepository = ownerRepository;
  }

  @Override
  public Owner save(Owner entity) {
    return ownerRepository.save(entity);
  }

  @Override
  public Owner findById(Long id) {
    return ownerRepository.findById(id).orElse(null);
  }

  @Override
  public Owner findByLastName(String lastName) {
    return ownerRepository.findByLastName(lastName);
  }

  @Override
  public Set<Owner> findAll() {
    return StreamSupport.stream(ownerRepository.findAll().spliterator(), false)
      .collect(Collectors.toSet());
  }

  @Override
  public List<Owner> findAllByLastNameLike(String lastName) {
    lastName = lastName == null ? "" : lastName;
    return ownerRepository.findAllByLastNameStartsWith(lastName);
  }

  @Override
  public Page<Owner> searchAllByLastNameLike(String lastName, Pageable pageable) {
    lastName = lastName == null ? "" : lastName;
    return ownerRepository.searchAllByLastNameStartsWith(lastName, pageable);
  }

  @Override
  public void deleteById(Long id) {
    ownerRepository.deleteById(id);
  }

  @Override
  public void delete(Owner entity) {
    ownerRepository.delete(entity);
  }
}