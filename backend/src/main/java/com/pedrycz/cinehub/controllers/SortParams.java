package com.pedrycz.cinehub.controllers;

public record SortParams (
    int pageNum,
    String sortBy,
    boolean ascending
) {}
