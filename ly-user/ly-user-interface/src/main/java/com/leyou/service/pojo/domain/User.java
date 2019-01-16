package com.leyou.service.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户实体类
 *
 * @author shaoyijiong
 * @date 2019/1/16
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@TableName("tb_user")
public class User extends Model<User> {

  private Long id;
  private String user;
  private String password;
  private String phone;
  private Date created;
}
