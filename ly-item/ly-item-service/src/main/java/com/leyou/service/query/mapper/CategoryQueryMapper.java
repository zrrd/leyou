package com.leyou.service.query.mapper;


import com.leyou.service.query.dto.CategoryQueryDto;
import java.util.List;
import org.apache.ibatis.annotations.Select;

/**
 * 商品类目相关mapper.
 *
 * @author shaoyijiong
 * @date 2018/11/28
 */
public interface CategoryQueryMapper {

  /**
   * 查询品类.
   *
   * @param parentId 父id
   * @return 品类列表
   */
  @Select("SELECT * FROM tb_category WHERE parent_id = #{parentId}")
  List<CategoryQueryDto> queryCategoryByPid(Long parentId);
}