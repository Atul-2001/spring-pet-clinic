package com.signature.petclinic.services.map;

import com.signature.petclinic.model.Speciality;
import com.signature.petclinic.model.Vet;
import com.signature.petclinic.services.SpecialityService;
import com.signature.petclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
@Profile({"default", "map"})
public class VetMapService extends AbstractMapService<Vet, Long> implements VetService {

  private final SpecialityService specialityService;

  public VetMapService(SpecialityService specialityService) {
    this.specialityService = specialityService;
  }

  @Override
  public Vet save(Vet entity) {
    if (entity == null) {
      return null;
    } else {
      if (entity.getSpecialities() != null && entity.getSpecialities().size() > 0) {
        entity.getSpecialities().forEach(speciality -> {
          if (speciality.getId() == null) {
            Speciality savedSpeciality = specialityService.save(speciality);
            speciality.setId(savedSpeciality.getId());
          }
        });
      }
      return super.save(entity);
    }
  }

  @Override
  public Vet findById(Long id) {
    return super.findById(id);
  }

  @Override
  public Set<Vet> findAll() {
    return super.findAll();
  }

  @Override
  public Page<Vet> findAll(Pageable pageable) {
    Set<Vet> vets = findAll();
    return new PageImpl<>(new ArrayList<>(vets), pageable, vets.size());
  }

  @Override
  public void deleteById(Long id) {
    super.deleteById(id);
  }

  @Override
  public void delete(Vet entity) {
    super.delete(entity);
  }
}