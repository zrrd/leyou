package com.leyou.service.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.leyou.service.pojo.dto.query.SkuQueryDto;
import com.leyou.service.pojo.dto.query.SpuDetailEditQueryDto;
import com.leyou.service.pojo.dto.query.SpuQueryDto;
import com.leyou.service.query.mapper.SkuQueryMapper;
import com.leyou.service.query.mapper.SpuDetailQueryMapper;
import com.leyou.service.query.mapper.SpuQueryMapper;
import java.util.List;
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

  private final SkuQueryMapper skuQueryMapper;

  private final SpuDetailQueryMapper spuDetailQueryMapper;

  private final SpuQueryMapper spuMapper;

  @SuppressWarnings("CheckStyle")
  @Autowired
  public GoodsQuery(SpuQueryMapper spuMapper, SkuQueryMapper skuQueryMapper,
      SpuDetailQueryMapper spuDetailQueryMapper) {
    this.spuMapper = spuMapper;
    this.skuQueryMapper = skuQueryMapper;
    this.spuDetailQueryMapper = spuDetailQueryMapper;
  }

  public IPage<SpuQueryDto> querySpuByPage(Page page, String key, Boolean saleable) {
    key = Strings.isNullOrEmpty(key) ? null : "%" + key + "%";
    return spuMapper.querySpuByPage(page, key, saleable);
  }

  public SpuDetailEditQueryDto querySpuDetail(Long id) {
    return spuDetailQueryMapper.querySpuDetailById(id);
  }

  public List<SkuQueryDto> querySkuBySpuId(Long spuId) {
    return skuQueryMapper.querySkuBySpuId(spuId);
  }
}
