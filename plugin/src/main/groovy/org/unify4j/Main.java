package org.unify4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unify4j.common.*;
import org.unify4j.model.enums.TimezoneType;
import org.unify4j.model.request.IDecisionRequest;
import org.unify4j.model.request.TimeframeRequest;

import java.util.Date;

public class Main {
    protected static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println(TimeDecorator4j.now().withTimezone(TimezoneType.DefaultTimezoneManila).format());
        System.out.println(TimeBuilder4j.from(new Date()).atEndOfDay().firstDayOfMonth().buildFormatted());
        System.out.println(TimeExtensions4j.decorate(new Date()).toEndOfDay().format());
        System.out.println(TimeExtensions4j.decorate(new Date()).withTimezone(TimezoneType.DefaultTimezoneManila).format());
        TimeframeRequest as = new TimeframeRequest();
        as.setStart(new Date());
        as.setEnd(Time4j.addDays(new Date(), 10));
        System.out.println(as);
        IDecisionRequest decision = new IDecisionRequest();
        decision.setEnabled(true);
        decision.setValue(1234);
        decision.setLabel("Sample Decision Label");
        System.out.println(decision);
        String text = new Text4j()
                .appendCompact("TZ")
                .colon().timestamp()
                .endingColon("text")
                .curlyBracket("LOG")
                .verticalIf(true, 123, 3)
                .quotation("Sample Text")
                .toString();
        System.out.println(text);
    }
}