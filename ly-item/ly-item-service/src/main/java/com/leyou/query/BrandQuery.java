package com.leyou.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.leyou.query.dto.BrandQueryDto;
import com.leyou.query.mapper.BrandQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品查询.
 *
 * @author shaoyijiong
 * @date 2018/11/29
 */
@Service
public class BrandQuery {

  private final BrandQueryMapper mapper;

  @Autowired
  public BrandQuery(BrandQueryMapper mapper) {
    this.mapper = mapper;
  }

  public IPage<BrandQueryDto> queryBrandByPage(Page page, String key) {
    key = "%" + key + "%";
    return mapper.queryBrandByPage(page, key);
  }
}
