package com.buschmais.cdo.neo4j.test.mapping.composite;

import com.buschmais.cdo.neo4j.api.annotation.Label;

@Label(value = "D", usingIndexOf = A.class)
public interface D extends A {
}
