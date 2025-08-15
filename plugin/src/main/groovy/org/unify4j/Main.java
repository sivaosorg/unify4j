package org.unify4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unify4j.common.TimeBuilder4j;
import org.unify4j.common.TimeDecorator4j;
import org.unify4j.common.TimeExtensions4j;
import org.unify4j.model.enums.TimezoneType;

import java.util.Date;

public class Main {
    protected static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println(TimeDecorator4j.now().withTimezone(TimezoneType.DefaultTimezoneManila).format());
        System.out.println(TimeBuilder4j.from(new Date()).atEndOfDay().firstDayOfMonth().buildFormatted());
        System.out.println(TimeExtensions4j.decorate(new Date()).toEndOfDay().format());
        System.out.println(TimeExtensions4j.decorate(new Date()).withTimezone(TimezoneType.DefaultTimezoneManila).format());
    }
}
