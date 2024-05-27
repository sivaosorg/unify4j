package org.unify4j;

import org.junit.Test;
import org.unify4j.model.builder.HttpWrapBuilder;

public class HttpWrapBuilderTest {

    @Test
    public void testBuilder() {
        HttpWrapBuilder<String> builder = new HttpWrapBuilder<>();
        builder.customFields("name", 112).customFields("age", 28);
        System.out.println(builder.build());
    }
}
