package com.signature.petclinic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "pets")
public class Pet extends BaseEntity {

  @Column(name = "name")
  private String name;

  @OneToOne
  @JoinColumn(name = "type_id")
  private PetType petType;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private Owner owner;

  @Column(name = "birth_date")
  private LocalDate birthdate;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
  private Set<Visit> visits;

  public Pet() {
    this.visits = new HashSet<>();
  }

  @Builder
  public Pet(Long id, String name, PetType petType, Owner owner, LocalDate birthdate, Set<Visit> visits) {
    super(id);
    this.name = name;
    this.petType = petType;
    this.owner = owner;
    this.birthdate = birthdate;
    this.visits = Objects.requireNonNullElseGet(visits, HashSet::new);
  }

  public Pet addVisit(Visit visit) {
    visit.setPet(this);
    this.visits.add(visit);
    return this;
  }

  @Override
  public String toString() {
    return name;
  }
}