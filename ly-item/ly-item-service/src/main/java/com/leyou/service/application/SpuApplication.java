package com.leyou.service.application;

import com.leyou.service.application.dto.SaveSpuDto;
import com.leyou.service.pojo.domain.Spu;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @author shaoyijiong
 * @date 2018/12/14
 */
@Service
public class SpuApplication {

  /**
   * 保存spu.
   */
  public Long saveSpu(SaveSpuDto dto) {
    Spu spu = Spu.newInstForSave(dto.getBrandId(), dto.getCid1(), dto.getCid2(), dto.getCid3(),
        dto.getTitle(), dto.getSubTitle());
    spu.insert();
    return spu.getId();
  }
}
