package com.signature.petclinic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "owners")
public class Owner extends Person {

  @Column(name = "address")
  private String address;

  @Column(name = "city")
  private String city;

  @Column(name = "telephone")
  private String telephone;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
  private Set<Pet> pets;

  public Owner() {
    this.pets = new HashSet<>();
  }

  public void addPet(Pet pet) {
    if (pet.isNew()) {
      getPets().add(pet);
      pet.setOwner(this);
    }
  }

  public Pet getPet(String name) {
    return getPet(name, false);
  }

  public Pet getPet(Long id) {
    for (Pet pet : getPets()) {
      if (!pet.isNew()) {
        Long compId = pet.getId();
        if (compId.equals(id)) {
          return pet;
        }
      }
    }
    return null;
  }

  public Pet getPet(String name, boolean ignoreNew) {
    name = name.toLowerCase();
    for (Pet pet : getPets()) {
      if (!ignoreNew || !pet.isNew()) {
        String compName = pet.getName();
        compName = compName == null ? "" : compName.toLowerCase();
        if (compName.equals(name)) {
          return pet;
        }
      }
    }
    return null;
  }

  public void addVisit(Long petId, Visit visit) {
    Assert.notNull(petId, "Pet identifier must not be null!");
    Assert.notNull(visit, "Visit must not be null!");

    Pet pet = getPet(petId);

    Assert.notNull(pet, "Invalid Pet identifier!");

    pet.addVisit(visit);
  }

  @Builder
  public Owner(Long id, String firstName, String lastName, String address, String city, String telephone, Set<Pet> pets) {
    super(id, firstName, lastName);
    this.address = address;
    this.city = city;
    this.telephone = telephone;
    this.pets = Objects.requireNonNullElseGet(pets, HashSet::new);
  }
}