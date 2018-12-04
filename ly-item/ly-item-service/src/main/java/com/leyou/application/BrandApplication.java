package com.leyou.application;

import com.leyou.application.dto.SaveBrandDto;
import com.leyou.item.pojo.domain.Brand;
import java.util.List;
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

  /**
   * 新增品牌.
   */
  public void saveBrand(SaveBrandDto dto, List<Long> ids) {
    //新增品牌
    Brand brand = Brand.newInstForSave(dto.getName(), dto.getImage(), dto.getLetter());
    brand.saveBrand();
    //新增品牌和分类的中间表
  }
}
