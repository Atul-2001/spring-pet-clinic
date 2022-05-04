package com.signature.petclinic.controller;

import com.signature.petclinic.model.Owner;
import com.signature.petclinic.model.Pet;
import com.signature.petclinic.model.PetType;
import com.signature.petclinic.services.OwnerService;
import com.signature.petclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

  private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/form";

  private final OwnerService ownerService;
  private final PetTypeService petTypeService;

  public PetController(OwnerService ownerService,
                       PetTypeService petTypeService) {
    this.ownerService = ownerService;
    this.petTypeService = petTypeService;
  }

  @InitBinder("owner")
  public void setDisallowedFields(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
  }

  @ModelAttribute("owner")
  public Owner getOwnerById(@PathVariable Long ownerId) {
    return this.ownerService.findById(ownerId);
  }

  @ModelAttribute("types")
  public Collection<PetType> findAllPetTypes() {
    return this.petTypeService.findAll();
  }

  @ModelAttribute("pet")
  public Pet findPetByOwnerAndId(@PathVariable Long ownerId,
                                 @PathVariable(required = false) Long petId) {
    return petId == null ? new Pet() : this.ownerService.findById(ownerId).getPet(petId);
  }
}