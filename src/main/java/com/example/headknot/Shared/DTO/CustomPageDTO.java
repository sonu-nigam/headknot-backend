package com.example.headknot.Shared.DTO;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class CustomPageDTO<T> {
    List<T> content;
    int pageNumber;
    int pageSize;
    long totalElements;
    int totalPages;
    boolean hasNext;

    public CustomPageDTO(Page<T> page) {
        this.content = page.getContent();
        this.pageNumber = page.getPageable().getPageNumber();
        this.pageSize = page.getPageable().getPageSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.hasNext = page.hasNext();
    }
}