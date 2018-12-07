package com.leyou.service.application;

import com.leyou.service.application.dto.SaveBrandDto;
import com.leyou.service.application.mapper.BrandMapper;
import com.leyou.service.pojo.domain.Brand;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 品牌应用类.
 *
 * @author 邵益炯
 * @date 2018/11/30
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class BrandApplication {

  private final BrandMapper brandMapper;

  @Autowired
  public BrandApplication(BrandMapper brandMapper) {
    this.brandMapper = brandMapper;
  }

  /**
   * 新增品牌.
   */
  public void saveBrand(SaveBrandDto dto, List<Long> ids) {
    //新增品牌
    Brand brand = Brand.newInstForSave(dto.getName(), dto.getImage(), dto.getLetter());
    brand.saveBrand();
    //新增品牌和分类的中间表
    ids.forEach(cid -> brandMapper.insertCategoryBrand(cid, brand.getId()));
  }
}
