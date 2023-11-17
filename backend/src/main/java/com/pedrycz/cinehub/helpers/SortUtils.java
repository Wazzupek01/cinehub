package com.pedrycz.cinehub.helpers;

import com.pedrycz.cinehub.controllers.SortParams;
import org.springframework.data.domain.Sort;

public class SortUtils {
    
    public static Sort getSort(SortParams sortParams) {
        if (sortParams.sortBy() != null) {
            if (sortParams.ascending()) {
                return Sort.by(sortParams.sortBy()).ascending();
            } else {
                return Sort.by(sortParams.sortBy()).descending();
            }
        }
        return Sort.unsorted();
    }
}
