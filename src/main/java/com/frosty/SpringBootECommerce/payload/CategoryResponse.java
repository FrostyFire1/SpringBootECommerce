package com.frosty.SpringBootECommerce.payload;

import com.frosty.SpringBootECommerce.model.Category;
import lombok.*;

import java.util.List;
@Builder
@Getter
@Deprecated
public class CategoryResponse {
    private List<CategoryDTO> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private Boolean lastPage;
}
