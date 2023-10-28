package com.pedrycz.cinehub.helpers;

import com.pedrycz.cinehub.controllers.SortParams;
import org.springframework.data.domain.Sort;

public class SortUtils {
    public static Sort getSort(SortParams sortParams) {
        if (sortParams.getSortBy() != null) {
            if (sortParams.isAscending()) {
                return Sort.by(sortParams.getSortBy()).ascending();
            } else {
                return Sort.by(sortParams.getSortBy()).descending();
            }
        }
        return Sort.unsorted();
    }
}
