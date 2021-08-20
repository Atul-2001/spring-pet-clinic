package com.signature.petclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vets")
public class VetController {

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index() {
        return "vets/index";
    }

}