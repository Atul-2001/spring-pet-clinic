package com.signature.petclinic.bootstrap;

import com.signature.petclinic.model.Owner;
import com.signature.petclinic.model.Vet;
import com.signature.petclinic.services.OwnerService;
import com.signature.petclinic.services.VetService;
import com.signature.petclinic.services.map.OwnerServiceMap;
import com.signature.petclinic.services.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader() {
        this.ownerService = new OwnerServiceMap();
        this.vetService = new VetServiceMap();
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setFirstName("Yash");
        owner.setFirstName("Chopra");

        ownerService.save(owner);

        owner.setId(2L);
        owner.setFirstName("Abhi");
        owner.setLastName("Singh");

        ownerService.save(owner);
        System.out.println("Owners list loaded successfully...");


        Vet vet = new Vet();
        vet.setId(1L);
        vet.setFirstName("Vivek");
        vet.setLastName("Dubey");

        vetService.save(vet);

        vet.setId(2L);
        vet.setFirstName("Shivang");
        vet.setLastName("Verma");

        vetService.save(vet);
        System.out.println("Vets list loaded successfully...");
    }
}