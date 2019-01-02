import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.leyou.common.service.LyItemServiceApplication;
import com.leyou.common.service.pojo.domain.Sku;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author shaoyijiong
 * @date 2019/1/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LyItemServiceApplication.class)
public class UpdateImage {

  @Test
  public void update() throws IOException {
    List<String> fileUrl = Lists.newArrayList();
    BufferedReader reader = new BufferedReader(
        new InputStreamReader(new FileInputStream(new File("D:\\code\\leyou\\leyou\\ly-upload\\img.txt"))));
    while (true) {
      String line = reader.readLine();
      if (!Strings.isNullOrEmpty(line)) {
        fileUrl.add(line);
      } else {
        break;
      }
    }
    System.out.println(fileUrl);
    Sku sku = new Sku();
    List<Sku> skus = sku.selectAll();
    int size = fileUrl.size();
    for (int i = 0; i < skus.size(); i++) {
      Sku sku1 = skus.get(i);
      sku1.updateImage(fileUrl.get(i));
      sku1.updateById();
    }
  }
}
