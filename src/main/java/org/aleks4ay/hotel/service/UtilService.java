package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.model.BaseEntity;

import java.util.ArrayList;
import java.util.List;


public final class UtilService<T> {

    List<T> doPagination(int positionOnPage, int page, List<? extends BaseEntity> entities) {
        int startPosition = positionOnPage * (page - 1);
        List<T> roomsAfterFilter = new ArrayList<>();

        if (entities.size() > startPosition) {
            for (int i = startPosition; i < startPosition + positionOnPage; i++) {
                if (i >= entities.size()) {
                    break;
                }
                roomsAfterFilter.add((T) entities.get(i));
            }
            return roomsAfterFilter;
        }
        return new ArrayList<>();
    }

    public static String filterFromListToString(List<String> filters) {
        StringBuilder sb = new StringBuilder();
        for (String f: filters) {
            if (sb.length() != 0) {
                sb.append(" and ");
            }
            sb.append(f);
        }
        return sb.append(";").toString();
    }
}
