package com.leyou.service.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.leyou.service.query.dto.SpuQueryDto;
import com.leyou.service.query.mapper.SpuQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品查询接口.
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
@Service
public class GoodsQuery {

  private final SpuQueryMapper spuMapper;

  @Autowired
  public GoodsQuery(SpuQueryMapper spuMapper) {
    this.spuMapper = spuMapper;
  }

  public IPage<SpuQueryDto> querySpuByPage(Page page, String key, Boolean saleable) {
    key = Strings.isNullOrEmpty(key) ? null : "%" + key + "%";
    return spuMapper.querySpuByPage(page, key, saleable);
  }
}
