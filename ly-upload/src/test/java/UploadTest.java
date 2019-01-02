import com.google.common.collect.Lists;
import com.leyou.common.LyUploadApplication;
import com.leyou.common.file.FileService;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author shaoyijiong
 * @date 2019/1/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LyUploadApplication.class)
public class UploadTest {

  private static final LinkedList<File> fileList = Lists.newLinkedList();
  private static final List<String> fileUrl = Lists.newArrayList();

  @Autowired
  private FileService fileService;

  @Test
  public void upload() {
    File file = new File("D:\\images");
    //通过递归的方式获得所有图片
    getAllFile(file);

    //上传图片
    fileList.forEach(f -> {
      String url = fileService.upload(f);
      fileUrl.add(url);
    });

    System.out.println(fileUrl);

    try (OutputStream outputStream = new BufferedOutputStream(
        new FileOutputStream(new File("img.txt")))) {
      fileUrl.forEach(f -> {
        try {
          outputStream.write(f.getBytes());
          outputStream.write("\r\n".getBytes());
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void getAllFile(File file) {
    //获得当前所有文件
    File[] files = file.listFiles();
    if (files != null && files.length > 0) {
      for (File file1 : files) {
        if (file1.isDirectory()) {
          getAllFile(file1);
        } else {
          //将所有文件放入列表
          fileList.add(file1);
        }
      }
    }
  }
}
