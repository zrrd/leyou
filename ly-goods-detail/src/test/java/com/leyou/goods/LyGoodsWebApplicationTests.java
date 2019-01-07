package com.leyou.goods;

import com.leyou.goods.controller.PageController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LyGoodsWebApplicationTests {

  @Autowired
  PageController pageController;

  @Test
  public void contextLoads() {
    //pageController.getSpecGroup(76L);
  }

}

