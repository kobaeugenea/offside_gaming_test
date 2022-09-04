package ru.kobaevgenii.testtask.offsidegaming.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class PageResponse<T> {
    private final List<T> items;
    private final int totalPages;
}
