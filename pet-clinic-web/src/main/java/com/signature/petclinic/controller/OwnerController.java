package com.signature.petclinic.controller;

import com.signature.petclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping({"", "/", "/index", "/index.html"})
  public String index(Model model) {
    model.addAttribute("owners", ownerService.findAll());
    return "owners/index";
  }

  @GetMapping("/{ownerId}")
  public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
    ModelAndView modelAndView = new ModelAndView("owners/detail");
    modelAndView.addObject(this.ownerService.findById(ownerId));
    return modelAndView;
  }

  @GetMapping("/find")
  public String getOwners() {
    return "not_implemented";
  }
}