package org.unify4j.model.response;

import org.unify4j.common.StringBuilder4j;
import org.unify4j.common.Time4j;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings({"all"})
public class DateStatsResponse implements Serializable {
    private final Date min;
    private final Date max;
    private final Date average;
    private final Date median;
    private final int count;

    public DateStatsResponse() {
        this(null, null, null, null, 0);
    }

    public DateStatsResponse(Date min, Date max, Date average, Date median, int count) {
        this.min = min;
        this.max = max;
        this.average = average;
        this.median = median;
        this.count = count;
    }

    public Date getMin() {
        return min;
    }

    public Date getMax() {
        return max;
    }

    public Date getAverage() {
        return average;
    }

    public Date getMedian() {
        return median;
    }

    public int getCount() {
        return count;
    }

    public long getRangeInDays() {
        if (min != null && max != null) {
            return (max.getTime() - min.getTime()) / (1000 * 60 * 60 * 24);
        }
        return 0;
    }

    @Override
    public String toString() {
        return StringBuilder4j.create()
                .append("Date Stats")
                .appendColon()
                .appendSpace()
                .appendLBrace()
                .appendSpace()
                .appendIf(min != null, "min: ")
                .appendIf(min != null, Time4j.format(min))
                .appendIf(min != null && max != null, ", ")
                .appendIf(max != null, "max: ")
                .appendIf(max != null, Time4j.format(max))
                .appendIf(max != null && average != null, ", ")
                .appendIf(average != null, "average: ")
                .appendIf(average != null, Time4j.format(average))
                .appendIf(average != null && median != null, ", ")
                .appendIf(median != null, "median: ")
                .appendIf(median != null, Time4j.format(median))
                .appendIf(median != null && count > 0, ", ")
                .appendIf(count >= 0, "count: ")
                .appendIf(count >= 0, count)
                .appendSpace()
                .appendRBrace()
                .build();
    }
}
