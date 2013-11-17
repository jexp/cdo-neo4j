package com.buschmais.cdo.neo4j.test.mapping.composite;

import com.buschmais.cdo.api.IterableQueryResult;
import com.buschmais.cdo.neo4j.api.annotation.Label;
import com.buschmais.cdo.neo4j.api.annotation.Relation;
import com.buschmais.cdo.neo4j.api.annotation.ResultOf;

import java.util.List;

import static com.buschmais.cdo.neo4j.api.annotation.ResultOf.Parameter;

@Label("E")
public interface E {

    @Relation("RELATED_TO")
    List<F> getRelatedTo();

    @ResultOf(query = ByName.class, usingThisAs = "e")
    IterableQueryResult<ByName> getByName(@Parameter("value") String value);

}