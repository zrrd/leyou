package com.leyou.common.service.query.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyou.common.service.pojo.domain.Spu;
import com.leyou.common.service.pojo.dto.query.SpuDto;
import com.leyou.common.service.pojo.dto.query.SpuQueryDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * spu查询mapper.
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
public interface SpuQueryMapper extends BaseMapper<Spu> {

  /**
   * 分页查询spu.
   *
   * @param page 分页信息
   * @param key 搜索字段
   * @param saleable 是否出售
   * @return 分页信息
   */
  IPage<SpuQueryDto> querySpuByPage(Page page, @Param("key") String key,
      @Param("saleable") Boolean saleable);

  /**
   * 查询spu
   *
   * @param id spu id
   * @return spu实体
   */
  @Select("SELECT * FROM tb_spu WHERE id = #{id} AND valid = 1")
  SpuDto querySpuById(Long id);
}
