package com.signature.petclinic.services.map;

import com.signature.petclinic.model.Owner;
import com.signature.petclinic.services.CurdService;

import java.util.Set;

public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements CurdService<Owner, Long> {
    
    @Override
    public Owner save(Owner entity) {
        return super.save(entity.getId(), entity);
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Owner entity) {
        super.delete(entity);
    }
}