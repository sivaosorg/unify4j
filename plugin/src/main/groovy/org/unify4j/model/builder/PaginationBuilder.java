package org.unify4j.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.unify4j.model.response.PaginationResponse;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginationBuilder implements Serializable {
    private int page;
    private int perPage;
    private int totalPages;
    private int totalItems;
    private boolean last;

    public PaginationBuilder setPage(int page) {
        this.page = page;
        return this;
    }

    public PaginationBuilder setPerPage(int perPage) {
        this.perPage = perPage;
        return this;
    }

    public PaginationBuilder setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public PaginationBuilder setTotalItems(int totalItems) {
        this.totalItems = totalItems;
        return this;
    }

    public PaginationBuilder setLast(boolean last) {
        this.last = last;
        return this;
    }

    public PaginationResponse build() {
        PaginationResponse e = new PaginationResponse();
        e.setPage(page);
        e.setPerPage(perPage);
        e.setTotalPages(totalPages);
        e.setTotalItems(totalItems);
        e.setLast(last);
        return e;
    }
}
