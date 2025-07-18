package com.frosty.SpringBootECommerce.payload;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Deprecated
public class ProductResponse {
    private List<ProductDTO> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private Boolean lastPage;
}
