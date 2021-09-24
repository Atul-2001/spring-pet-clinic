package com.signature.petclinic.bootstrap;

import com.signature.petclinic.model.*;
import com.signature.petclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDog = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCat = petTypeService.save(cat);

        Owner owner = new Owner();
        owner.setFirstName("Yash");
        owner.setLastName("Chopra");
        owner.setAddress("civil lines");
        owner.setCity("Prayagraj");
        owner.setTelephone("6792754278");

        Pet yashPet = new Pet();
        yashPet.setPetType(savedDog);
        yashPet.setName("Cobara");
        yashPet.setBirthdate(LocalDate.now());
        yashPet.setOwner(owner);

        owner.getPets().add(yashPet);
        ownerService.save(owner);

        Visit visit = new Visit();
        visit.setPet(yashPet);
        visit.setDate(LocalDate.now());
        visit.setDescription("Sneeze dog");
        visitService.save(visit);

        owner = new Owner();
        owner.setFirstName("Abhi");
        owner.setLastName("Singh");
        owner.setAddress("lanka");
        owner.setCity("Varanasi");
        owner.setTelephone("4793823950");

        Pet abhiCat = new Pet();
        abhiCat.setPetType(savedCat);
        abhiCat.setName("Meow");
        abhiCat.setBirthdate(LocalDate.now());
        abhiCat.setOwner(owner);

        owner.getPets().add(abhiCat);
        ownerService.save(owner);

        visit = new Visit();
        visit.setPet(abhiCat);
        visit.setDate(LocalDate.now());
        visit.setDescription("Sneeze cat");
        visitService.save(visit);
        System.out.println("Owners list loaded successfully...");

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        radiology = specialityService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");
        surgery = specialityService.save(surgery);

        Speciality dentistry = new Speciality();
        dentistry.setDescription("Dentistry");
        dentistry = specialityService.save(dentistry);

        Vet vet = new Vet();
        vet.setFirstName("Vivek");
        vet.setLastName("Dubey");
        vet.getSpecialities().add(radiology);
        vet.getSpecialities().add(surgery);

        vetService.save(vet);

        vet = new Vet();
        vet.setFirstName("Shivang");
        vet.setLastName("Verma");
        vet.getSpecialities().add(dentistry);

        vetService.save(vet);
        System.out.println("Vets list loaded successfully...");
    }
}