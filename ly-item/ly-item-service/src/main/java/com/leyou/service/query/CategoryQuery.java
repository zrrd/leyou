package com.leyou.service.query;

import com.google.common.collect.ImmutableList;
import com.leyou.service.pojo.domain.Category;
import com.leyou.service.pojo.dto.query.CategoryQueryDto;
import com.leyou.service.query.mapper.CategoryQueryMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品类目相关查询.
 *
 * @author shaoyijiong
 * @date 2018/11/28
 */
@Service
public class CategoryQuery {

  private final CategoryQueryMapper mapper;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
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

  public List<CategoryQueryDto> queryCategoryByBid(Long bid) {
    return mapper.queryCategoryByBid(bid);
  }

  public List<String> queryCategoryName(List<Long> ids) {
    return mapper.queryCategoryNames(ids);
  }

  /**
   * 通过3级id 查找1级id
   */
  public List<Category> queryAllByCid3(Long id) {
    Category category = new Category(id);

    //三级目录
    Category category3 = category.selectById();
    //二级目录
    category.findParent(category3.getParentId());
    Category category2 = category.selectById();
    //一级目录
    category.findParent(category2.getParentId());
    Category category1 = category.selectById();
    return ImmutableList.of(category1, category2, category3);
  }
}
