package com.leyou.service.application;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.leyou.service.pojo.domain.Sku;
import com.leyou.service.pojo.dto.application.SaveSkuDto;
import org.springframework.stereotype.Service;

/**
 * @author shaoyijiong
 * @date 2018/12/14
 */
@Service
public class SkuApplication {

  /**
   * 保存sku.
   */
  public Long saveSku(SaveSkuDto dto) {
    Sku sku = Sku.newInstForSave(dto.getSpuId(), dto.getEnable(), dto.getPrice(), dto.getImages(),
        dto.getIndexes(), dto.getOwnSpec(), dto.getTitle());
    sku.insert();
    return sku.getId();
  }

  /**
   * 删除sku
   */
  public void deleteSku(Long spuId) {
    Sku sku = new Sku();
    UpdateWrapper<Sku> uw = new UpdateWrapper<>();
    uw.eq("spu_id", spuId);
    sku.delete(uw);
  }
}
