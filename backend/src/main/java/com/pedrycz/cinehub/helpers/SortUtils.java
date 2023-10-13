package com.pedrycz.cinehub.helpers;

import com.pedrycz.cinehub.controllers.GetParams;
import org.springframework.data.domain.Sort;

public class SortUtils {
    public static Sort getSort(GetParams getParams) {
        if (getParams.getSortBy() != null) {
            if (getParams.isAscending()) {
                return Sort.by(getParams.getSortBy()).ascending();
            } else {
                return Sort.by(getParams.getSortBy()).descending();
            }
        }
        return Sort.unsorted();
    }
}
