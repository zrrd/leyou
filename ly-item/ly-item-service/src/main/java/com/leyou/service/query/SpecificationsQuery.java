package com.leyou.service.query;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leyou.service.query.mapper.SpecificationsQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @author shoayijiong
 * @date 2018/12/11
 */
@Service
public class SpecificationsQuery {

  private final SpecificationsQueryMapper mapper;

  @Autowired
  public SpecificationsQuery(SpecificationsQueryMapper mapper) {
    this.mapper = mapper;
  }

  public String querySpecByCid(Long cid) {
    return mapper.querySpecByCid(cid);
  }
}
