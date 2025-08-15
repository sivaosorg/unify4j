package org.unify4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unify4j.common.Time4jBuilder;
import org.unify4j.common.Time4jDecorator;
import org.unify4j.common.Time4jExtensions;
import org.unify4j.model.enums.TimezoneType;

import java.util.Date;

public class Main {
    protected static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println(Time4jDecorator.now().withTimezone(TimezoneType.DefaultTimezoneManila).format());
        System.out.println(Time4jBuilder.from(new Date()).atEndOfDay().firstDayOfMonth().buildFormatted());
        System.out.println(Time4jExtensions.decorate(new Date()).toEndOfDay().format());
        System.out.println(Time4jExtensions.decorate(new Date()).withTimezone(TimezoneType.DefaultTimezoneManila).format());
    }
}
