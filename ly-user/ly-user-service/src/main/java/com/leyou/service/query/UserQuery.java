package com.leyou.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyou.common.base.exception.ExceptionEnum;
import com.leyou.common.base.exception.LyException;
import com.leyou.service.pojo.domain.User;
import com.leyou.service.query.mapper.UserQueryMapper;
import com.leyou.service.utils.CodecUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shaoyijiong
 * @date 2019/1/16
 */
@Service
public class UserQuery {

  private final UserQueryMapper queryMapper;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  public UserQuery(UserQueryMapper queryMapper) {
    this.queryMapper = queryMapper;
  }

  /**
   * 校验数据是否存在
   */
  public Boolean checkData(String data, Integer type) {
    QueryWrapper<User> qw = new QueryWrapper<>();
    switch (type) {
      //type 为1 查询用户名
      case 1:
        qw.eq("username", data);
        break;
      //type 为2 查询电话
      case 2:
        qw.eq("phone", data);
        break;
      default:
        throw new LyException(ExceptionEnum.INVALID_USER_DATA_TYPE);
    }
    return queryMapper.selectCount(qw) == 0;

  }

  /**
   * 根据用户名密码查询用户
   */
  public User queryUser(String username, String password) {
    QueryWrapper<User> qw = new QueryWrapper<>();
    qw.eq("username", username);
    User user = queryMapper.selectOne(qw);
    if (user == null) {
      throw new LyException(ExceptionEnum.USER_NOT_EXIST);
    }
    //校验密码
    if (!user.getPassword().equals(CodecUtils.md5Hex(password, user.getSalt()))) {
      throw new LyException(ExceptionEnum.USER_NOT_EXIST);
    }
    //用户名密码正确
    return user;
  }
}
