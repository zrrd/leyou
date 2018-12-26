package com.leyou.service.query.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyou.service.pojo.dto.query.SpuQueryDto;
import org.apache.ibatis.annotations.Param;

/**
 * spu查询mapper.
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
public interface SpuQueryMapper {

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

}
