package org.unify4j;

import org.junit.Test;
import org.unify4j.common.Date4j;
import org.unify4j.common.Time4j;
import org.unify4j.text.TimeFormatText;

import java.util.Date;

public class Date4jTest {

    @Test
    public void testParseDate() {
        Date date = Date4j.parse("2024/05/25 19:54:23");
        System.out.println(Time4j.since(date) + " * " + Time4j.format(date, TimeFormatText.SPREADSHEET_BIBLIOGRAPHY_EPOCH_PATTERN));
    }
}
