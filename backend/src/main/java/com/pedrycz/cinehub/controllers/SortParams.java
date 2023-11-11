package com.pedrycz.cinehub.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SortParams {
    int pageNum;
    String sortBy;
    boolean ascending;
}
