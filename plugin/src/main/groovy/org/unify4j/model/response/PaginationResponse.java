package org.unify4j.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginationResponse implements Serializable {
    public PaginationResponse() {
        super();
    }

    @JsonProperty("page")
    private int page;
    @JsonProperty("per_page")
    private int perPage;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("total_items")
    private int totalItems;
    @JsonProperty("is_last")
    private boolean last;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return String.format("Pagination response { page: %d, per_page: %d, total_pages: %d, total_items: %d, is_last: %s }", page, perPage, totalPages, totalItems, last);
    }
}
