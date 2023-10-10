package com.pedrycz.cinehub.helpers;

import com.pedrycz.cinehub.controllers.GetParams;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class SortUtils {
    public static void setSort(GetParams getParams, PageRequest pageRequest) {
        if (getParams.getSortBy() != null) {
            if (getParams.isAscending()) {
                pageRequest.withSort(Sort.by(getParams.getSortBy()).ascending());
            } else {
                pageRequest.withSort(Sort.by(getParams.getSortBy()).descending());
            }
        }
    }
}
