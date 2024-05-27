package org.unify4j;

import org.junit.Test;
import org.unify4j.common.Numeric4j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class Numeric4jTest {
    @Test
    public void testCurrencyWithLocale() {
        assertEquals("$1,234.56", Numeric4j.currency(1234.56, Locale.US));
    }

    @Test
    public void testCurrencyWithLanguageAndCountry() {
        assertEquals("$1,234.56", Numeric4j.currency(1234.56, "en", "US"));
    }

    @Test
    public void testCurrencyWithGrouping() {
        assertEquals("$1,234.56", Numeric4j.currency(1234.56, Locale.US, true));
        assertEquals("$1234.56", Numeric4j.currency(1234.56, Locale.US, false));
    }

    @Test
    public void testCurrencyWithPattern() {
        assertEquals("1,234.5600", Numeric4j.currency(1234.56, 4, "#,##0.0000", Locale.US));
    }

    @Test
    public void testCurrencyWithPrecision() {
        assertEquals("$1,234.5600", Numeric4j.currency(1234.56, 4, Locale.US));
    }

    @Test
    public void testMinimumLong() {
        assertEquals(1L, Numeric4j.minimum(3L, 5L, 1L, 4L));
    }

    @Test
    public void testMaximumLong() {
        assertEquals(5L, Numeric4j.maximum(3L, 5L, 1L, 4L));
    }

    @Test
    public void testMinimumBigInteger() {
        assertEquals(BigInteger.ONE, Numeric4j.minimum(BigInteger.valueOf(3), BigInteger.valueOf(5), BigInteger.ONE, BigInteger.valueOf(4)));
    }

    @Test
    public void testMaximumBigInteger() {
        assertEquals(BigInteger.valueOf(5), Numeric4j.maximum(BigInteger.valueOf(3), BigInteger.valueOf(5), BigInteger.ONE, BigInteger.valueOf(4)));
    }

    @Test
    public void testMinimumBigDecimal() {
        assertEquals(BigDecimal.ONE, Numeric4j.minimum(BigDecimal.valueOf(3), BigDecimal.valueOf(5), BigDecimal.ONE, BigDecimal.valueOf(4)));
    }

    @Test
    public void testMaximumBigDecimal() {
        assertEquals(BigDecimal.valueOf(5), Numeric4j.maximum(BigDecimal.valueOf(3), BigDecimal.valueOf(5), BigDecimal.ONE, BigDecimal.valueOf(4)));
    }

    @Test
    public void testTryNumeric() {
        assertEquals(123L, Numeric4j.tryNumeric("123"));
        assertEquals(-123L, Numeric4j.tryNumeric("-123"));
        assertEquals(123.45, Numeric4j.tryNumeric("123.45"));
        assertEquals(new BigInteger("12345678901234567890"), Numeric4j.tryNumeric("12345678901234567890"));
        assertEquals(new BigDecimal("1234567890.1234567890"), Numeric4j.tryNumeric("1234567890.1234567890"));
    }
}
