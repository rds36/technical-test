package com.dans.multipro.technicaltest.exception;

import java.util.UUID;

public class EntityNotFoundException  extends RuntimeException{

    public EntityNotFoundException(Class<?> entity){
        super(entity.getSimpleName().toLowerCase() + " not found.");
    }
}
