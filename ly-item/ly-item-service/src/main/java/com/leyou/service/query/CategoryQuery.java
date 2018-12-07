package com.leyou.service.query;

import com.leyou.service.query.dto.CategoryQueryDto;
import com.leyou.service.query.mapper.CategoryQueryMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品类目相关查询.
 *
 * @author 邵益炯
 * @date 2018/11/28
 */
@Service
public class CategoryQuery {

  private final CategoryQueryMapper mapper;

  @Autowired
  public CategoryQuery(CategoryQueryMapper mapper) {
    this.mapper = mapper;
  }

  /**
   * 通过父类目 id 找到所以子类目 id.
   */
  public List<CategoryQueryDto> queryCategoryByPid(Long parentId) {
    return mapper.queryCategoryByPid(parentId);
  }
}
