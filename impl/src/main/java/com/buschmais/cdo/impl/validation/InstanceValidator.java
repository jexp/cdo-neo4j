package com.buschmais.cdo.impl.validation;

import com.buschmais.cdo.impl.cache.TransactionalCache;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class InstanceValidator {

    private final ValidatorFactory validatorFactory;

    private final TransactionalCache<?>[] caches;

    public InstanceValidator(ValidatorFactory validatorFactory, TransactionalCache<?>... caches) {
        this.validatorFactory = validatorFactory;
        this.caches = caches;
    }

    public Set<ConstraintViolation<Object>> validate() {
        if (validatorFactory == null) {
            return Collections.emptySet();
        }
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Object>> violations = new HashSet<>();
        for (TransactionalCache<?> cache : caches) {
            for (Object instance : new ArrayList<>(cache.writtenInstances())) {
                violations.addAll(validator.validate(instance));
            }
        }
        return violations;
    }
}
