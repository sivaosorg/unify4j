package org.unify4j.model.filter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.unify4j.common.Time4j;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DateTimeFilter implements Serializable {
    public DateTimeFilter() {
        super();
    }

    public DateTimeFilter(Date from, Date to) {
        super();
        this.from = from;
        this.to = to;
    }

    private Date from;
    private Date to;

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("DateTime filtering { from: '%s', to: '%s' }", Time4j.format(from), Time4j.format(to));
    }
}
