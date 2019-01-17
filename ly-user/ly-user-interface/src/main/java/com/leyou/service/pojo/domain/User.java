package com.leyou.service.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

  @TableId(type = IdType.AUTO)
  private Long id;
  private String username;
  @JsonIgnore
  private String password;
  private String phone;
  private Date created;
  /**
   * 加密盐值
   */
  @JsonIgnore
  private String salt;

  public static User newInstForSave(String username, String password, String phone, String salt) {
    return new User(null, username, password, phone, new Date(), salt);
  }
}
