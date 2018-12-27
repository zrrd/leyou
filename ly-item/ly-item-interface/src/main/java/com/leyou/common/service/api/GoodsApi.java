package com.leyou.common.service.api;

import com.leyou.common.base.response.PageResult;
import com.leyou.common.service.mvc.req.SpuQueryPageReq;
import com.leyou.common.service.pojo.domain.Spu;
import com.leyou.common.service.pojo.dto.query.SkuQueryDto;
import com.leyou.common.service.pojo.dto.query.SpuDetailEditQueryDto;
import com.leyou.common.service.pojo.dto.query.SpuQueryDto;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * goods 调用
 *
 * @author shaoyijiong
 * @date 2018/12/26
 */
@SuppressWarnings("unused")
public interface GoodsApi {

  /**
   * 分页查找
   *
   * @param key 搜索字段
   * @param saleable 是否在售
   * @param page 页码
   * @param rows 每页行数
   * @param sortBy 排序字段
   * @param desc 升序倒序
   * @return spu
   */
  @RequestMapping(value = "spu/page", method = RequestMethod.GET)
  PageResult<SpuQueryDto> querySpuByPage(@RequestParam("key") String key,
      @RequestParam("saleable") Boolean saleable, @RequestParam("page") Integer page,
      @RequestParam("rows") Integer rows, @RequestParam("sortBy") String sortBy,
      @RequestParam("desc") Boolean desc);

  /**
   * 查找spu详情
   *
   * @param id spuId
   * @return 详情
   */
  @RequestMapping(value = "spu/detail/{id}", method = RequestMethod.GET)
  SpuDetailEditQueryDto querySpuDetailById(@PathVariable("id") Long id);

  /**
   * 根据spu查找sku
   *
   * @param id spuId
   * @return sku 列表
   */
  @RequestMapping(value = "sku/list", method = RequestMethod.GET)
  List<SkuQueryDto> querySkuBySpuId(@RequestParam("id") Long id);

  /**
   * 根据id查询spu
   *
   * @param id spuId
   * @return spu
   */
  @RequestMapping(value = "spu/{id}", method = RequestMethod.GET)
  Spu querySpuById(@PathVariable("id") Long id);
}
