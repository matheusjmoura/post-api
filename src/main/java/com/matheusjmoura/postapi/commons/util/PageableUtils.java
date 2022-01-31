package com.matheusjmoura.postapi.commons.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableUtils {

    public static Pageable sort(@NonNull Pageable pageable, @NonNull Sort.Direction direction, @NonNull String field) {
        if (!pageable.getSort().isSorted()) {
            return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, field));
        }

        return pageable;
    }

    public static Pageable sort(@NonNull Pageable pageable, @NonNull String field) {
        return sort(pageable, Sort.Direction.ASC, field);
    }
}
