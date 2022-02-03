package br.com.alg.giraofoodapi.core.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;
import java.util.stream.Collectors;

public class PageableTranslator {
    public static Pageable tranlate(Pageable pageable, Map<String, String> fieldsMapping) {

        var orders = pageable.getSort().stream()
                .filter(order -> fieldsMapping.containsKey(order.getProperty()))
                .map(order ->  new Sort.Order(order.getDirection(),
                fieldsMapping.get(order.getProperty()))).collect(Collectors.toList());

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }
}