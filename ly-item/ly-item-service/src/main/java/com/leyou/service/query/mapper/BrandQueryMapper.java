package com.leyou.service.query.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyou.service.pojo.dto.query.BrandQueryDto;
import com.leyou.service.pojo.dto.query.SelectBrandDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 品牌相关mapper
 *
 * @author shaoyijiong
 * @date 2018/11/28
 */
public interface BrandQueryMapper {

  IPage<BrandQueryDto> queryBrandByPage(Page page, @Param("key") String key);

  @Select("SELECT id,`name` FROM tb_brand b INNER JOIN tb_category_brand c "
      + "ON b.id = c.brand_id  AND c.category_id = #{cid}")
  List<SelectBrandDto> queryByCategoryId(Long cid);

  @Select("SELECT `name` FROM tb_brand WHERE id = #{id}")
  String queryBrandName(Long id);
}
