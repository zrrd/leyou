package com.leyou.goods;

import com.leyou.goods.controller.PageController;
import com.leyou.goods.service.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LyGoodsWebApplicationTests {

  @Autowired
  PageController pageController;

  @Autowired
  GoodsService goodsService;
  @Value("${static.html.url}")
  private String htmlUrl;
  @Test
  public void contextLoads() {
    System.out.println(htmlUrl);
  }

}

