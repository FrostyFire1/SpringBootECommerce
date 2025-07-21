package com.frosty.SpringBootECommerce.payload;

import java.util.List;
import lombok.*;

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
