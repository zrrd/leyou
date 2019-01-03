package com.leyou.search.pojo;

import com.leyou.common.base.response.PageResult;
import com.leyou.common.service.pojo.domain.Brand;
import com.leyou.common.service.pojo.domain.Category;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shaoyijiong
 * @date 2019/1/3
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SearchResult extends PageResult<Goods> {

  private List<Category> categories;
  private List<Brand> brands;
  private List<Map<String, String>> specs;

}
