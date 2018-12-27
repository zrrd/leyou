package com.leyou.search.pojo;

import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * @author shaoyijiong
 * @date 2018/12/27
 */
@Data
public class Specification {

  private String group;
  private List<Map<String, Object>> params;
}
