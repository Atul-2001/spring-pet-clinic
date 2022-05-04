package com.signature.petclinic.controller;

import com.signature.petclinic.model.Owner;
import com.signature.petclinic.services.OwnerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/owners")
public class OwnerController {

  private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/form";

  private final OwnerService ownerService;

  public OwnerController(OwnerService ownerService) {
    this.ownerService = ownerService;
  }

  @InitBinder
  public void setDisallowedFields(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
  }

  @GetMapping("/add")
  public String getCreateOwnerForm(Model model) {
    model.addAttribute("owner", new Owner());
    return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
  }

  @PostMapping("/add")
  public String postCreateOwnerForm(@Valid Owner owner,
                                    BindingResult result) {
    if (result.hasErrors()) {
      return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    } else {
      Owner savedOwner = this.ownerService.save(owner);
      return String.format("redirect:/owners/%d", savedOwner.getId());
    }
  }

  @GetMapping("/search")
  public String getSearchForm(Model model) {
    model.addAttribute("owner", new Owner());
    return "owners/search";
  }

  @GetMapping({"", "/", "/index", "/index.html"})
  public String searchOwners(Owner owner, BindingResult result,
                             Model model, Pageable pageable) {
    Page<Owner> searchResult = ownerService.searchAllByLastNameLike(owner.getLastName(), pageable);

    if (searchResult.getTotalElements() == 1L) {
      return String.format("redirect:/owners/%d", searchResult.getContent().get(0).getId());
    } else if (searchResult.isEmpty()) {
      result.rejectValue("lastName", "notFound", "No owner found!");
      return "owners/search";
    } else {
      model.addAttribute("searchResult", searchResult);
      return "owners/index";
    }
  }

  @GetMapping("/{ownerId}")
  public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
    ModelAndView modelAndView = new ModelAndView("owners/detail");
    modelAndView.addObject(this.ownerService.findById(ownerId));
    return modelAndView;
  }

  @GetMapping("/{ownerId}/edit")
  public String getUpdateOwnerForm(@PathVariable("ownerId") Long ownerId, Model model) {
    model.addAttribute(ownerService.findById(ownerId));
    return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
  }

  @PostMapping("/{ownerId}/edit")
  public String postUpdateOwnerForm(@Valid Owner owner, BindingResult result,
                                    @PathVariable("ownerId") Long ownerId) {
    if (result.hasErrors()) {
      return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    } else {
      owner.setId(ownerId);
      this.ownerService.save(owner);
      return "redirect:/owners/{ownerId}";
    }
  }
}