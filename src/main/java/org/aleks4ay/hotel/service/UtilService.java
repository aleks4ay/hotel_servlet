package org.aleks4ay.hotel.service;

import java.util.List;
import java.util.stream.Collectors;

public final class UtilService<T> {

    List<T> doPagination(int positionOnPage, int page, List<T> entities) {
        return entities.stream()
                .skip(positionOnPage * (page - 1))
                .limit(positionOnPage)
                .collect(Collectors.toList());
    }
}
