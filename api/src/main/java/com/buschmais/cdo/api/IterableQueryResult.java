package com.buschmais.cdo.api;

import java.io.Closeable;
import java.util.List;

public interface IterableQueryResult<T> extends IterableResult<T>, AutoCloseable, Closeable {

    public List<String> getColumns();

    public interface CompositeRowObject extends CompositeObject {
        <C> C get(String name, Class<C> type);
    }
}