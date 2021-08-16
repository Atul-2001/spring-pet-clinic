package com.signature.petclinic.services;

import com.signature.petclinic.model.Owner;

public interface OwnerService extends CurdService<Owner, Long> {

    Owner findByLastName(String lastName);
}