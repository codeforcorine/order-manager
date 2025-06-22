package com.technical.evaluation.orders.shared.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingInfo {
    private int size;
    private int pageCount;
    private int currentPage;
    private boolean hasPrevious;
    private boolean hasNext;

    public PagingInfo(Page<?> page) {
        this.size = page.getSize();
        this.pageCount = page.getTotalPages();
        this.currentPage = page.getNumber() + 1;
        this.hasNext = page.hasNext();
        this.hasPrevious = page.hasPrevious();
    }
}