package com.signature.petclinic.bootstrap;

import com.signature.petclinic.model.Owner;
import com.signature.petclinic.model.Pet;
import com.signature.petclinic.model.PetType;
import com.signature.petclinic.model.Vet;
import com.signature.petclinic.services.OwnerService;
import com.signature.petclinic.services.PetTypeService;
import com.signature.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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