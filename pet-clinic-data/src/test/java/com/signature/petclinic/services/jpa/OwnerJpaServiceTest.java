package com.signature.petclinic.services.jpa;

import com.signature.petclinic.model.Owner;
import com.signature.petclinic.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerJpaServiceTest {

  private static final String FIRST_NAME = "Atul";
  private static final String LAST_NAME = "Singh";

  @Mock
  public OwnerRepository ownerRepository;

  @InjectMocks
  public OwnerJpaService ownerService;

  private Owner owner;

  @BeforeEach
  void setUp() {
    owner = Owner.builder().id(1L).firstName(FIRST_NAME).lastName(LAST_NAME).build();
  }

  @Test
  void save() {
    when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

    Owner savedOwner = ownerService.save(owner);

    verify(ownerRepository, times(1)).save(any(Owner.class));

    assertNotNull(savedOwner);
  }

  @Test
  void findById() {
    when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));

    Owner ownerById = ownerService.findById(1L);

    verify(ownerRepository, times(1)).findById(anyLong());

    assertNotNull(ownerById);

    assertEquals(1L, ownerById.getId());
  }

  @Test
  void findByIdNotFound() {
    when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

    Owner ownerById = ownerService.findById(1L);

    verify(ownerRepository, times(1)).findById(anyLong());

    assertNull(ownerById);
  }

  @Test
  void findByLastName() {
    when(ownerRepository.findByLastName(anyString())).thenReturn(owner);

    Owner ownerByLastName = ownerService.findByLastName(LAST_NAME);

    verify(ownerRepository, times(1)).findByLastName(anyString());

    assertNotNull(ownerByLastName);

    assertEquals(LAST_NAME, ownerByLastName.getLastName());
  }

  @Test
  void findAll() {
    Set<Owner> ownerSet = new HashSet<>();

    ownerSet.add(Owner.builder().id(1L).build());
    ownerSet.add(Owner.builder().id(2L).build());

    when(ownerRepository.findAll()).thenReturn(ownerSet);

    Set<Owner> owners = ownerService.findAll();

    verify(ownerRepository, times(1)).findAll();

    assertEquals(2, owners.size());
  }

  @Test
  void deleteById() {
    ownerService.deleteById(1L);

    verify(ownerRepository, times(1)).deleteById(anyLong());
  }

  @Test
  void delete() {
    ownerService.delete(owner);

    verify(ownerRepository, times(1)).delete(any(Owner.class));
  }
}