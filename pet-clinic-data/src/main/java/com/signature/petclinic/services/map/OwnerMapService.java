package com.signature.petclinic.services.map;

import com.signature.petclinic.model.Owner;
import com.signature.petclinic.model.Pet;
import com.signature.petclinic.services.OwnerService;
import com.signature.petclinic.services.PetService;
import com.signature.petclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile({"default", "map"})
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {

  private final PetService petService;
  private final PetTypeService petTypeService;

  public OwnerMapService(PetService petService, PetTypeService petTypeService) {
    this.petService = petService;
    this.petTypeService = petTypeService;
  }

  @Override
  public Owner save(Owner entity) {
    if (entity == null) {
      return null;
    } else {
      if (entity.getPets() != null) {
        entity.getPets().forEach(pet -> {
          if (pet.getPetType() == null) {
            throw new RuntimeException("Pet Type is required");
          } else {
            if (pet.getPetType().getId() == null) {
              pet.setPetType(petTypeService.save(pet.getPetType()));
            }
          }

          if (pet.getId() == null) {
            Pet savedPet = petService.save(pet);
            pet.setId(savedPet.getId());
          }
        });
      }
      return super.save(entity);
    }
  }

  @Override
  public Owner findById(Long id) {
    return super.findById(id);
  }

  @Override
  public Owner findByLastName(String lastName) {
    return super.findAll().stream().findAny().filter(owner -> owner.getLastName().equals(lastName)).orElse(null);
  }

  @Override
  public Set<Owner> findAll() {
    return super.findAll();
  }

  @Override
  public List<Owner> findAllByLastNameLike(String lastName) {
    return findAll().stream()
      .filter(owner -> StringUtils.startsWithIgnoreCase(owner.getLastName(), lastName))
      .collect(Collectors.toList());
  }

  @Override
  public Page<Owner> searchAllByLastNameLike(String lastName, Pageable pageable) {
    lastName = lastName == null ? "" : lastName;
    List<Owner> owners = findAllByLastNameLike(lastName);
    return new PageImpl<>(owners, pageable, owners.size());
  }

  @Override
  public void deleteById(Long id) {
    super.deleteById(id);
  }

  @Override
  public void delete(Owner entity) {
    super.delete(entity);
  }
}