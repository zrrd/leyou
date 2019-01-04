package com.leyou.search.pojo;

import com.leyou.common.base.response.PageResult;
import com.leyou.common.service.pojo.domain.Brand;
import com.leyou.common.service.pojo.domain.Category;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shaoyijiong
 * @date 2019/1/3
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchResult extends PageResult<Goods> {

  private List<Category> categories;
  private List<Brand> brands;
  private List<Map<String, Object>> specs;

  @SuppressWarnings("CheckStyle")
  public SearchResult(Long total, Long totalPage, List<Goods> items,
      List<Category> categories, List<Brand> brands,
      List<Map<String, Object>> specs) {
    super(total, totalPage, items);
    this.categories = categories;
    this.brands = brands;
    this.specs = specs;
  }
}
