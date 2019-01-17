import com.leyou.sms.LySmsApplication;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author shaoyijiong
 * @date 2019/1/14
 */
@SpringBootTest(classes = LySmsApplication.class)
@RunWith(SpringRunner.class)
public class SmsTest {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Test
  public void sendTest() throws InterruptedException {
    Map<String, String> map = new HashMap<>();
    map.put("code", "9417");
    map.put("phone", "17826808394");
    rabbitTemplate.convertAndSend("ly.sms.exchange", "ly.sms.verity", map);
    TimeUnit.SECONDS.sleep(60);
  }

  @Test
  public void redisTest() {
    String phone = "17826808394";
    redisTemplate.opsForValue()
        .set(phone, String.valueOf(System.currentTimeMillis()), 10, TimeUnit.SECONDS);

    redisTemplate.opsForValue().get(phone);
  }



}
