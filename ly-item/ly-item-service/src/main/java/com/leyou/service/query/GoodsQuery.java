package com.leyou.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.leyou.service.pojo.domain.Sku;
import com.leyou.service.pojo.dto.query.SkuQueryDto;
import com.leyou.service.pojo.dto.query.SpuDetailEditQueryDto;
import com.leyou.service.pojo.dto.query.SpuDto;
import com.leyou.service.pojo.dto.query.SpuQueryDto;
import com.leyou.service.query.mapper.SkuQueryMapper;
import com.leyou.service.query.mapper.SpuDetailQueryMapper;
import com.leyou.service.query.mapper.SpuQueryMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品查询接口
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
@Service
public class GoodsQuery {

  private final SkuQueryMapper skuMapper;

  private final SpuDetailQueryMapper spuDetailMapper;

  private final SpuQueryMapper spuMapper;

  @SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "CheckStyle"})
  @Autowired
  public GoodsQuery(SpuQueryMapper spuMapper, SkuQueryMapper skuMapper,
      SpuDetailQueryMapper spuDetailMapper) {
    this.spuMapper = spuMapper;
    this.skuMapper = skuMapper;
    this.spuDetailMapper = spuDetailMapper;
  }

  public IPage<SpuQueryDto> querySpuByPage(Page page, String key, Boolean saleable) {
    key = Strings.isNullOrEmpty(key) ? null : "%" + key + "%";
    return spuMapper.querySpuByPage(page, key, saleable);
  }

  public SpuDetailEditQueryDto querySpuDetail(Long id) {
    return spuDetailMapper.querySpuDetailById(id);
  }

  public List<SkuQueryDto> querySkuBySpuId(Long spuId) {
    return skuMapper.querySkuBySpuId(spuId);
  }

  public SpuDto querySpuById(Long id) {
    return spuMapper.querySpuById(id);
  }

  public Sku querySkuById(Long id) {
    return skuMapper.selectById(id);
  }
}
