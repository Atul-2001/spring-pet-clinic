package com.signature.petclinic.controller;

import com.signature.petclinic.model.Owner;
import com.signature.petclinic.model.Pet;
import com.signature.petclinic.model.Visit;
import com.signature.petclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}/visits")
public class VisitController {

  private static final String CREATE_OR_UPDATE_VISIT_VIEW = "visit/form";

  private final OwnerService ownerService;

  public VisitController(OwnerService ownerService) {
    this.ownerService = ownerService;
  }

  @InitBinder
  public void setDisallowedFields(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
  }

  @ModelAttribute
  public Owner getOwnerById(@PathVariable Long ownerId) {
    return ownerService.findById(ownerId);
  }

  @ModelAttribute
  public Pet getPetById(@PathVariable Long ownerId,
                        @PathVariable Long petId) {
    Owner owner = ownerService.findById(ownerId);
    return owner.getPet(petId);
  }

  @ModelAttribute("visit")
  public Visit loadPetWithVisit(@PathVariable Long ownerId,
                                @PathVariable Long petId,
                                Model model) {
    Owner owner = ownerService.findById(ownerId);
    Pet pet = owner.getPet(petId);
    model.addAttribute("owner", owner);
    model.addAttribute("pet", pet);
    Visit visit = new Visit();
    pet.addVisit(visit);
    return visit;
  }

  @GetMapping("/add")
  public String getNewVisitForm(@PathVariable String ownerId,
                                @PathVariable String petId) {
    return CREATE_OR_UPDATE_VISIT_VIEW;
  }

  @PostMapping("/add")
  public String postNewVisitForm(@ModelAttribute Owner owner,
                                 @PathVariable Long ownerId,
                                 @PathVariable Long petId,
                                 BindingResult result,
                                 @Valid Visit visit) {
    if (result.hasErrors()) {
      return CREATE_OR_UPDATE_VISIT_VIEW;
    } else {
      owner.addVisit(petId, visit);
      this.ownerService.save(owner);
      return "redirect:/owners/{ownerId}";
    }
  }
}