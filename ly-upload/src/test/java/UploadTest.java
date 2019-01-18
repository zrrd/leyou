import com.leyou.common.LyUploadApplication;
import com.leyou.common.file.FileService;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author shaoyijiong
 * @date 2019/1/18
 */
@SpringBootTest(classes = LyUploadApplication.class)
@RunWith(SpringRunner.class)
public class UploadTest {

  private static final String pubKeyPath = "D:\\code\\leyou\\leyou\\ly-auth\\rsa.pub";

  private static final String priKeyPath = "D:\\code\\leyou\\leyou\\ly-auth\\rsa.pri";

  @Resource
  FileService fileService;

  @Test
  public void up() throws URISyntaxException {
    File pubKey = new File(pubKeyPath);
    File priKey = new File(priKeyPath);
    String pubKeyUrl = fileService.upload(pubKey);
    String priKeyUrl = fileService.upload(priKey);
    System.out.println("pubKeyUrl=" + pubKeyUrl);
    System.out.println("priKeyUrl=" + priKeyUrl);
  }
}
