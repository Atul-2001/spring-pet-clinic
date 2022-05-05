package com.signature.petclinic.controller;

import com.signature.petclinic.services.VetService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vets")
public class VetController {

  private final VetService vetService;

  public VetController(VetService vetService) {
    this.vetService = vetService;
  }

  @GetMapping({"", "/", "/index", "/index.html"})
  public String index(Model model, Pageable pageable) {
    model.addAttribute("searchResult", vetService.findAll(pageable));
    return "vets/index";
  }

}