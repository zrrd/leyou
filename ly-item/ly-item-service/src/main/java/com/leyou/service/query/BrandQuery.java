package com.leyou.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.leyou.service.pojo.domain.Brand;
import com.leyou.service.pojo.dto.query.BrandQueryDto;
import com.leyou.service.pojo.dto.query.SelectBrandDto;
import com.leyou.service.query.mapper.BrandQueryMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品查询
 *
 * @author shaoyijiong
 * @date 2018/11/29
 */
@Service
public class BrandQuery {

  private final BrandQueryMapper mapper;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  public BrandQuery(BrandQueryMapper mapper) {
    this.mapper = mapper;
  }

  /**
   * 分页查询品牌
   */
  public IPage<BrandQueryDto> queryBrandByPage(Page page, String key) {
    if (!Strings.isNullOrEmpty(key)) {
      key = "%" + key + "%";
    }
    return mapper.queryBrandByPage(page, key);
  }

  public List<SelectBrandDto> queryByCategoryId(Long cid) {
    return mapper.queryByCategoryId(cid);
  }

  public String queryBrandName(Long id) {
    return mapper.queryBrandName(id);
  }

  /**
   * 根据id查询品牌
   */
  public List<Brand> queryBrandByIds(List<Long> ids) {
    QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
    queryWrapper.in("id", ids);
    return new Brand().selectList(queryWrapper);
  }
}
