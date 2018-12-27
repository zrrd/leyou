package com.leyou.common.service.application.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyou.common.service.pojo.domain.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @author shaoyijiong
 * @date 2018/12/4
 */
public interface BrandMapper extends BaseMapper<Brand> {

  /**
   * 插入品牌 品类中间表.
   *
   * @param cid 分类id
   * @param bid 品牌id
   */
  @Insert("INSERT INTO tb_category_brand VALUES (#{cid},#{bid})")
  void insertCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);

  /**
   * 根据品牌id删除 品牌品类链接表
   *
   * @param bid 品牌id
   */
  @Delete("DELETE FROM tb_category_brand WHERE brand_id = #{bid}")
  void deleteCategoryBrandByBid(Long bid);
}
