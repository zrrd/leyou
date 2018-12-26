package com.leyou.service.query.mapper;

import com.leyou.service.pojo.dto.query.SkuQueryDto;
import java.util.List;
import org.apache.ibatis.annotations.Select;

/**
 * sku查询mapper.
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
public interface SkuQueryMapper {

  @Select("SELECT id,`enable`,title,images,price,own_spec,indexes,stock "
      + "FROM tb_sku INNER JOIN tb_stock ON id = sku_id WHERE spu_id = #{spuId}")
  List<SkuQueryDto> querySkuBySpuId(Long spuId);
}
