package com.leyou.common.service.application;

import com.leyou.common.service.pojo.domain.Sku;
import com.leyou.common.service.pojo.dto.application.SaveSkuDto;
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
}
