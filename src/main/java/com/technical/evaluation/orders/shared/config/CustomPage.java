package com.technical.evaluation.orders.shared.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
public class CustomPage<T> {
    private long totalCount;
    private PagingInfo pagingInfo;
    private List<T> content;

    public CustomPage(Page<?> page, List<T> content) {
        totalCount = page.getTotalElements();
        pagingInfo = new PagingInfo(page);
        this.content = content;
    }

    public CustomPage(CustomPage<?> page, List<T> content) {
        totalCount = page.getTotalCount();
        pagingInfo = page.getPagingInfo();
        this.content = content;
    }
}
