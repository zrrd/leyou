package com.leyou.service.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 产品规格.
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
@Setter(AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@TableName("tb_specification")
public class Specifications extends Model<Specifications> {

  /**
   * 分类id.
   */
  Long categoryId;
  /**
   * 规格参数.  jsons
   */
  String specifications;

  public static Specifications newInstForSave(Long categoryId, String specifications) {
    return new Specifications(categoryId, specifications);
  }

  /**
   * 根据品类更新.
   */
  public void updateByCid() {
    UpdateWrapper<Specifications> uw = new UpdateWrapper<>();
    uw.eq("category_id", categoryId);
    update(uw);
  }
}
