package com.example.accessingdatajpa.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 */
@Slf4j
public class Output {
    public static void list(Iterable<?> categories, String title) {

        StringBuilder message = new StringBuilder(String.format("==== %s ====\n", title));

        categories.forEach(category -> message.append(category.toString().replace(", ", ",\n\t")).append("\n"));

        log.info(message.toString());
    }
}
