package com.leyou.service.query.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * .
 *
 * @author shaoyijiong
 * @date 2018/12/11
 */
public interface SpecificationsQueryMapper {

  /**
   * 根据品类查找规格消息.
   *
   * @param cid 品类id
   * @return 规格消息json数据
   */
  @Select("SELECT specifications FROM tb_specification WHERE category_id = #{cid}")
  String querySpecByCid(Long cid);
}
