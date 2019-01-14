package com.leyou.service.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 抽象商品额外内容
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@TableName("tb_spu_detail")
public class SpuDetail extends Model<SpuDetail> {

  @TableId
  Long spuId;
  /**
   * 商品描述信息
   */
  String description;
  /**
   * 全部规格参数数据
   */
  String specifications;
  /**
   * 特有规格参数及可选值信息，json格式
   */
  String specTemplate;
  /**
   * 包装清单
   */
  String packingList;
  /**
   * 售后服务
   */
  String afterService;

  public static SpuDetail newInstForSave(Long spuId, String description, String specifications,
      String specTemplate, String packingList, String afterService) {
    return new SpuDetail(spuId, description, specifications, specTemplate, packingList,
        afterService);
  }
}
