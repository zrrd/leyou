import com.leyou.service.LyItemServiceApplication;
import com.leyou.service.application.SpuDetailApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author shaoyijiong
 * @date 2019/1/23
 */
@SpringBootTest(classes = LyItemServiceApplication.class)
@RunWith(SpringRunner.class)
public class A {

  @Autowired
  private SpuDetailApplication application;

  @Test
  public void a() {
    application.a();

  }
}
