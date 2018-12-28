package com.leyou.search;

import com.google.common.collect.ImmutableList;
import com.leyou.common.base.response.PageResult;
import com.leyou.common.service.mvc.req.SpuQueryPageReq;
import com.leyou.common.service.pojo.dto.query.SpuQueryDto;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.search.service.SearchService;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LeyouSearchApplicationTests {

  @Resource
  private CategoryClient categoryClient;

  @Resource
  private GoodsClient goodsClient;

  @Autowired
  private GoodsRepository goodsRepository;
  @Autowired
  private ElasticsearchTemplate template;
  @Autowired
  private SearchService searchService;

  @Test
  public void contextLoads() {
  }

  @Test
  public void testClient() {
    List<String> strings = categoryClient.queryNameByIds(ImmutableList.of(1L, 2L, 3L));
    System.out.println(strings);
  }

  @Test
  public void createIndex() {
    //创建索引
    template.createIndex(Goods.class);
    //创建映射
    template.putMapping(Goods.class);
  }

  /**
   * 将数据插入索引库
   */
  @Test
  public void insertIndex() {
    PageResult<SpuQueryDto> spuPage = goodsClient.querySpuByPage(null, true, 1, 1000, null, null);
    List<Long> list = spuPage.getItems().stream().map(SpuQueryDto::getId)
        .collect(Collectors.toList());
    List<Goods> goods = list.stream().map(a -> searchService.buildGoods(a))
        .collect(Collectors.toList());
    goodsRepository.saveAll(goods);

  }
}

