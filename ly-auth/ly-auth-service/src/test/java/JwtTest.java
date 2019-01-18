import com.leyou.auth.entiy.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.auth.utils.RsaUtils;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.junit.Before;
import org.junit.Test;

/**
 * @author shaoyijiong
 * @date 2019/1/18
 */
public class JwtTest {

  private static final String pubKeyPath = "D:\\code\\leyou\\leyou\\ly-auth\\rsa.pub";

  private static final String priKeyPath = "D:\\code\\leyou\\leyou\\ly-auth\\rsa.pri";

  private PublicKey publicKey;

  private PrivateKey privateKey;

  //@Test
  public void testRsa() throws Exception {
    RsaUtils.generateKey(pubKeyPath, priKeyPath, "5566");
  }

  //@Before
  public void testGetRsa() throws Exception {
    this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
    this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
  }

  //@Test
  public void testGenerateToken() {
    // 生成token
    String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
    System.out.println("token = " + token);
  }

  //@Test
  public void testParseToken() {
    String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU0Nzc4MTg2M30.Xl8lc4ki6jrkKzW3_7-PV64e95fM-dXRQp0f-tE90Q-nLHdcKrNJk1D4JzUxhgk65VIPViKo5eVJ_EKoRJzn2sTGrs3dENiyg9K8OGj1vLwzW-q10MdqMYqR8d4hFWZvtkBPXIgpEjHf2o2wtVdEylqUFvalS6dxeE4jbXE3KZI";

    // 解析token
    UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
    System.out.println("id: " + user.getId());
    System.out.println("userName: " + user.getUsername());
  }
}
