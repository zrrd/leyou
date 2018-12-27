import com.leyou.common.utils.EnumUtils;
import com.leyou.common.service.enums.SkuEnable;
import org.junit.Test;

/**
 * @author shaoyijiong
 * @date 2018/12/27
 */
public class A {

  @Test
  public void ta() {
    SkuEnable skuEnable = EnumUtils.get(SkuEnable.class, true);
    System.out.println(skuEnable);
  }
}
