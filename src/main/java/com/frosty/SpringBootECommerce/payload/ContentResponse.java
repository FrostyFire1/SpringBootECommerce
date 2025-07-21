package com.frosty.SpringBootECommerce.payload;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

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
