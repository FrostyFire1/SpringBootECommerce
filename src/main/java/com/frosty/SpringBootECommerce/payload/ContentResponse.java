package com.frosty.SpringBootECommerce.payload;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ContentResponse<T> {
    private List<T> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private Boolean lastPage;
}
