package org.unify4j.model.enums;

import java.util.TimeZone;

// @formatter:off
@SuppressWarnings({"SpellCheckingInspection"})
public enum TimezoneType {
    DefaultTimezoneVietnam("Asia/Ho_Chi_Minh"),
    DefaultTimezoneNewYork("America/New_York"),
    DefaultTimezoneLondon("Europe/London"),
    DefaultTimezoneTokyo("Asia/Tokyo"),
    DefaultTimezoneSydney("Australia/Sydney"),
    DefaultTimezoneParis("Europe/Paris"),
    DefaultTimezoneMoscow("Europe/Moscow"),
    DefaultTimezoneLosAngeles("America/Los_Angeles"),
    DefaultTimezoneManila("Asia/Manila"),
    DefaultTimezoneKualaLumpur("Asia/Kuala_Lumpur"),
    DefaultTimezoneJakarta("Asia/Jakarta"),
    DefaultTimezoneYangon("Asia/Yangon"),
    DefaultTimezoneAuckland("Pacific/Auckland"),
    DefaultTimezoneBangkok("Asia/Bangkok"),
    DefaultTimezoneDelhi("Asia/Kolkata"), // Note: India standard time
    DefaultTimezoneDubai("Asia/Dubai"),
    DefaultTimezoneCairo("Africa/Cairo"),
    DefaultTimezoneAthens("Europe/Athens"),
    DefaultTimezoneRome("Europe/Rome"),
    DefaultTimezoneJohannesburg("Africa/Johannesburg"),
    DefaultTimezoneStockholm("Europe/Stockholm"),
    DefaultTimezoneOslo("Europe/Oslo"),
    DefaultTimezoneHelsinki("Europe/Helsinki"),
    DefaultTimezoneKiev("Europe/Kiev"),
    DefaultTimezoneBeijing("Asia/Shanghai"),
    DefaultTimezoneSingapore("Asia/Singapore"),
    DefaultTimezoneIslamabad("Asia/Karachi"),
    DefaultTimezoneColombo("Asia/Colombo"),
    DefaultTimezoneDhaka("Asia/Dhaka"),
    DefaultTimezoneKathmandu("Asia/Kathmandu"),
    DefaultTimezoneBrisbane("Australia/Brisbane"),
    DefaultTimezoneWellington("Pacific/Auckland"), // Note: Wellington uses the same as Auckland
    DefaultTimezonePortMoresby("Pacific/Port_Moresby"),
    DefaultTimezoneSuva("Pacific/Fiji");

    private final String timeZoneId;

    TimezoneType(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public static TimeZone getTimeZone(TimezoneType timezone) {
        return TimeZone.getTimeZone(timezone.getTimeZoneId());
    }
}
// @formatter:on
