package com.signature.petclinic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "visits")
public class Visit extends BaseEntity {

  @Column(name = "date")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate date;

  @Column(name = "description")
  private String description;

  @ManyToOne
  @JoinColumn(name = "pet_id")
  private Pet pet;

  public Visit(Long id, LocalDate date, String description) {
    super(id);
    this.date = date;
    this.description = description;
  }

  @Builder
  public Visit(Long id, LocalDate date, String description, Pet pet) {
    super(id);
    this.date = date;
    this.description = description;
    this.pet = pet;
  }
}