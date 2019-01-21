import com.leyou.common.base.response.PageResult;
import com.leyou.search.LySearchApplication;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.service.BuildService;
import com.leyou.service.pojo.dto.query.SpuQueryDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author shaoyijiong
 * @date 2019/1/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySearchApplication.class)
public class CreateIndex {

  @Autowired
  private GoodsClient goodsClient;

  @Autowired
  private BuildService buildService;

  @Test
  public void createTest() {
    PageResult<SpuQueryDto> result = goodsClient
        .querySpuByPage(null, null, 1, 1000, null, null);
    List<Long> collect = result.getItems().stream().map(SpuQueryDto::getId)
        .collect(Collectors.toList());
    List<Goods> goods = new ArrayList<>();
    collect.forEach(a -> goods.add(buildService.build(a)));
    buildService.insertIndex(goods);
  }
}
