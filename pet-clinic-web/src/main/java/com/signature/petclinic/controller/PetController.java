package com.signature.petclinic.controller;

import com.signature.petclinic.model.Owner;
import com.signature.petclinic.model.Pet;
import com.signature.petclinic.model.PetType;
import com.signature.petclinic.services.OwnerService;
import com.signature.petclinic.services.PetService;
import com.signature.petclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

  private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/form";

  private final PetService petService;
  private final OwnerService ownerService;
  private final PetTypeService petTypeService;

  public PetController(PetService petService,
                       OwnerService ownerService,
                       PetTypeService petTypeService) {
    this.petService = petService;
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
                                 @PathVariable(name = "petId", required = false) Long petId) {
    if (petId == null) {
      return new Pet();
    } else {
      Owner owner = ownerService.findById(ownerId);
      if (owner != null) {
        Pet pet = owner.getPet(petId);
        if (pet != null) {
          return pet;
        } else {
          return petService.findById(petId);
        }
      }
      return null;
    }
  }

  @GetMapping("/pets/add")
  public String getAddPetForm(Owner owner, Model model) {
    Pet pet = new Pet();
    owner.addPet(pet);
    model.addAttribute("pet", pet);
    return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
  }

  @PostMapping("/pets/add")
  public String postAddPetForm(Owner owner, @Valid Pet pet,
                               BindingResult result, Model model) {
    if (StringUtils.hasLength(pet.getName()) && pet.isNew()
      && owner.getPet(pet.getName(), true) != null) {
      result.rejectValue("name", "duplicate", "pet already exists!");
    }

    owner.addPet(pet);

    if (result.hasErrors()) {
      model.addAttribute("pet", pet);
      return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    } else {
      this.petService.save(pet);
      return "redirect:/owners/{ownerId}";
    }
  }

  @GetMapping("/pets/{petId}/edit")
  public String getUpdatePetForm(Owner owner, Pet pet, Model model) {
    model.addAttribute("pet", pet);
    model.addAttribute("owner", owner);
    return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
  }

  @PostMapping("/pets/{petId}/edit")
  public String postUpdatePetForm(Owner owner, @Valid Pet pet,
                                  BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("pet", pet);
      return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    } else {
      owner.addPet(pet);
      this.petService.save(pet);
      return "redirect:/owners/{ownerId}";
    }
  }
}