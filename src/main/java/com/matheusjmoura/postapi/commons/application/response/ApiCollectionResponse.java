package com.matheusjmoura.postapi.commons.application.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiCollectionResponse<T> {

    private boolean hasNext;
    private int pageSize;
    private int pageNumber;
    private long totalElements;
    private Collection<T> items = new ArrayList<>();

    public static <T> ApiCollectionResponse<T> from(Page<T> page) {
        return new ApiCollectionResponse<>(
            !page.isLast(),
            page.getSize(),
            page.getNumber(),
            page.getTotalElements(),
            page.getContent());
    }

    public boolean hasNext() {
        return hasNext;
    }

}