package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.exception.APIException;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public class GlobalServiceHelper {
    private static Pageable getPageDetails(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortDetails = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return PageRequest.of(pageNumber, pageSize, sortDetails);
    }

    public static <T, ID> Page<T> getAllItems(
            Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, JpaRepository<T, ID> repository) {
        Pageable pageDetails = getPageDetails(pageNumber, pageSize, sortBy, sortOrder);
        return repository.findAll(pageDetails);
    }

    public static <T, CRITERIA> Page<T> getAllItemsBy(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder,
            BiFunction<CRITERIA, Pageable, Page<T>> findByCriteria,
            CRITERIA criteria) {
        Pageable pageDetails = getPageDetails(pageNumber, pageSize, sortBy, sortOrder);
        return findByCriteria.apply(criteria, pageDetails);
    }

    public static <T, TDTO> List<TDTO> getDTOContent(
            Page<T> page, Class<TDTO> dtoClass, ModelMapper modelMapper, String exceptionMessage) {
        return getDTOContent(page, dtoClass, modelMapper, exceptionMessage, t -> true);
    }

    public static <T, TDTO> List<TDTO> getDTOContent(
            Page<T> page, Class<TDTO> dtoClass, ModelMapper modelMapper, String exceptionMessage, Predicate<T> filter) {
        List<TDTO> list = page.getContent().stream()
                .filter(filter)
                .map(product -> modelMapper.map(product, dtoClass))
                .toList();
        if (list.isEmpty()) {
            throw new APIException(exceptionMessage);
        }
        return list;
    }
}
