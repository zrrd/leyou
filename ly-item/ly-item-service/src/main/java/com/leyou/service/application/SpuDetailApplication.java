package com.leyou.service.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.common.utils.JsonUtils;
import com.leyou.service.pojo.domain.SpuDetail;
import com.leyou.service.pojo.dto.application.SaveSpuDetailDto;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sound.midi.SoundbankResource;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @author shaoyijiong
 * @date 2018/12/14
 */
@Service
public class SpuDetailApplication {

  /**
   * 保存SaveSpuDetail
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

  public void a() {
    SpuDetail aa = new SpuDetail();
    List<SpuDetail> spuDetails = aa.selectAll();
    spuDetails.forEach(s -> {
      String specifications = s.getSpecifications();
      List<Specification> maps = JsonUtils
          .nativeRead(specifications, new TypeReference<List<Specification>>() {
          });
      List<Specification> collect = maps.stream().filter(a -> "主芯片".equals(a.getGroup()))
          .collect(Collectors.toList());
      try {
        Object o = collect.get(0).getParams().stream().filter(a -> "CPU频率".equals(a.get("k")))
            .collect(Collectors.toList()).get(0).get("v");
        if (o instanceof String) {
          System.out.println(s.getSpuId());
        }
      } catch (Exception e) {
        System.out.println("不存在" + s.getSpuId());
      }

    });
  }


  @Data
  static class Specification {

    private String group;
    private List<Map<String, Object>> params;
    private Boolean empty;
  }

}
