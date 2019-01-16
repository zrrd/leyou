package com.leyou.service.application;

import com.leyou.service.application.mapper.BrandMapper;
import com.leyou.service.pojo.domain.Brand;
import com.leyou.service.pojo.dto.application.EditBrandDto;
import com.leyou.service.pojo.dto.application.SaveBrandDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 品牌应用类
 *
 * @author shaoyijiong
 * @date 2018/11/30
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class BrandApplication {

  private final BrandMapper brandMapper;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  public BrandApplication(BrandMapper brandMapper) {
    this.brandMapper = brandMapper;
  }

  /**
   * 新增品牌
   */
  public void saveBrand(SaveBrandDto dto, List<Long> ids) {
    //新增品牌
    Brand brand = Brand.newInstForSave(dto.getName(), dto.getImage(), dto.getLetter());
    brand.saveBrand();
    //新增品牌和分类的中间表
    ids.forEach(cid -> brandMapper.insertCategoryBrand(cid, brand.getId()));
  }

  /**
   * 修改品牌
   */
  public void editBrand(EditBrandDto dto, List<Long> ids) {
    Brand brand = Brand.newInstForEdit(dto.getId(), dto.getName(), dto.getImage(), dto.getLetter());
    brand.updateById();
    brandMapper.deleteCategoryBrandByBid(dto.getId());
    ids.forEach(cid -> brandMapper.insertCategoryBrand(cid, brand.getId()));
  }

  /**
   * 删除品牌
   */
  public void deleteBrand(Long id) {
    Brand brand = Brand.newInstForDelete(id);
    brand.deleteById();
    brandMapper.deleteCategoryBrandByBid(id);
  }
}
