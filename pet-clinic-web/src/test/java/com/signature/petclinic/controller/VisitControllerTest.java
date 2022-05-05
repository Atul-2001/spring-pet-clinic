package com.signature.petclinic.controller;

import com.signature.petclinic.model.Owner;
import com.signature.petclinic.model.Pet;
import com.signature.petclinic.model.PetType;
import com.signature.petclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

  private final UriTemplate visitsUriTemplate = new UriTemplate("/owners/{ownerId}/pets/{petId}/visits/add");
  private static final String PETS_CREATE_OR_UPDATE_VISIT_FORM = "visit/form";
  private static final String YET_ANOTHER_VISIT_DESCRIPTION = "yet another visit";
  private static final String REDIRECT_OWNERS_1 = "redirect:/owners/{ownerId}";

  private URI visitsUri;
  private MockMvc mockMvc;

  @Mock
  public OwnerService ownerService;

  @InjectMocks
  public VisitController visitController;

  private final Map<String, String> uriVariables = new HashMap<>();

  @BeforeEach
  void setUp() {
    Long petId = 1L;
    Long ownerId = 1L;

    Owner owner = Owner.builder().id(ownerId).firstName("Joe").lastName("Doe").build();
    Pet pet = Pet.builder().id(petId).name("Cutie").visits(new HashSet<>())
      .birthdate(LocalDate.of(2018,11,13)).build();
    PetType petType = PetType.builder().name("Dog").build();

    owner.setPets(Set.of(pet));
    pet.setPetType(petType);
    pet.setOwner(owner);

    when(ownerService.findById(anyLong())).thenReturn(owner);

    uriVariables.clear();
    uriVariables.put("ownerId", ownerId.toString());
    uriVariables.put("petId", petId.toString());
    visitsUri = visitsUriTemplate.expand(uriVariables);

    mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
  }

  @Test
  void getNewVisitForm() throws Exception {
    mockMvc.perform(get(visitsUri)).andExpect(status().isOk())
      .andExpect(view().name(PETS_CREATE_OR_UPDATE_VISIT_FORM));
  }


  @Test
  void postNewVisitForm() throws Exception {
    mockMvc.perform(post(visitsUri)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("date", "2018-11-11")
        .param("description", YET_ANOTHER_VISIT_DESCRIPTION))
      .andExpect(status().is3xxRedirection())
      .andExpect(view().name(REDIRECT_OWNERS_1))
      .andExpect(model().attributeExists("visit"));
  }
}