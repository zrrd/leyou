package com.leyou.service.application;

import com.leyou.service.pojo.domain.SpuDetail;
import com.leyou.service.pojo.dto.application.SaveSpuDetailDto;
import org.springframework.stereotype.Service;

/**
 * @author shaoyijiong
 * @date 2018/12/14
 */
@Service
public class SpuDetailApplication {

  /**
   * 保存SaveSpuDetail.
   */
  public void saveSpuDetail(SaveSpuDetailDto dto) {
    SpuDetail spuDetail = SpuDetail
        .newInstForSave(dto.getSpuId(), dto.getDescription(), dto.getSpecifications(),
            dto.getSpecTemplate(), dto.getPackingList(), dto.getAfterService());
    spuDetail.insert();
  }

  /**
   * 更新
   */
  public void updateSpuDatail(SaveSpuDetailDto dto) {
    SpuDetail spuDetail = SpuDetail
        .newInstForSave(dto.getSpuId(), dto.getDescription(), dto.getSpecifications(),
            dto.getSpecTemplate(), dto.getPackingList(), dto.getAfterService());
    spuDetail.updateById();
  }
}
