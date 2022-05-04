package com.signature.petclinic.formatter;

import com.signature.petclinic.model.PetType;
import com.signature.petclinic.services.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

  private final PetTypeService petTypeService;

  public PetTypeFormatter(PetTypeService petTypeService) {
    this.petTypeService = petTypeService;
  }

  @Override
  public String print(PetType petType, Locale locale) {
    return petType.getName();
  }

  @Override
  public PetType parse(String text, Locale locale) throws ParseException {
    for (PetType type : petTypeService.findAll()) {
      if (type.getName().equals(text)) {
        return type;
      }
    }
    throw new ParseException("Pet type not found: " + text, 0);
  }
}