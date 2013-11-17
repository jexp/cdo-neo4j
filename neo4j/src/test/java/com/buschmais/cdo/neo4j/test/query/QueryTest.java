package com.buschmais.cdo.neo4j.test.query;

import com.buschmais.cdo.api.CdoException;
import com.buschmais.cdo.api.CdoManager;
import com.buschmais.cdo.api.Query;
import com.buschmais.cdo.neo4j.test.AbstractCdoManagerTest;
import com.buschmais.cdo.neo4j.test.query.composite.A;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class QueryTest extends AbstractCdoManagerTest {

    @Override
    protected Class<?>[] getTypes() {
        return new Class<?>[]{A.class};
    }

    @Test
    public void index() {
        CdoManager cdoManager = getCdoManager();
        cdoManager.begin();
        A a1 = cdoManager.create(A.class);
        a1.setValue("A1");
        A a2_1 = cdoManager.create(A.class);
        a2_1.setValue("A2");
        A a2_2 = cdoManager.create(A.class);
        a2_2.setValue("A2");
        cdoManager.commit();
        cdoManager.begin();
        A a = cdoManager.find(A.class, "A1").getSingleResult();
        assertThat(a, equalTo(a1));
        try {
            cdoManager.find(A.class, "A2").getSingleResult();
            fail("Expecting a " + CdoException.class.getName());
        } catch (CdoException e) {

        }
        cdoManager.commit();
    }

    @Test
    public void query() {
        CdoManager cdoManager = getCdoManager();
        cdoManager.begin();
        A a1 = cdoManager.create(A.class);
        a1.setValue("A1");
        A a2_1 = cdoManager.create(A.class);
        a2_1.setValue("A2");
        A a2_2 = cdoManager.create(A.class);
        a2_2.setValue("A2");
        cdoManager.commit();
        cdoManager.begin();
        Query.Result queryResult = cdoManager.createQuery("match (a:A) where a.Value={a} return a").withParameter("a", "A1").execute();
        A a = queryResult.getRows().getSingleResult().get("a", A.class);
        assertThat(a, equalTo(a1));
        queryResult = cdoManager.createQuery("match (a:A) where a.Value={a} return a").withParameter("a", "A2").execute();
        try {
            queryResult.getRows().getSingleResult().get("a", A.class);
            fail("Expecting a " + CdoException.class.getName());
        } catch (CdoException e) {
        }
        cdoManager.commit();
    }
}