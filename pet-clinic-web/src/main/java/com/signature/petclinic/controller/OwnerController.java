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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/owners")
public class OwnerController {

  private final OwnerService ownerService;

  public OwnerController(OwnerService ownerService) {
    this.ownerService = ownerService;
  }

  @InitBinder
  public void setDisallowedFields(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
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
}