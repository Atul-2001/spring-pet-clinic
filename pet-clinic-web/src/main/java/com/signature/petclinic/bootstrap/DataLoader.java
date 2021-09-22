package com.signature.petclinic.bootstrap;

import com.signature.petclinic.model.Owner;
import com.signature.petclinic.model.PetType;
import com.signature.petclinic.model.Vet;
import com.signature.petclinic.services.OwnerService;
import com.signature.petclinic.services.PetTypeService;
import com.signature.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
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

        ownerService.save(owner);

        owner = new Owner();
        owner.setFirstName("Abhi");
        owner.setLastName("Singh");

        ownerService.save(owner);
        System.out.println("Owners list loaded successfully...");


        Vet vet = new Vet();
        vet.setFirstName("Vivek");
        vet.setLastName("Dubey");

        vetService.save(vet);

        vet = new Vet();
        vet.setFirstName("Shivang");
        vet.setLastName("Verma");

        vetService.save(vet);
        System.out.println("Vets list loaded successfully...");
    }
}