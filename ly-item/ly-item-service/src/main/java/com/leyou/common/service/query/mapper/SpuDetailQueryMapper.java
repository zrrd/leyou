package com.leyou.common.service.query.mapper;

import com.leyou.common.service.pojo.dto.query.SpuDetailEditQueryDto;
import org.apache.ibatis.annotations.Select;

/**
 * SpuDetail查询mapper
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
public interface SpuDetailQueryMapper {

  /**
   * 查询spu详情
   *
   * @param id spu id
   * @return spu详情
   */
  @Select("SELECT * FROM tb_spu_detail WHERE spu_id = #{id}")
  SpuDetailEditQueryDto querySpuDetailById(Long id);
}
