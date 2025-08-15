package org.unify4j.model.request;

import org.unify4j.common.Time4j;
import org.unify4j.common.Time4jExtensions;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings({"all"})
public class DateRangeRequest implements Serializable {
    private final Date start;
    private final Date end;

    public DateRangeRequest(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public boolean contains(Date date) {
        return Time4j.isWithinRange(date, start, end);
    }

    public long getDurationInDays() {
        if (start != null && end != null) {
            return (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24);
        }
        return 0;
    }

    public List<Date> getAllDates() {
        return Time4jExtensions.getDateRange(start, end);
    }
}
