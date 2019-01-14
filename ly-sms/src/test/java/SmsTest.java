import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.sms.LySmsApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author shaoyijiong
 * @date 2019/1/14
 */
@SpringBootTest(classes = LySmsApplication.class)
@RunWith(SpringRunner.class)
public class SmsTest {

  @Test
  public void sendTest() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    String apikey = "5bca25375f73df47070232a2cb8fd1df";

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.add("Accept", MediaType.APPLICATION_JSON.toString());
    MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
    params.add("apikey", apikey);
    params.add("text", "【云片网】您的验证码是1234");
    params.add("mobile", "17826808394");

    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

    String a = restTemplate
        .postForObject("https://sms.yunpian.com/v2/sms/single_send.json",
            requestEntity, String.class);
    System.out.println(a);
  }
}
