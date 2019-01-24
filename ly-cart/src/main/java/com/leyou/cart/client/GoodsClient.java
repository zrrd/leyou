package com.leyou.cart.client;

import com.leyou.service.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author shaoyijiong
 * @date 2019/1/24
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {

}
