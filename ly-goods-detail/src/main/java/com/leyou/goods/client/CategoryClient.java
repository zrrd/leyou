package com.leyou.goods.client;

import com.leyou.service.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 远程调用
 *
 * @author shaoyijiong
 * @date 2018/12/26
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {

}
