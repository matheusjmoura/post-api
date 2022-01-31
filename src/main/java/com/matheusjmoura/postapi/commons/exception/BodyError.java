package com.matheusjmoura.postapi.commons.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BodyError {

    private final String title;
    private final List<String> details = new ArrayList<>();

    public BodyError(String title) {
        this.title = title;
    }

    public void addDetail(String detail) {
        details.add(detail);
    }
}
