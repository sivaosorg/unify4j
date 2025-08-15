package org.unify4j.model.response;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings({"all"})
public class DateStatisticsResponse implements Serializable {
    private final Date min;
    private final Date max;
    private final Date average;
    private final Date median;
    private final int count;

    public DateStatisticsResponse() {
        this(null, null, null, null, 0);
    }

    public DateStatisticsResponse(Date min, Date max, Date average, Date median, int count) {
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
}
