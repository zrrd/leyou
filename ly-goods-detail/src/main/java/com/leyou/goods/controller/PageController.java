package com.leyou.goods.controller;

import com.leyou.goods.service.GoodsService;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author shaoyijiong
 * @date 2019/1/7
 */
@Slf4j
@Controller
public class PageController {

  private final GoodsService goodsService;

  @Autowired
  public PageController(GoodsService goodsService) {
    this.goodsService = goodsService;
  }


  /**
   * 请求商品详情
   */
  @GetMapping("item/{id}.html")
  public String toItemPage(Model model, @PathVariable("id") Long supId) {
    //准备模型数据
    Map<String, Object> map = goodsService.loadModel(supId);
    //放入模型
    model.addAllAttributes(map);
    // 页面静态化
    goodsService.createHtml(map, supId);
    return "item";
  }
}
