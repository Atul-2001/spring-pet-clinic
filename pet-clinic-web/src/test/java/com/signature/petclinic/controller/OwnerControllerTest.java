package com.signature.petclinic.controller;

import com.signature.petclinic.model.Owner;
import com.signature.petclinic.services.jpa.OwnerJpaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    mockMvc = MockMvcBuilders.standaloneSetup(ownerController)
      .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
      .build();
  }

  @Test
  void getSearchForm() throws Exception {
    mockMvc.perform(get("/owners/search"))
      .andExpect(status().isOk())
      .andExpect(view().name("owners/search"));

    verifyNoInteractions(ownerService);
  }

  @Test
  void searchOwnersReturnOne() throws Exception {
    when(ownerService.searchAllByLastNameLike(any(), any()))
      .thenReturn(new PageImpl<>(List.of(Owner.builder().id(1L).build())));

    mockMvc.perform(get("/owners"))
      .andExpect(status().is3xxRedirection())
      .andExpect(view().name("redirect:/owners/1"));
  }

  @Test
  void searchOwnersReturnMany() throws Exception {
    when(ownerService.searchAllByLastNameLike(any(), any()))
      .thenReturn(new PageImpl<>(new ArrayList<>(owners)));

    mockMvc.perform(get("/owners"))
      .andExpect(status().isOk())
      .andExpect(view().name("owners/index"))
      .andExpect(model().attribute("searchResult",
        hasProperty("content", hasSize(2))));
  }

  @Test
  void searchOwnersReturnEmpty() throws Exception {
    when(ownerService.searchAllByLastNameLike(any(), any()))
      .thenReturn(new PageImpl<>(new ArrayList<>()));

    mockMvc.perform(get("/owners"))
      .andExpect(status().isOk())
      .andExpect(view().name("owners/search"));
  }

  @Test
  void searchOwnersFormEmptyReturnAll() throws Exception {
    when(ownerService.searchAllByLastNameLike(anyString(), any()))
      .thenReturn(new PageImpl<>(new ArrayList<>(owners)));

    mockMvc.perform(get("/owners")
        .param("lastName", ""))
      .andExpect(status().isOk())
      .andExpect(view().name("owners/index"))
      .andExpect(model().attribute("searchResult",
        hasProperty("content", hasSize(2))));
  }


  @Test
  void showOwner() throws Exception {
    when(ownerService.findById(anyLong()))
      .thenReturn(Owner.builder().id(1L).build());

    mockMvc.perform(get("/owners/1"))
      .andExpect(status().isOk())
      .andExpect(view().name("owners/detail"))
      .andExpect(model().attribute("owner",
        hasProperty("id", is(1L))));
  }

  @Test
  void getCreateOwnerForm() throws Exception {
    mockMvc.perform(get("/owners/add"))
      .andExpect(status().isOk())
      .andExpect(view().name("owners/form"))
      .andExpect(model().attributeExists("owner"));

    verifyNoInteractions(ownerService);
  }

  @Test
  void postCreateOwnerForm() throws Exception {
    when(ownerService.save(ArgumentMatchers.any()))
      .thenReturn(Owner.builder().id(1L).build());

    mockMvc.perform(post("/owners/add"))
      .andExpect(status().is3xxRedirection())
      .andExpect(view().name("redirect:/owners/1"))
      .andExpect(model().attributeExists("owner"));

    verify(ownerService).save(ArgumentMatchers.any());
  }

  @Test
  void getUpdateOwnerForm() throws Exception {
    when(ownerService.findById(anyLong()))
      .thenReturn(Owner.builder().id(1L).build());

    mockMvc.perform(get("/owners/1/edit"))
      .andExpect(status().isOk())
      .andExpect(view().name("owners/form"))
      .andExpect(model().attributeExists("owner"));
  }

  @Test
  void postUpdateOwnerForm() throws Exception {
    when(ownerService.save(ArgumentMatchers.any()))
      .thenReturn(Owner.builder().id(1L).build());

    mockMvc.perform(post("/owners/1/edit"))
      .andExpect(status().is3xxRedirection())
      .andExpect(view().name("redirect:/owners/{ownerId}"))
      .andExpect(model().attributeExists("owner"));

    verify(ownerService).save(ArgumentMatchers.any());
  }
}