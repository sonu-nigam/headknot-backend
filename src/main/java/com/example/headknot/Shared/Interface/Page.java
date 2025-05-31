package com.example.headknot.Shared.Interface;

import org.springframework.data.domain.Slice;

import java.util.List;

public interface Page<T> extends Slice<T> {

    int getPageNumber();
    int getPageSize();
    int getTotalPages();

    @Override
    boolean hasNext();

    @Override
    boolean hasContent();

    @Override
    boolean hasPrevious();

    @Override
    List<T> getContent();
}
