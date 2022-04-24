package com.signature.petclinic.services.map;

import com.signature.petclinic.model.Pet;
import com.signature.petclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class PetMapService extends AbstractMapService<Pet, Long> implements PetService {

  @Override
  public Pet save(Pet entity) {
    return super.save(entity);
  }

  @Override
  public Pet findById(Long id) {
    return super.findById(id);
  }

  @Override
  public Set<Pet> findAll() {
    return super.findAll();
  }

  @Override
  public void deleteById(Long id) {
    super.deleteById(id);
  }

  @Override
  public void delete(Pet entity) {
    super.delete(entity);
  }
}