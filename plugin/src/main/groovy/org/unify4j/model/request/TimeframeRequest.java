package org.unify4j.model.request;

import org.unify4j.common.StringBuilder4j;
import org.unify4j.common.Time4j;
import org.unify4j.common.TimeExtensions4j;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings({"all"})
public class TimeframeRequest implements Serializable {
    private Date start;
    private Date end;

    public TimeframeRequest() {
        super();
    }

    public TimeframeRequest(Date start, Date end) {
        super();
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
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
        return TimeExtensions4j.getDateRange(start, end);
    }

    @Override
    public String toString() {
        return StringBuilder4j.create()
                .append("Timeframe")
                .appendColon()
                .appendSpace()
                .appendLBrace()
                .appendSpace()
                .appendIf(start != null, "start: ")
                .appendIf(start != null, Time4j.format(start))
                .appendIf(start != null && end != null, ", ")
                .appendIf(end != null, "end: ")
                .appendIf(end != null, Time4j.format(end))
                .appendSpace()
                .appendRBrace()
                .build();
    }
}
