package com.leyou.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyou.item.pojo.Category;
import com.leyou.query.mapper.CategoryMapper;
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

  private final CategoryMapper mapper;

  @Autowired
  public CategoryQuery(CategoryMapper mapper) {
    this.mapper = mapper;
  }

  /**
   * 通过父类目 id 找到所以子类目 id.
   */
  public List<Category> queryCategoryByPid(Long parentId) {
    QueryWrapper<Category> qw = new QueryWrapper<>();
    qw.eq("parent_id", parentId);
    return mapper.selectList(qw);
  }
}
