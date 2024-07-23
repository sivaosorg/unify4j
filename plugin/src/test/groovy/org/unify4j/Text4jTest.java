package org.unify4j;

import org.junit.Test;
import org.unify4j.common.Collection4j;
import org.unify4j.common.Text4j;
import org.unify4j.model.c.Pair;

public class Text4jTest {

    @Test
    public void testAppend() {
        String val = new Text4j()
                .append("POWER", 3)
                .vertical("1111")
                .append("GST %d", new Object[]{100})
                .parenthesis("@gmail")
                .quotation("abc")
                .comma()
                .hyphenMinus(123)
                .bracket(Collection4j.mapOf(new Pair<>(1, 2), new Pair<>(3, 4)))
                .curlyBracket(Collection4j.mapOf(new Pair<>(1, 2), new Pair<>(3, 4)))
                .line(3)
                .timestamp()
                .equal(1997)
                .toString();
        System.out.println(val);
    }
}
