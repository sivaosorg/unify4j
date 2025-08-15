package org.unify4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unify4j.common.Time4jDecorator;
import org.unify4j.model.enums.TimezoneType;

public class Main {
    protected static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println(Time4jDecorator.now().addDays(10).withTimezone(TimezoneType.DefaultTimezoneManila).format("HH:mm:ss"));
    }
}
