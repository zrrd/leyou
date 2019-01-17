package com.leyou.service.mvc.req;

import com.leyou.common.base.request.Req;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

/**
 * 注册请求
 *
 * @author shaoyijiong
 * @date 2019/1/17
 */
@Validated
@Data
public class RegisterReq implements Req {

  @Length(min = 4, max = 30, message = "用户名只能再4~30位之间")
  private String username;

  @Length(min = 4, max = 30, message = "密码只能再4~30位之间")
  private String password;

  @Pattern(regexp = "^1[35678]\\d{9}", message = "手机号格式不正确")
  private String phone;
  private String code;
}
