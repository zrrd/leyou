package com.leyou.common.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 拦截白名单
 *
 * @author shaoyijiong
 * @date 2019/1/21
 */
@Component
@Data
@ConfigurationProperties(prefix = "ly.filter")
public class FilterProperties {

  private List<String> allowPaths;

}
