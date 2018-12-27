package com.leyou.common.service.application;

import com.leyou.common.service.pojo.domain.Specifications;
import org.springframework.stereotype.Service;

/**
 * 规格参数应用
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
@Service
public class SpecificationsApplication {


  public void saveSpecifications(Long cid, String spec) {
    Specifications specifications = Specifications.newInstForSave(cid, spec);
    specifications.insert();
  }

  public void updateSpecifications(Long cid, String spec) {
    Specifications specifications = Specifications.newInstForSave(cid, spec);
    specifications.updateByCid();
  }
}
