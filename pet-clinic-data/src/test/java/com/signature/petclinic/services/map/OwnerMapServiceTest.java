package com.signature.petclinic.services.map;

import com.signature.petclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OwnerMapServiceTest {

  private final Long id1 = 1L;
  private final Long id2 = 2L;
  private final String firstName = "dummy";
  private final String lastName = "dump";
  private OwnerMapService ownerMapService;

  @BeforeEach
  void setUp() {
    this.ownerMapService = new OwnerMapService(new PetMapService(), new PetTypeMapService());
    this.ownerMapService.save(Owner.builder().id(id1).firstName(firstName).lastName(lastName).build());
  }

  @Test
  void save() {
    Owner saved = ownerMapService.save(Owner.builder()
      .id(id2).firstName("white").lastName("devil").build());
    assertNotNull(saved);
    assertEquals(id2, saved.getId());
  }

  @Test
  void findById() {
    Owner owner = ownerMapService.findById(id1);
    assertNotNull(owner);
    assertEquals(id1, owner.getId());
  }

  @Test
  void findByLastName() {
    Owner owner = ownerMapService.findByLastName(lastName);
    assertNotNull(owner);
    assertEquals(id1, owner.getId());
    assertEquals(lastName, owner.getLastName());
  }

  @Test
  void findAll() {
    Set<Owner> owners = ownerMapService.findAll();
    assertNotNull(owners);
    assertEquals(1L, owners.size());
  }

  @Test
  void deleteById() {
    ownerMapService.deleteById(id1);
    assertEquals(0L, ownerMapService.findAll().size());
  }

  @Test
  void delete() {
    Owner owner = ownerMapService.findById(id1);
    assertNotNull(owner);

    ownerMapService.delete(owner);
    assertEquals(0L, ownerMapService.findAll().size());
  }
}