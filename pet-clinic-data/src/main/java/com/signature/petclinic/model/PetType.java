package com.signature.petclinic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "types")
public class PetType extends BaseEntity {

  @Column(name = "name")
  private String name;

  @Builder
  public PetType(Long id, String name) {
    super(id);
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}