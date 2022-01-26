package com.signature.petclinic.controller;

import com.signature.petclinic.model.Owner;
import com.signature.petclinic.services.jpa.OwnerJpaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

  @Mock
  public OwnerJpaService ownerService;

  @InjectMocks
  public OwnerController ownerController;

  public MockMvc mockMvc;

  public Set<Owner> owners;

  @BeforeEach
  void setUp() {
    owners = new HashSet<>();
    owners.add(Owner.builder().id(1L).build());
    owners.add(Owner.builder().id(2L).build());

    mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
  }

  @Test
  void index() throws Exception {
    when(ownerService.findAll()).thenReturn(owners);

    mockMvc.perform(get("/owners"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/index"))
            .andExpect(model().attribute("owners", hasSize(2)));
  }

  @Test
  void index2() throws Exception {
    when(ownerService.findAll()).thenReturn(owners);

    mockMvc.perform(get("/owners/"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/index"))
            .andExpect(model().attribute("owners", hasSize(2)));
  }

  @Test
  void index3() throws Exception {
    when(ownerService.findAll()).thenReturn(owners);

    mockMvc.perform(get("/owners/index"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/index"))
            .andExpect(model().attribute("owners", hasSize(2)));
  }

  @Test
  void index4() throws Exception {
    when(ownerService.findAll()).thenReturn(owners);

    mockMvc.perform(get("/owners/index.html"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/index"))
            .andExpect(model().attribute("owners", hasSize(2)));
  }

  @Test
  void getOwners() throws Exception {
    mockMvc.perform(get("/owners/find"))
            .andExpect(status().isOk())
            .andExpect(view().name("not_implemented"));

    verifyNoInteractions(ownerService);
  }
}